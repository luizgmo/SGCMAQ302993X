<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuários</title>
    </head>
    <body>
        <% ArrayList<Usuario> dados = new Usuario().getAllTableEntities(); %>
        <h1>Usuários</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Tipo Usuário ID</th>
                <th></th>
                <th></th>
            </tr>
            
            <% for (Usuario user : dados) {%>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getNome() %></td>
                <td><%= user.getCpf() %></td>
                <td><%= user.getTipoUsuarioId() %></td>
                <td><a href="<%= request.getContextPath() %>/home/usuarios_form.jsp?action=update&id=<%= user.getId() %>">Alterar</a></td>
                <td><a href="<%= request.getContextPath() %>/home/usuarios?action=delete&id=<%= user.getId() %>" onclick="return confirm('Deseja realmente excluir Usuário <%= user.getId() %> (<%= user.getNome() %>) ?')" >Excluir</a></td>
            </tr>
            <% } %>
            
        </table>
        <a href="<%= request.getContextPath() %>/home/usuarios_form.jsp?action=create" >Adicionar</a>
    </body>
</html>