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

<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <form action="createCustomer" method="post">
                <fieldset class="form-group">
                    <label>Last Name</label> <input type="text"
                                                    value="<c:out value='${customer.lastName}' />" class="form-control"
                                                    name="lastName" required="required">
                    <label>First Name</label> <input type="text"
                                                     value="<c:out value='${customer.firstName}' />"
                                                     class="form-control"
                                                     name="firstName" required="required">
                    <label>Middle Name</label> <input type="text"
                                                      value="<c:out value='${customer.middleName}' />"
                                                      class="form-control"
                                                      name="middleName" required="required">
                    <label>City</label> <input type="text"
                                               value="<c:out value='${city}' />"
                                               class="form-control"
                                               name="city" required="required">
                </fieldset>
                <button type="submit" class="btn btn-success">Save</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>