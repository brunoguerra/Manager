<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="customer.add.form.button.add" var="add" />
<spring:message code="customer.add.form.button.reset" var="reset" />

<%@include file="/WEB-INF/scripts/ResetTextFields.jsp"%>

<script type="text/javascript">
    function repleaceComma() {
        var price = $('#priceGross').val();
        var state = $('#state\\.currentState').val();
        if(price.indexOf(',') > 0) {
            price = price.replace(',', '.');
            $('#priceGross').val(price);
        }
        if(state.indexOf(',') > 0) {
            state = state.replace(',', '.');
            $('#state\\.currentState').val(state);
        }

        return true;
    }
</script>

<section id="main" class="column">

<article class="module width_full">
    <sf:form modelAttribute="item" method="post" onsubmit="return repleaceComma();">
        <header><h3><spring:message code="item.add.form.title"/></h3></header>
        <div class="module_content">
            <fieldset style="width:48%; float:left;">
                <label><spring:message code="item.add.form.name"/>:</label>
                <sf:input path="name" cssStyle="width:92%;"/>
                <sf:errors path="name" cssClass="error_text"/>
            </fieldset><div class="clear"></div>
            <fieldset style="width:48%; float:left;">
                <label><spring:message code="item.add.form.nameInvoice"/>:</label>
                    <sf:input path="nameInvoice" cssStyle="width:92%;"/>
            </fieldset><div class="clear"></div>
            <fieldset style="width:48%; float:left;">
                <label><spring:message code="item.add.form.code"/>:</label>
                <sf:input path="code" cssStyle="width:92%;"/>
                <sf:errors path="code" cssClass="error_text"/>
            </fieldset><div class="clear"></div>
            <fieldset style="width:48%; float:left;">
                <label><spring:message code="item.add.form.value"/>:</label>
                <sf:input path="value" cssStyle="width:92%;"/>
                <sf:errors path="value" cssClass="error_text"/>
            </fieldset><div class="clear"></div>
            <fieldset style="width:48%; float:left;">
                <label><spring:message code="item.add.form.priceGross"/>:</label>
                <sf:input path="priceGross" cssStyle="width:92%;"/>
                <sf:errors path="priceGross" cssClass="error_text"/>
            </fieldset><div class="clear"></div>
            <fieldset style="width:48%; float:left;">
                <label><spring:message code="item.add.form.currentState"/>:</label>
                <sf:input path="state.currentState" cssStyle="width:92%;"/>
                <sf:errors path="state.currentState" cssClass="error_text"/>
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
