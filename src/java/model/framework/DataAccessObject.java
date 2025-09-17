package model.framework;

import controller.AppConfig;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

public abstract class DataAccessObject {
    // nome da tabela no banco de dados relacional à qual este objeto está mapeado
    private String tableEntity;
    
    // flag que indica se esta entidade é nova
    private boolean novelEntity;
    
    // flag que indica se a entidade foi modificada
    private boolean changedEntity;
    
    // mapa que armazena os campos que foram alterados (padrão unit of work)
    private HashMap<String, Object> dirtyFields;

    public DataAccessObject(String tableEntity) {
        setTableEntity(tableEntity);
        dirtyFields = new HashMap<>();
        // inicialmente, toda entidade é considerada nova
        setNovelEntity(true);
        // inicialmente, nenhuma alteração foi feita
        setChangedEntity(false);
    }

    private String getTableEntity() {
        return tableEntity;
    }

    private boolean isNovelEntity() {
        return novelEntity;
    }

    private boolean isChangedEntity() {
        return changedEntity;
    }

    private void setTableEntity(String tableEntity) {
        if (tableEntity != null && !tableEntity.isEmpty() && !tableEntity.isBlank()){
            this.tableEntity = tableEntity;
        } else {
            throw new IllegalArgumentException("table must be valid");
        }
    }

    protected void setNovelEntity(boolean novelEntity) {
        this.novelEntity = novelEntity;
    }

    protected void setChangedEntity(boolean changedEntity) {
        this.changedEntity = changedEntity;
        // limpa os campos sujos quando a entidade é marcada como não alterada
        if (this.changedEntity == false) {
            dirtyFields.clear();
        }
    }
    
    // adiciona uma alteração a ser persistida (padrão unit of work)
    protected void addChange(String field, Object value) {
        dirtyFields.put(field, value);
        // marca a entidade como modificada
        setChangedEntity(true);
    }
    
    // executa operação de insert no banco de dados
    private void insert() throws SQLException {
        String dml = "INSERT INTO " + getTableEntity();
        
        StringJoiner fields = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");
        
        // constrói dinamicamente a lista de campos e valores
        for (String field: dirtyFields.keySet()) {
            fields.add(field);
            values.add("?");
        }
        
        dml += " (" + fields + ") VALUES (" + values + ")";
        
        if (AppConfig.getInstance().isVerbose()) System.out.println(dml);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        PreparedStatement pst =  con.prepareStatement(dml);
        
        // preenche os parâmetros da query com os valores dos campos alterados
        int index = 1;
        for (String field: dirtyFields.keySet()) {
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        
        if (AppConfig.getInstance().isVerbose()) System.out.println(pst);
        
        pst.execute();
        
        pst.close();
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    // executa operação de update no banco de dados
    private void update() throws SQLException {
        String dml = "UPDATE " + getTableEntity() + " SET ";
        
        StringJoiner changes = new StringJoiner(", ");
        
        // constrói dinamicamente a cláusula SET
        for(String field: dirtyFields.keySet()) {
            changes.add(field + " = ? ");
        }
        
        dml += changes + " WHERE " + getWhereClauseForOneEntity();
        
        if(AppConfig.getInstance().isVerbose()) System.out.println(dml);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        PreparedStatement pst =  con.prepareStatement(dml);
        
        // preenche os parâmetros da query com os valores dos campos alterados
        int index = 1;
        for (String field: dirtyFields.keySet()) {
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        
        if (AppConfig.getInstance().isVerbose()) System.out.println(pst);
        
        pst.execute();
        
        pst.close();
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    // persiste as alterações no banco de dados (padrão unit of work)
    public void save() throws SQLException {
        if (isChangedEntity()) {
            if (isNovelEntity()) {
                insert();
                setNovelEntity(false);
            } else {
                update();
            }
            setChangedEntity(false);
        }
    }
    
    // remove a entidade do banco de dados
    public void delete() throws SQLException {
        String dml = "DELETE FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if (AppConfig.getInstance().isVerbose()) System.out.println(dml);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        
        st.execute(dml);
        st.close();
        
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    // carrega os dados da entidade do banco de dados
    public boolean load() throws SQLException {
        boolean resultado;
        
        String dql = "SELECT * FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if (AppConfig.getInstance().isVerbose()) System.out.println(dql);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(dql);
        
        resultado = rs.next();
        
        if (resultado) {
            ArrayList<Object> data = new ArrayList();
            // extrai todos os dados do resultset
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                data.add(rs.getObject(i));
            }
            
            // preenche o objeto com os dados recuperados
            fill(data);
            setNovelEntity(false);
        }
        
        return resultado;
    }
    
    // recupera todas as entidades da tabela
    public <T extends DataAccessObject> ArrayList<T> getAllTableEntities() throws SQLException {
        ArrayList<T> result = new ArrayList<>();
        
        String dql = "SELECT * FROM " + getTableEntity();
        
        if (AppConfig.getInstance().isVerbose()) System.out.println(dql);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(dql);
        
        while (rs.next()) {
            ArrayList<Object> data = new ArrayList<>();
            
            // extrai todos os dados do resultset
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                data.add(rs.getObject(i));
            }
            
            // cria uma cópia do objeto preenchido com os dados
            result.add(fill(data).copy());
        }
        
        st.close();
        
        DataBaseConnections.getInstance().closeConnection(con);
        
        return result;
    }
    
    // método abstrato para definir a cláusula WHERE para uma entidade específica
    protected abstract String getWhereClauseForOneEntity();
    
    // método abstrato para preencher o objeto com dados do banco
    protected abstract DataAccessObject fill(ArrayList<Object> data);
    
    // método abstrato para criar uma cópia do objeto
    protected abstract <T extends DataAccessObject> T copy();
}