package model;

import java.sql.SQLException;
import java.util.ArrayList;
import model.framework.DataBaseConnections;

public class __Exemplo {
    public static void main(String[] args) throws SQLException {
        
        TipoUsuario tp = new TipoUsuario();
        
//        tp.setId(51);
//        tp.setModuloAdministrativo("N");
//        tp.setModuloAgendamento("N");
//        tp.setModuloAtendimento("S");
//        
//        tp.save(); // insert
//        
//        tp.setModuloAdministrativo("S");
//        tp.setModuloAgendamento("S");
//        
//        tp.save(); // update

//        tp.setId(5);
//        boolean status = tp.load();
//        System.out.println(status);
//        System.out.println(tp);
//        
//        tp.setNome("tipo 5");
//        tp.save(); // update
//        System.out.println(tp);
//        
//        tp.delete();
        
        ArrayList<TipoUsuario> lst = new TipoUsuario().getAllTableEntities();
        System.out.println(lst);
    }
}
