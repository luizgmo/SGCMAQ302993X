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
        <%@include file = "/home/app/modulos.jsp"%>
        <% ArrayList<Usuario> dados = new Usuario().getAllTableEntities(); %>
        <h1>Usuários</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Tipo Usuário ID</th>
                <th>Endereço</th>
                <th></th>
                <th></th>
            </tr>
            
            <% for (Usuario user : dados) {%>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getNome() %></td>
                <td><%= user.getCpf() != null ? user.getCpf() : "-" %></td>
                <td><%= user.getTipoUsuarioId() %></td>
                <td><%= user.getEndereco() %></td>
                <td><a href="<%= request.getContextPath() %>/home/app/adm/usuarios_form.jsp?action=update&id=<%= user.getId() %>">Alterar</a></td>
                <td><a href="<%= request.getContextPath() %>/home?action=delete&id=<%= user.getId() %>&task=usuarios" onclick="return confirm('Deseja realmente excluir Usuário <%= user.getId() %> (<%= user.getNome() %>) ?')" >Excluir</a></td>
            </tr>
            <% } %>
            
        </table>
        <a href="<%= request.getContextPath() %>/home/app/adm/usuarios_form.jsp?action=create" >Adicionar</a>
    </body>
</html>