<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="report.add.form.button.add" var="add" />
<spring:message code="report.add.form.button.reset" var="reset" />
<spring:message code="report.add.search" var="search" />

<%@include file="/WEB-INF/scripts/ResetTextFields.jsp"%>

<script src="<c:url value="/resources/js/jquery.ui.datepicker-pl.js" />" ></script>
<script type="text/javascript">
    $(document).ready(function() {
        $( "#startDate" ).datepicker();
        $( "#endDate" ).datepicker();
        $("#startDate").attr('autocomplete','off');
        $("#endDate").attr('autocomplete','off');
    });
</script>

<section id="main" class="column">

    <article class="module width_full">
        <sf:form modelAttribute="report" method="post">
            <header><h3><spring:message code="report.add.form.title" /></h3></header>
            <div class="module_content">
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="report.add.form.quoter"/>:</label>
                    <sf:input path="quoter" cssStyle="width:92%;"/>
                    <sf:errors path="quoter" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="report.add.form.startDate"/>:</label>
                    <sf:input path="startDate" cssStyle="width:92%;"/>
                    <sf:errors path="startDate" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
                <fieldset style="width:48%; float:left;">
                    <label><spring:message code="report.add.form.endDate"/>:</label>
                    <sf:input path="endDate" cssStyle="width:92%;"/>
                    <sf:errors path="endDate" cssClass="error_text"/>
                </fieldset><div class="clear"></div>
            </div>
            <footer>
                <div class="submit_link">
                    <input id="reset" type="button" value="${reset}"/>
                    <input id="add" type="submit" value="${add}" class="alt_btn">
                </div>
            </footer>
        </sf:form>
    </article><!-- end of post new article -->
</section>
