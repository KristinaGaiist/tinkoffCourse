<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <h3 class="text-center">List of Customers</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/createCustomer" class="btn btn-success">Add New Customer</a>
        </div>
        <br>
        <c:if test="${car != null}">
            <h3 class="text-center">Customers of car: ${car.model.model} ${car.model.brand.name} ${car.stateNumber}</h3>
        </c:if>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Middle Name</th>
                <th>City</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <td><c:out value="${customer.id}"/></td>
                    <td><c:out value="${customer.lastName}"/></td>
                    <td><c:out value="${customer.firstName}"/></td>
                    <td><c:out value="${customer.middleName}"/></td>
                    <td><c:out value="${customer.city.name}"/></td>
                    <td><a href="updateCustomer?id=<c:out value='${customer.id}'/>">Update</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteCustomer?id=<c:out value='${customer.id}'/>">Delete</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="addCustomerCar?id=<c:out value='${customer.id}'/>">Add Car</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="showCustomerCars?id=<c:out value='${customer.id}'/>">Cars</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>