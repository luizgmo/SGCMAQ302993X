package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import model.framework.DataAccessObject;

// classe que representa a entidade usuário e herda de dataaccessobject
// implementa o padrão dao para persistência de dados na tabela 'usuarios'
public class Usuario extends DataAccessObject {

    // atributos mapeados para as colunas da tabela usuarios
    private int id;
    private String nome;
    private String cpf;
    private String senha;
    // chave estrangeira para a tabela tipo_usuario
    private int tipoUsuarioId;
    private String endereco;

    // construtor padrão que define o nome da tabela no banco de dados
    public Usuario() {
        super("usuarios");
    }

    // métodos getters para acesso aos atributos
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public int getTipoUsuarioId() {
        return tipoUsuarioId;
    }
    
    public String getEndereco() {
        return endereco;
    }

    // métodos setters que também registram as alterações (padrão unit of work)
    public void setId(int id) {
        this.id = id;
        addChange("id", this.id);
    }

    public void setNome(String nome) {
        this.nome = nome;
        addChange("nome", this.nome);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        addChange("cpf", this.cpf);
    }

    public void setSenha(String senha) throws Exception {
        if (senha == null) {
            if (this.senha != null) {
                this.senha = senha;
                addChange("senha", this.senha);
            }
        } else {
            if (senha.equals(this.senha) == false) {

                String senhaSal = getId() + senha + getId() / 2;

                MessageDigest md = MessageDigest.getInstance("SHA-256");

                String hash = new BigInteger(1, md.digest(senhaSal.getBytes("UTF-8"))).toString(16);

                this.senha = hash;
                addChange("senha", this.senha);

            }
        }
    }

    public void setTipoUsuarioId(int tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
        addChange("tipo_usuario_id", this.tipoUsuarioId);
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
        addChange("endereco", this.endereco);
    }

    // implementação do método abstrato para definir a cláusula where
    @Override
    protected String getWhereClauseForOneEntity() {
        return " id = " + getId();
    }

    // implementação do método abstrato para preencher o objeto com dados do resultset
    @Override
    protected DataAccessObject fill(ArrayList<Object> data) {
        // preenche os atributos na mesma ordem das colunas da tabela
        id = (int) data.get(0);
        nome = (String) data.get(1);
        cpf = (String) data.get(2);
        senha = (String) data.get(3);
        tipoUsuarioId = (int) data.get(4);
        endereco = (String) data.get(5);
        return this;
    }

    // implementação do método abstrato para criar uma cópia do objeto
    @Override
    protected Usuario copy() {
        Usuario copia = new Usuario();

        // copia todos os atributos para o novo objeto
        copia.setId(getId());
        copia.setNome(getNome());
        copia.setCpf(getCpf());
        copia.senha = (getSenha());
        copia.setTipoUsuarioId(getTipoUsuarioId());
        copia.setEndereco(getEndereco());

        // marca a cópia como não sendo uma nova entidade
        copia.setNovelEntity(false);

        return copia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Usuario) {
            Usuario aux = (Usuario) obj;
            if (getId() == aux.getId()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + getId() + ", " + getNome() + ", " + getCpf() + ", " + getSenha() + ", " + getTipoUsuarioId() + "," + getEndereco() + ")";
    }
}
