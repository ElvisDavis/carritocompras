<%--
  Created by IntelliJ IDEA.
  User: ADMIN-ITQ
  Date: 2/12/2024
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,models.*" %>
<%
    List<Productos> productos=(List<Productos>) request.getAttribute("productos");
    Optional<String> username=(Optional<String>) request.getAttribute("username");

%>
<html>
<head>
    <title>Lista Productos</title>
</head>
<body>
<h1>Listado de productos</h1>
<%
    if (username.isPresent()) {%>
        <div style="color: blue">Hola <%=username.get()%>, bienvenido</div>
        <div><a href="${pageContext.request.contextPath}/productos/form">Ingresar Productos</a></div>
   <% }%>
<table>
    <tr>
        <th>ID PRODUCTO</th>
        <th>NOMBRE PRODUCTO</th>
        <th>CATEGORIA</th>
        <%if(username.isPresent()){%>
        <th>PRECIO</th>
        <th>OPCIONES</th>
        <%}%>
    </tr>
    <%
        for (Productos p : productos) {%>
    <tr>
        <td><%=p.getIdProducto()%></td>
        <td><%=p.getNombre()%></td>
        <td><%=p.getCategoria().getNombre()%></td>
        <%if(username.isPresent()){%>
        <td><%=p.getPrecio()%></td>
        <td><a href="<%=request.getContextPath()%>/agregar-carro?idProducto=<%=p.getIdProducto()%>">agregar</a></td>
        <%}%>
    </tr>
    <%}%>
</table>

</body>
</html>
