<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Menu</h1>
<menu>
    <li><a href="<%= request.getContextPath() %>/home/app/menu.jsp">Menu</a></li>
    <li><a href="<%= request.getContextPath() %>/home?task=logout">Logout <%= session.getAttribute("usuario") %> </a></li>
</menu>
