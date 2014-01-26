<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<spring:message code="customer.add.form.button.add" var="add" />
<spring:message code="customer.add.form.button.reset" var="reset" />
<spring:message code="order.add.search" var="search" />

<%@include file="/WEB-INF/scripts/ClientVatAutocomplete.jsp"%>
<script src="<c:url value="/resources/js/jquery.ui.datepicker-pl.js" />" ></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#order\\.orderDate" ).datepicker();
        $("#order\\.orderDate").attr('autocomplete','off');
    });
</script>
<script type="text/javascript">

    $(document).ready(function(){

        //set current date
        var myDate = new Date();
        var prettyDate = myDate.getDate() + '-' +  ( '0' + (myDate.getMonth()+1) ).slice( -2 ) + '-' +
                myDate.getFullYear();
        $("#order\\.orderDate").val(prettyDate);

        $('#paymentDate').hide();
        $('#paymentDate').datepicker();

        var cid = ${sessionScope.customer.id == null ? -1 : sessionScope.customer.id};
        if(cid > 0) {
            $("#clientName").text( "" + '${sessionScope.customer.name}' + "");
            $("#clientAddress").text( "" + '${sessionScope.customer.address.city}' + " " + '${sessionScope.customer.address.postCode}' + " " + '${sessionScope.customer.address.street}' + " " + '${sessionScope.customer.address.number}' + "");
            $("#clientNip").text("" + '${sessionScope.customer.nip}' + "" );
            $("#order\\.customer\\.id").val(cid);
        }
        //add remove data row
        $('.new').click(function(){
            if($(this).attr('class') == 'add remove') {
                $(this).parent().parent().remove();
            } else {
                var thisRow = $(this).parent().parent();
                newRow = thisRow.clone(true).insertAfter(thisRow);
                newRow.find('input:not(.add)').val("");
                var q = newRow.find('input');
                var number = parseInt(getNumber(q));


                var pricegross = newRow.find("input[name=order\\.orderDetails\\[" + number +"\\]\\.priceGross]");
                pricegross.attr('name', getNeName(pricegross.attr('name')));

                var pricenet = newRow.find("[name=order\\.orderDetails\\[" + number +"\\]\\.priceNet]");
                pricenet.attr('name', getNeName(pricenet.attr('name')));

                var pricegrossexcise = newRow.find("[name=order\\.orderDetails\\[" + number +"\\]\\.priceGrossExcise]");
                pricegrossexcise.attr('name', getNeName(pricegrossexcise.attr('name')));

                var pricenetexcise = newRow.find("[name=order\\.orderDetails\\[" + number +"\\]\\.priceNetExcise]");
                pricenetexcise.attr('name', getNeName(pricenetexcise.attr('name')));

                var quantity = newRow.find("[name=order\\.orderDetails\\[" + number +"\\]\\.quantity]");
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

    function checkClient() {
        if($('#order\\.customer\\.id').val() == '') {
            $("#missingclient").show();
            return false;
        }
        return true;
    }

    function getNumber(obj) {

        var text = obj.attr('name').split('[');
        var number = text[1].split(']');
        return number[0];
    }

    function calculate(obj) {
        var number = getNumber(obj);
        var id = $("[name=order\\.orderDetails\\[" + number +"\\]\\.item\\.id]").val();
        var quantity = $("[name=order\\.orderDetails\\[" + number +"\\]\\.quantity]").val();
        $.ajax({
            url: '${contextPath}/invoice/calculateInvoice',
            data: {
                id: id, quantity: quantity
            },
            dataType: 'json',
            success: function(data) {
                $("[name=order\\.orderDetails\\[" + number +"\\]\\.priceGross]").val(data.priceGross);
                $("[name=order\\.orderDetails\\[" + number +"\\]\\.priceNet]").val(data.priceNet);
                $("[name=order\\.orderDetails\\[" + number +"\\]\\.priceGrossExcise]").val(data.priceGrossExcise);
                $("[name=order\\.orderDetails\\[" + number +"\\]\\.priceNetExcise]").val(data.priceNetExcise);
            }
        });
        return true;
    }

    function paymentChange() {
        if( $('#payment').val() == 'TRANSFER') {
            $('#paymentDate').show();
        } else {
            $('#paymentDate').hide();
        }
    }
</script>

<section id="main" class="column">

            <h4 id="missingclient" class="alert_warning" style="display: none;"><spring:message code="order.add.form.missingcustomer"/></h4>

            <article class="module width_full">
            <form class="quick_search_custom">
            <input id="search" name="search" type="text" value="${search}" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
            </form>
            <sf:form modelAttribute="invoiceForm" method="post" onsubmit="return checkClient();">
                <joda:format var="date" value="${order.orderDate}" pattern="dd-MM-yyyy"/>
                <header>
                    <h3 style="width: 30%;"><spring:message code="order.add.form.title" /> <sf:input path="order.invoiceNumber" cssStyle="width: 30%;"/> <sf:errors path="order.invoiceNumber" /></h3>
                    <h3 style="width: 30%;"><spring:message code="order.add.form.date" /> <sf:input path="order.orderDate" cssStyle="width: 30%;"/> <sf:errors path="order.invoiceNumber" /></h3>
                </header>
                <div class="module_content">
                    <fieldset style="width:48%; float:left; margin-right: 3px;">
                        <label id="clientData"><spring:message code="order.add.form.customerdata" />:</label>
                        <label id="clientName" style="clear: left; width: 300px;"></label>
                        <label id="clientAddress" style="clear: left; width: 300px;"></label>
                        <label id="clientNip" style="clear: left;"></label>
                        <sf:hidden path="order.customer.id" />
                    </fieldset>
                    <fieldset style="width:48%; float:left;">
                        <sf:checkbox path="grossInvoice" label="Faktura brutto" /><div class="clear">
                        <sf:checkbox path="excise" label="Akcyza" />
                        <select id="payment" name="payment" onchange="return paymentChange();" >
                            <c:forEach items="${payments}" var="item">
                                <option value="${item}">${item.description}</option>
                            </c:forEach>
                        </select>
                        <sf:input path="paymentDate" cssStyle="margin-top: 15px; width: 100px;"></sf:input>
                    </fieldset><div class="clear"></div>

                <!--second row -->
                <div>
                    <fieldset style="width:25%; float:left;  margin-right: 2%; padding-right: 0.5%;">
                        <label><spring:message code="order.add.form.item" />:</label>
                        <select name="order.orderDetails[0].item.id" cssStyle="width:92%;">
                            <option value="NONE"><spring:message code="order.add.form.select" /></option>
                            <c:forEach items="${items}" var="item">
                                <option value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>
                        <%--<sf:errors path="orderDetails[0].item.id" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:1px; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label><spring:message code="order.add.form.quantity" />:</label>
                        <input name="order.orderDetails[0].quantity" cssStyle="width:92%;" onkeyup="return calculate($(this));"/>
                        <%--<sf:errors path="orderDetails[0].quantity" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:25%; float:left; margin-right: 2%; padding-right: 0.5%;">
                        <label><spring:message code="order.add.form.reason" />:</label>
                        <select name="order.orderDetails[0].reason.id" cssStyle="width:92%;">
                            <c:forEach items="${reasons}" var="reason">
                                <option value="${reason.id}">${reason.description}</option>
                            </c:forEach>
                        </select>
                            <%--<sf:errors path="orderDetails[0].priceGross" cssClass="error_text"/>--%>
                    </fieldset><div class="clear"></div>

                    <fieldset style="width:1px; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label>Cena brutto:</label>
                        <input name="order.orderDetails[0].priceGross" cssStyle="width:92%;"/>
                            <%--<sf:errors path="orderDetails[0].quantity" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:1px; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label>Cena netto:</label>
                        <input name="order.orderDetails[0].priceNet" cssStyle="width:92%;"/>
                            <%--<sf:errors path="orderDetails[0].quantity" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:1px; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label>Cena brutto (akc):</label>
                        <input name="order.orderDetails[0].priceGrossExcise" cssStyle="width:92%;"/>
                            <%--<sf:errors path="orderDetails[0].quantity" cssClass="error_text"/>--%>
                    </fieldset>
                    <fieldset style="width:1px; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label>Cena netto (akc):</label>
                        <input name="order.orderDetails[0].priceNetExcise" cssStyle="width:92%;"/>
                            <%--<sf:errors path="orderDetails[0].quantity" cssClass="error_text"/>--%>
                    </fieldset>

                    <fieldset style="width:2%; float:left; margin-right: 2%; padding-right: 0.1%; padding-top: 1%;padding-bottom: 1%;">
                        <input type="button" class="new add" value="+" />
                    </fieldset>

                    <div class="clear"></div><div class="clear"></div>
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
