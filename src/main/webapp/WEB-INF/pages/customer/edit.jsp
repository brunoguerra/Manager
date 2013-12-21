<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="customer.edit.form.button.save" var="save" />
<spring:message code="customer.edit.form.button.cancel" var="cancel"/>

<section id="main" class="column">

    <article class="module width_full">
        <sf:form modelAttribute="customer" method="post">
            <header><h3><spring:message code="customer.edit.form.title"/></h3></header>
            <div class="module_content">
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="customer.edit.form.name"/>:</label>
                    <sf:input path="firstName" cssStyle="width:92%;"/>
                    <sf:hidden path="id"/>
                    <sf:errors path="firstName" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="customer.edit.form.surname"/>:</label>
                    <sf:input path="lastName" cssStyle="width:92%;"/>
                    <sf:errors path="lastName" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <%--<fieldset style="width:48%; float:left;">--%>
                    <%--<label><spring:message code="customer.edit.form.phone"/>:</label>--%>
                    <%--<sf:input path="phoneNumber" cssStyle="width:92%;"/>--%>
                <%--</fieldset><div class="clear"></div>--%>
                <%--<fieldset style="width:48%; float:left;">--%>
                    <%--<label><spring:message code="customer.edit.form.pesel"/>:</label>--%>
                    <%--<sf:input path="pesel" cssStyle="width:92%;"/>--%>
                <%--</fieldset><div class="clear"></div>--%>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="customer.edit.form.city"/>:</label>
                    <sf:hidden path="address.id"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="customer.edit.form.city"/>:</label>
                    <sf:input path="address.city" cssStyle="width:92%;"/>
                    <sf:errors path="address.city" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="customer.edit.form.street"/>:</label>
                    <sf:input path="address.street" cssStyle="width:92%;"/>
                    <sf:errors path="address.street" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <%--<fieldset style="width:48%; float:left;">--%>
                    <%--<label><spring:message code="customer.edit.form.number"/>:</label>--%>
                    <%--<sf:input path="address.number" cssStyle="width:92%;"/>--%>
                <%--</fieldset><div class="clear"></div>--%>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="customer.edit.form.postcode"/>:</label>
                    <sf:input path="address.postCode" cssStyle="width:92%;"/>
                    <sf:errors path="address.postCode" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
            </div>
            <footer>
                <div class="submit_link">
                    <input id="cancel" type="button" value="${cancel}" onclick="javascript:window.location.replace('${contextPath}/customer/list')"/>
                    <input id="add" type="submit" value="${save}" class="alt_btn">
                </div>
            </footer>
        </sf:form>
    </article><!-- end of post new article -->
</section>