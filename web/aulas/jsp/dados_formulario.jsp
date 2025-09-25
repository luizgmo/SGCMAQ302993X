<%@page import="aulas.servlet.getpost.InformacaoForm"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dados do Formulário</title>
    </head>
    <body>
        <% ArrayList<InformacaoForm> dados = (ArrayList<InformacaoForm>) request.getAttribute("dados"); %>
        <h1>Dados do Formulário</h1>
        <table>
            <tr>
                <th>Campo A</th>
                <th>Opção A</th>
                <th>Opção B</th>
            </tr>

            <% if (dados != null) { %> 
            <% for (InformacaoForm info : dados) {%>
            <tr>
                <td><%= info.getCampoA()%></td>
                <td><%= info.getOpcaoA()%></td>
                <td><%= info.getOpcaoB()%></td>
            </tr>
            <%} %>
            <%}%>

        </table>

        <a href="<%= request.getContextPath()%>/aulas/jsp/formulario.jsp">Adicionar Informação</a>
    </body>
</html>
