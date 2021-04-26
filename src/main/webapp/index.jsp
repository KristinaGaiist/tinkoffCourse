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
    </nav>
</header>
<br>

<div class="row">
    <div class="container">
        <h3 class="text-center">Main Info</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/showBrands" class="btn btn-success">Show Brands</a>
            <a href="<%=request.getContextPath()%>/showModels" class="btn btn-success">Show Models</a>
            <a href="<%=request.getContextPath()%>/showCities" class="btn btn-success">Show Cities</a>
            <a href="<%=request.getContextPath()%>/showCars" class="btn btn-success">Show Cars</a>
            <a href="<%=request.getContextPath()%>/showCustomers" class="btn btn-success">Show Customers</a>
        </div>
        <br>
    </div>
</div>
</body>
</html>