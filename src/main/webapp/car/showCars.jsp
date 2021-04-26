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
        <h3 class="text-center">List of Car</h3>
        <hr>
        <c:if test="${customer != null}">
            <h3 class="text-center">Cars of customer: ${customer.lastName} ${customer.firstName} ${customer.middleName}</h3>
        </c:if>
        <div class="container text-left">
            <c:if test="${customer == null}">
            <a href="<%=request.getContextPath()%>/createCar" class="btn btn-success">Add New Car</a>
            </c:if>
            <c:if test="${customer != null}">
                <a href="<%=request.getContextPath()%>addCustomerCar?id=<c:out value='${customer.id}'/>" class="btn btn-success">Add Car</a>
            </c:if>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>State Number</th>
                <th>Model</th>
                <th>Brand</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="car" items="${cars}">
                <tr>
                    <td><c:out value="${car.id}" /></td>
                    <td><c:out value="${car.stateNumber}" /></td>
                    <td><c:out value="${car.model.model}" /></td>
                    <td><c:out value="${car.model.brand.name}" /></td>
                    <td><a href="updateCar?id=<c:out value='${car.id}'/>">Update</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteCar?stateNumber=<c:out value='${car.stateNumber}'/>">Delete</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="showCarCustomers?stateNumber=<c:out value='${car.stateNumber}'/>">Car Owners</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>
</div>
</body>
</html>