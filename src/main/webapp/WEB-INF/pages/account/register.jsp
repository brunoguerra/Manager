<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Rejestracja</title>
    <style type="text/css">
        .error_text {
            color: #ff290a;
        }
    </style>
</head>
<body>

<spring:message code="login.email" var="email"/>
<spring:message code="login.password" var="password"/>

<!--WRAPPER-->
<div id="wrapper">
    <sf:form modelAttribute="company" method="post">
        <div class="header">
            <h1>Rejestracja</h1>
        </div>
        <div class="content">
            Email: <sf:input path="username" type="text"/>
            <sf:errors path="username" cssClass="error_text"/>
            </br>
            Hasło: <sf:input path="password" type="password"/>
            <sf:errors path="password" cssClass="error_text"/>
            </br>
            Powtórz hasło: <sf:input path="confirmPassword" type="password"/>
            <sf:errors path="confirmPassword" cssClass="error_text"/>
            <c:if test="passwordDontMatch">
                <span class="error_text">Hasła muszą być identyczne</span>
            </c:if>
            </br>
            Nazwa firmy: <sf:input path="fullName" type="text"/>
            <sf:errors path="fullName" cssClass="error_text"/>
            </br>
            NIP: <sf:input path="nip" type="text"/>
            <sf:errors path="nip" cssClass="error_text"/>
            </br>
            Adres firmy:
            </br>
            Miasto: <sf:input path="address.city" type="text" />
            <sf:errors path="address.city" cssClass="error_text"/>
            </br>
            Kod pocztowy: <sf:input path="address.postCode"/>
            <sf:errors path="address.postCode" cssClass="error_text"/>
            </br>
            Ulica: <sf:input path="address.street" type="text" />
            </br>
            Numer: <sf:input path="address.number" type="text" />
            <sf:errors path="address.number" cssClass="error_text"/>
            </br>
        </div>
        <div class="footer">
            <input type="submit" name="submit" value="Zarejestruj"/>
        </div>
    </sf:form>
</div>

</body>
</html>
