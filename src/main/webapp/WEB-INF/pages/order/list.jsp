<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<spring:message code="order.list.msg.q.delete" var="qDelete"/>

<script type="text/javascript">

    function confirmDelete() {
        if (confirm("${qDelete}")) {
            return true;
        }
        return false;
    }

    function send(id) {
        $.ajax({
            url: "${contextPath}/item/state/" + id + "/change",
            type: "POST",
            data: "value=" + $('#stateChangeValue_' + id).val(),
            success: function(result) {
                $('#currentState_' + id).text(result);
                $('#stateChangeValue_' + id).val('');
                show(id);
            }
        });
    }

    function show(id) {
        $('#extraRow_' + id).toggle('slow');
    }

    function cancel(id) {
        $('#stateChangeValue_' + id).val('');
        $('#extraRow_' + id).toggle('slow');
    }

    function showOrder(id) {
        window.open('${contextPath}/order/show/' + id, '_blank');
    }

</script>
<section id="main" class="column">
    <c:if test="${orderAdded}">
        <h4 class="alert_success"><spring:message code="order.add.msg.success"/></h4>
    </c:if>
    <c:if test="${orderDeleted}">
        <h4 class="alert_success"><spring:message code="order.delete.form.msg.success"/></h4>
    </c:if>
    <c:if test="${orderEdited}">
        <h4 class="alert_success"><spring:message code="order.edit.form.msg.success"/></h4>
    </c:if>
    <c:choose>
        <c:when test="${not empty orderPage.content}">
            <spring:message code="order.list.action.print" var="print" />
            <spring:message code="order.list.action.edit" var="edit" />
            <spring:message code="order.list.action.delete" var="delete" />
            <spring:message code="order.list.docNumber" var="docNumber" />
            <spring:message code="order.list.date" var="date" />
            <spring:message code="order.list.customer" var="customer" />
            <spring:message code="order.list.action" var="action" />
            <spring:message code="order.list.table.next" var="next" />
            <spring:message code="order.list.table.back" var="back" />


            <article class="module width_full">
                <header><h3 class="tabs_involved"><spring:message code="order.list.title"/></h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>${docNumber}</th>
                                <th>${date}</th>
                                <th>${customer}</th>
                                <th>${action}</th>
                            </tr>
                            </thead>
                            <c:forEach var="order" items="${orderPage.content}">
                                <c:if test="${not empty order.documentName}">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <a href="#" style="float: left;" onclick="return showOrder(${order.id});" ><input type="image" src="<c:url value="/resources/images/icn_print.png"/>" title="${print}"></a>
                                            </td>
                                            <td><c:out value="${order.docNumber}"/></td>
                                            <td><joda:format value="${order.orderDate}" pattern="dd-MM-yyyy"/></td>
                                            <c:choose>
                                                <c:when test="${order.invoice}">
                                                    <td><c:out value="${order.customer.name} "/></td>
                                                </c:when>
                                                <c:when test="${not order.invoice}">
                                                    <td><c:out value="${order.customer.lastName} ${order.customer.firstName} "/></td>
                                                </c:when>
                                            </c:choose>
                                            <td>
                                                <a href="${contextPath}/order/edit/${order.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_edit.png"/>" title="${edit}"></a>
                                                <form id="deleteForm" method="post" action="${contextPath}/order/delete" onsubmit="return confirmDelete();">
                                                    <input name="id" type="hidden" value="${order.id}"/>
                                                    <a href="#">
                                                        <input id="delete" type="image" src="<c:url value="/resources/images/icn_trash.png"/>" title="${delete}">
                                                    </a>
                                                </form>
                                            </td>
                                        </tr>
                                    </tbody>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div><!-- end of #tab1 -->
                </div><!-- end of .tab_container -->
                <footer>
                    <div class="submit_link">
                        <spring:url value="" var="nextUrl">
                            <spring:param name="page" value="${orderPage.number + 1}"></spring:param>
                        </spring:url>
                        <spring:url value="" var="prevUrl">
                            <spring:param name="page" value="${orderPage.number - 1}"></spring:param>
                        </spring:url>

                        <c:if test="${orderPage.number != 0 && !orderPage.firstPage}">
                            <a href="${prevUrl}"><input id="back" type="button" value="${back}"/></a>
                        </c:if>
                        <c:if test="${orderPage.totalPages > 1 && !orderPage.lastPage}">
                            <a href="${nextUrl}"><input id="next" type="button" value="${next}"/></a>
                        </c:if>
                    </div>
                </footer>
            </article><!-- end of content manager article -->
        </c:when>
        <c:otherwise>
            <h4 class="alert_info"><spring:message code="order.list.msg.nodata" /></h4>
        </c:otherwise>
    </c:choose>

</section>