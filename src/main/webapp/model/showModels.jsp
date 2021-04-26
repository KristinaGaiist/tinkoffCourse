<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Users Car Application</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand"> Users Car Application </a>
        </div>
        <ul class="navbar-nav">
            <li><a href="/" class="nav-link">Main</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <div class="container">
        <h3 class="text-center">List of Models</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/createModel" class="btn btn-success">Add New Model</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Model</th>
                <th>Brand</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="model" items="${models}">
                <tr>
                    <td><c:out value="${model.id}" /></td>
                    <td><c:out value="${model.model}" /></td>
                    <td><c:out value="${model.brand.name}" /></td>
                    <td><a href="updateModel?id=<c:out value='${model.id}'/>">Update</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteModel?model=<c:out value='${model.model}'/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>
</div>
</body>
</html>