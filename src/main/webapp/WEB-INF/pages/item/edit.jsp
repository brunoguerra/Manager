<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="item.edit.form.button.save" var="save" />
<spring:message code="item.edit.form.button.cancel" var="cancel" />

<%@include file="/WEB-INF/scripts/ResetTextFields.jsp"%>

<section id="main" class="column">

    <article class="module width_full">
        <sf:form modelAttribute="item" method="post">
            <header><h3><spring:message code="item.edit.form.title"/></h3></header>
            <div class="module_content">
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="item.edit.form.name"/>:</label>
                    <sf:input path="name" cssStyle="width:92%;"/>
                    <sf:errors path="name" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="item.edit.form.code"/>:</label>
                    <sf:input path="code" cssStyle="width:92%;"/>
                    <sf:errors path="code" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="item.edit.form.value"/>:</label>
                    <sf:input path="value" cssStyle="width:92%;"/>
                    <sf:errors path="value" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="item.edit.form.priceGross"/>:</label>
                    <sf:input path="priceGross" cssStyle="width:92%;"/>
                    <sf:errors path="priceGross" cssClass="error_text"/>
                    <sf:hidden path="state.id" />
                    <sf:hidden path="state.currentState" />
                    <sf:hidden path="state.lastValue" />
                </fieldset><div class="clear"></div>
            </div>
            <footer>
                <div class="submit_link">
                    <input id="cancel" type="button" value="${cancel}" onclick="javascript:window.location.replace('${contextPath}/item/list')"/>
                    <input id="add" type="submit" value="${save}" class="alt_btn">
                </div>
            </footer>
        </sf:form>
    </article><!-- end of post new article -->
</section>
