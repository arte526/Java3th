<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Distance Calculator</title>
  <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
  <h1>Distance Calculator</h1>
  <form action="calculateDistance" method="post">
    <div class="form-group">
      <label>Координати міста 1:</label>
      <div class="coords">
        <input type="text" name="lat1" placeholder="Широта" value="${param.lat1}">
        <input type="text" name="lon1" placeholder="Довгота" value="${param.lon1}">
      </div>
    </div>
    <div class="form-group">
      <label>Координати міста 2:</label>
      <div class="coords">
        <input type="text" name="lat2" placeholder="Широта" value="${param.lat2}">
        <input type="text" name="lon2" placeholder="Довгота" value="${param.lon2}">
      </div>
    </div>
    <div class="form-group">
      <button type="submit">Розрахувати</button>
    </div>
    <div class="form-group error">
      <c:if test="${not empty error}">
        ${error}
      </c:if>
    </div>
  </form>
  <c:if test="${not empty distance}">
    <div class="result">
      <p>Distance: ${distance} meters</p>
    </div>
  </c:if>
</div>
</body>
</html>
