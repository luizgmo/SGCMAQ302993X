package model;

import java.sql.SQLException;
import java.util.ArrayList;

public class __ExemploUsuario {
    public static void main(String[] args) throws SQLException {
        
        Usuario usuario = new Usuario();
        
//        usuario.setId(1);
//        usuario.setNome("João Silva");
//        usuario.setCpf("123.456.789-00");
//        usuario.setSenha("senha123");
//        usuario.setTipoUsuarioId(1);
//        
//        usuario.save();
        
        ArrayList<Usuario> listaUsuarios = new Usuario().getAllTableEntities();
        System.out.println("Lista de usuários: " + listaUsuarios);
    }
}