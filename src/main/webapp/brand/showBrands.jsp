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
        <h3 class="text-center">List of Brand</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/createBrand" class="btn btn-success">Add New Brand</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="brand" items="${brands}">
                <tr>
                    <td><c:out value="${brand.id}" /></td>
                    <td><c:out value="${brand.name}" /></td>
                    <td><a href="updateBrand?id=<c:out value='${brand.id}'/>">Update</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteBrand?name=<c:out value='${brand.name}'/>">Delete</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="showBrandModels?name=<c:out value='${brand.name}'/>">Brand Models</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>
</div>
</body>
</html>