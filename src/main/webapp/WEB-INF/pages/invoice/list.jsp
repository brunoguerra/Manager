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

</script>
<section id="main" class="column">
    <c:if test="${invoiceAdded}">
        <h4 class="alert_success"><spring:message code="order.add.msg.success"/></h4>
    </c:if>
    <c:if test="${invoiceDeleted}">
        <h4 class="alert_success"><spring:message code="order.delete.form.msg.success"/></h4>
    </c:if>
    <c:choose>
        <c:when test="${not empty orderPage.content}">


            <article class="module width_full">
                <header><h3 class="tabs_involved">Lista faktór VAT</h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>Numer faktury</th>
                                <th>Data</th>
                                <th>Klient</th>
                                <th>Akcja</th>
                            </tr>
                            </thead>
                            <c:forEach var="order" items="${orderPage.content}">
                                <tbody>
                                    <tr>
                                        <td>
                                            <a href="${contextPath}/invoice/show-vat/${order.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_print.png"/>" title="Drukuj Fakture"></a>
                                            <c:if test="${not empty order.documentName}">
                                                <a href="${contextPath}/order/show/${order.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_print.png"/>" title="Drukuj dokument"></a>
                                            </c:if>
                                        </td>
                                        <td><c:out value="${order.documentInvoiceName}"/></td>
                                        <td><joda:format value="${order.orderDate}" pattern="dd/MM/yyyy"/></td>
                                        <td><c:out value="${order.customer.name} "/></td>
                                        <td>
                                            <form id="deleteForm" method="post" action="${contextPath}/invoice/delete" onsubmit="return confirmDelete();">
                                                <input name="id" type="hidden" value="${order.id}"/>
                                                <a href="#">
                                                    <input id="delete" type="image" src="<c:url value="/resources/images/icn_trash.png"/>" title="${delete}">
                                                </a>
                                            </form>
                                        </td>
                                    </tr>
                                </tbody>
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
                            <a href="${prevUrl}"><input id="back" type="button" value="Poprzednia"/></a>
                        </c:if>
                        <c:if test="${orderPage.totalPages > 1 && !orderPage.lastPage}">
                            <a href="${nextUrl}"><input id="next" type="button" value="Następna"/></a>
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