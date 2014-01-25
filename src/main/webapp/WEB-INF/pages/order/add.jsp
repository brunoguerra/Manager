<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<spring:message code="customer.add.form.button.add" var="add" />
<spring:message code="customer.add.form.button.reset" var="reset" />
<spring:message code="order.add.search" var="search" />

<%@include file="/WEB-INF/scripts/ClientAutocomplete.jsp"%>

<script src="<c:url value="/resources/js/jquery.ui.datepicker-pl.js" />" ></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#orderDate" ).datepicker();
        $("#orderDate").attr('autocomplete','off');
    });
</script>

<script type="text/javascript">

    $(document).ready(function(){

        //set current date
        var myDate = new Date();
        var prettyDate = myDate.getDate() + '-' +  ( '0' + (myDate.getMonth()+1) ).slice( -2 ) + '-' +
                myDate.getFullYear();
        $("#orderDate").val(prettyDate);

        var cid = ${sessionScope.customer.id == null ? -1 : sessionScope.customer.id};
        if(cid > 0) {
            $("#clientName").text( "" + '${sessionScope.customer.lastName}' + " " + '${sessionScope.customer.firstName}' + "");
            $("#clientAddress").text( "" + '${sessionScope.customer.address.city}' + " " + '${sessionScope.customer.address.postCode}' + " " + '${sessionScope.customer.address.street}' + " " + '${sessionScope.customer.address.number}' + "");
            $("#customer\\.id").val(cid);
        }
        //add remove data row
        $('.new').click(function(){
            if($(this).attr('class') == 'add remove') {
                $(this).parent().parent().remove();
            } else {
                var thisRow = $(this).parent().parent();
                newRow = thisRow.clone(true).insertAfter(thisRow);
                newRow.find('input:not(.add)').val("");

                //set new name for select boxes
                var quantity = newRow.find('input');
                quantity.attr('name', getNeName(quantity.attr('name')));

                newRow.find('select').each(function() {
                    $(this).attr('name', getNeName($(this).attr('name')));
                });

                $(this).removeClass('new').addClass('remove');
                $(this).val("-");
            }
        });
    });

    function getNeName(name) {
        var text = name.split('[');
        var number = text[1].split(']');
        var result = (parseInt(number[0])+1);
        return text[0] + '[' + result + ']' + number[1];
    }

    function repleaceComma() {
        var quantity0 = $('[name=orderDetails\\[0\\]\\.quantity]').val();
        var quantity1 = $('[name=orderDetails\\[1\\]\\.quantity]').val();
        var quantity2 = $('[name=orderDetails\\[2\\]\\.quantity]').val();
        var quantity3 = $('[name=orderDetails\\[3\\]\\.quantity]').val();

        if(quantity0.indexOf(',') > 0) {
            quantity0 = quantity0.replace(',', '.');
            $('[name=orderDetails\\[0\\]\\.quantity]').val(quantity0);
        }
        if(quantity1.indexOf(',') > 0) {
            quantity1 = quantity1.replace(',', '.');
            $('[name=orderDetails\\[1\\]\\.quantity]').val(quantity1);
        }
        if(quantity2.indexOf(',') > 0) {
            quantity2 = quantity2.replace(',', '.');
            $('[name=orderDetails\\[2\\]\\.quantity]').val(quantity2);
        }
        if(quantity3.indexOf(',') > 0) {
            quantity3 = quantity3.replace(',', '.');
            $('[name=orderDetails\\[3\\]\\.quantity]').val(quantity3);
        }
        return true;
    }

    function checkClient() {
        if($('#customer\\.id').val() == '') {
            $("#missingclient").show();
            return false;
        }
        repleaceComma();
        return true;
    }
</script>

<section id="main" class="column">

    <h4 id="missingclient" class="alert_warning" style="display: none;"><spring:message code="order.add.form.missingcustomer"/></h4>

    <article class="module width_full">
        <form class="quick_search_custom">
            <input id="search" name="search" type="text" value="${search}" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
        </form>
        <sf:form modelAttribute="order" method="post" onsubmit="return checkClient();">
            <joda:format var="date" value="${order.orderDate}" pattern="dd-MM-yyyy"/>
            <header>
                <h3 style="width: 30%;"><spring:message code="order.add.form.title" /> <sf:input path="docNumber" cssStyle="width: 30%;"/> <sf:errors path="docNumber" /></h3>
                <h3 style="width: 30%;"><spring:message code="order.add.form.date" /> <sf:input path="orderDate" cssStyle="width: 30%;"/> <sf:errors path="orderDate"/> </h3>
            </header>
            <div class="module_content">
                <fieldset style="width:48%; float:left;">
                    <label id="clientData"><spring:message code="order.add.form.customerdata" />:</label>
                    <label id="clientName" style="clear: left;"></label>
                    <label id="clientAddress" style="clear: left; width: 300px;"></label>
                    <sf:hidden path="customer.id" />
                </fieldset><div class="clear"></div>

                <!--second row -->
                <div>
                    <fieldset style="width:25%; float:left;  margin-right: 2%; padding-right: 0.5%;">
                        <label><spring:message code="order.add.form.item" />:</label>
                        <select name="orderDetails[0].item.id" cssStyle="width:92%;">
                            <option value="NONE"><spring:message code="order.add.form.select" /></option>
                            <c:forEach items="${items}" var="item">
                                <option value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>
                        <%--<sf:errors path="orderDetails[0].item.id" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:20%; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label style="width: 20%"><spring:message code="order.add.form.quantity" />:</label>
                        <input name="orderDetails[0].quantity" cssStyle="width:92%;"/>
                        <%--<sf:errors path="orderDetails[0].quantity" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:25%; float:left; margin-right: 2%; padding-right: 0.5%;">
                        <label><spring:message code="order.add.form.reason" />:</label>
                        <select name="orderDetails[0].reason.id" cssStyle="width:92%;">
                            <c:forEach items="${reasons}" var="reason">
                                <option value="${reason.id}">${reason.description}</option>
                            </c:forEach>
                        </select>
                        <%--<sf:errors path="orderDetails[0].priceGross" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:2%; float:left; margin-right: 2%; padding-right: 0.1%; padding-top: 1%;padding-bottom: 1%;">
                        <input type="button" class="new add" value="+" />
                    </fieldset>
                    <div class="clear"></div>
                </div>

            </div>
            <footer>
                <div class="submit_link">
                    <%--<input id="reset" type="button" value="${reset}"/>--%>
                    <input id="add" type="submit" value="${add}" class="alt_btn">
                </div>
            </footer>
        </sf:form>
    </article><!-- end of post new article -->
</section>
