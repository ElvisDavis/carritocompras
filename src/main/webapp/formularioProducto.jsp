<%--
  Created by IntelliJ IDEA.
  User: ADMIN-ITQ
  Date: 5/12/2024
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, models.*" %>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
%>
<html>
<head>
    <title>Ingresar Producto</title>
</head>
<body>
<h1>Ingresar Productos</h1>
<div>
    <form action="<%=request.getContextPath()%>/productos/form" method="post">
        <div>
            <label for="nombre">Ingrese el nombre del producto:</label>
            <div>
                <input type="text" id="nombre" name="nombre">
            </div>
        </div>
        <div>
            <label for="categoria">Categoria</label>
            <div>
                <select name="idCategoria" id="categoria">
                    <option value="">---Selecione una Categoria---</option>
                    <% for (Categoria c: categorias) {%>
                    <option value="<%=c.getIdCategoria()%>"><%=c.getNombre()%></option>
                    <%}%>

}
                </select>
            </div>
        </div>
        <div>
            <label for="precio">Ingrese el precio:</label>
            <div>
                <input type="number" name="precio" id="precio" step="0.01">
            </div>
        </div>
        <div>
            <input type="submit" value="enviar">
        </div>

    </form>
</div>

</body>
</html>
