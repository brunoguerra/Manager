<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>

    <!--------------------
    LOGIN FORM
    by: Amit Jakhu
    www.amitjakhu.com
    --------------------->

    <!--META-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Logowanie</title>

    <!--STYLESHEETS-->
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />

    <!--SCRIPTS-->
    <script src="//code.jquery.com/jquery-1.9.1.js"></script>
    <!--Slider-in icons-->
    <script type="text/javascript">
        $(document).ready(function() {
            $(".username").focus(function() {
                $(".user-icon").css("left","-48px");
            });
            $(".username").blur(function() {
                $(".user-icon").css("left","0px");
            });

            $(".password").focus(function() {
                $(".pass-icon").css("left","-48px");
            });
            $(".password").blur(function() {
                $(".pass-icon").css("left","0px");
            });
        });
    </script>

</head>
<body>

<spring:message code="login.email" var="email"/>
<spring:message code="login.password" var="password"/>

<!--WRAPPER-->
<div id="wrapper">

    <!--SLIDE-IN ICONS-->
    <div class="user-icon"></div>
    <div class="pass-icon"></div>
    <!--END SLIDE-IN ICONS-->

    <!--LOGIN FORM-->
    <form name="login-form" class="login-form" action="${contextPath}/j_spring_security_check" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <!--HEADER-->
        <div class="header">
            <!--TITLE--><h1><spring:message code="login.title"/></h1><!--END TITLE-->
            <!--DESCRIPTION--><span><spring:message code="login.description"/></span><!--END DESCRIPTION-->
        </div>
        <!--END HEADER-->

        <c:if test="${param.error != null}">
            <div class="login-fail">
                <spring:message code="login.fail"/>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="logout-success">
                <spring:message code="login.logout"/>
            </div>
        </c:if>
        <!--CONTENT-->
        <div class="content">
            <!--USERNAME--><input name="username" type="text" class="input username" value="${email}" onfocus="this.value=''" /><!--END USERNAME-->
            <!--PASSWORD--><input name="password" type="password" class="input password" value="${password}" onfocus="this.value=''" /><!--END PASSWORD-->
        </div>
        <!--END CONTENT-->

        <!--FOOTER-->
        <div class="footer">
            <!--LOGIN BUTTON--><input type="submit" name="submit" value="Login" class="button" /><!--END LOGIN BUTTON-->
        </div>
        <!--END FOOTER-->

    </form>
    <!--END LOGIN FORM-->

</div>
<!--END WRAPPER-->

<!--GRADIENT--><div class="gradient"></div><!--END GRADIENT-->

</body>
</html>