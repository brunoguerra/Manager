<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="customer.add.form.button.add" var="add" />
<spring:message code="customer.add.form.button.reset" var="reset" />
<spring:message code="order.add.search" var="search" />

<%@include file="/WEB-INF/scripts/ClientAutocomplete.jsp"%>

<script type="text/javascript">

    $(document).ready(function(){

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

    function checkClient() {
        if($('#customer\\.id').val() == '') {
            $("#missingclient").show();
            return false;
        }
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
            <header><h3><spring:message code="order.add.form.title" /> <sf:input path="docNumber"/> <sf:errors path="docNumber" /></h3></header>
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
                    <fieldset style="width:25%; float:left;  margin-right: 2%; padding-right: 0.5%; padding-bottom: 15px;">
                        <label><spring:message code="order.add.form.quantity" />:</label>
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
