<%@page import="model.Usuario"%>
<%@page import="model.TipoUsuario"%>
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
        <%
            Usuario user = null;
            String action = request.getParameter("action");
            if (action == null) {
                action = "create";
            } else {
                if (action.equals("update")) {
                    int id = Integer.valueOf( request.getParameter("id") );
                    
                    user = new Usuario();
                    user.setId(id);
                    user.load();
                }
            }

            // Carregar tipos de usuário
            ArrayList<TipoUsuario> tiposUsuario = new TipoUsuario().getAllTableEntities();
        %>
        <h1>Usuários</h1>
        <form action="<%= request.getContextPath()%>/home?action=<%= action%>&task=usuarios" method="post">

            <label for="id">Id:</label>
            <input type="text" id="id" name="id" pattern="\d+" title="apenas digitos" value="<%= (user != null) ? user.getId() : "" %>" <%= (user != null) ? "readonly" : "" %> required ><br/>

            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= ((user != null) && (user.getNome() != null)) ? user.getNome() : "" %>" ><br/>

            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" title="Formato: 000.000.000-00" value="<%= ((user != null) && (user.getCpf() != null)) ? user.getCpf() : "" %>" ><br/>
            
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" value="<%= ((user != null) && (user.getSenha() != null)) ? user.getSenha() : "" %>" required><br/>
      
            <label for="cep">CEP</label>
            <input type="text" id="cep" name="cep" >
            <input type="button" id="buscar_por_cep" name="buscar_por_endereco" pattern="\d{5}-\d{3}" value="Buscar Endereço" /> <br/>
            
            <label for="endereco">Endereço:</label>
            <textarea type="text" id="endereco" rows="4" cols="50" name="endereco" ><%= ((user != null) && (user.getEndereco() != null)) ? user.getEndereco() : "" %></textarea><br/>

            <label for="tipo_usuario_id">Tipo Usuário:</label>
            <select id="tipo_usuario_id" name="tipo_usuario_id" required>
                <option value="">Selecione</option>
                <% for (TipoUsuario tp : tiposUsuario) { %>
                <option value="<%= tp.getId() %>" <%= (user != null && user.getTipoUsuarioId() == tp.getId()) ? "selected" : "" %>><%= tp.getNome() %></option>
                <% } %>
            </select><br/>

            <input type="submit" name="Salvar" value="Salvar">
        </form>

    </body>
</html>