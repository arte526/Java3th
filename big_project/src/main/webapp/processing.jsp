<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 19.05.2024
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Distance Result</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <h2>Результат обробки</h2>
    <c:if test="${not empty distance}">
        <div class="result">
            <p>Distance: ${distance} meters</p>
        </div>
    </c:if>
    <a href="index.jsp" class="back-link">Back</a>
</div>
</body>
</html>
