<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>--%>
<spring:message code="customer.list.msg.q.delete" var="qDelete"/>

<script type="text/javascript">
    function confirmDelete() {
        if (confirm("${qDelete}")) {
            return true;
        }
        return false;
    }
</script>
<section id="main" class="column">
    <c:set value="${not empty customersPage.content}" var="notEmptyContent"/>
    <c:if test="${customerVatAdded}">
        <h4 class="alert_success"><spring:message code="customer.add.form.msg.success"/></h4>
    </c:if>
    <c:if test="${customerVatUpdated}">
        <h4 class="alert_success"><spring:message code="customer.edit.form.msg.success"/></h4>
    </c:if>
    <c:if test="${customerVatDeleted}">
        <h4 class="alert_success"><spring:message code="customer.delete.form.msg.success"/></h4>
    </c:if>
    <c:choose>
        <c:when test="${notEmptyContent}">
            <spring:message code="customer.list.table.action.edit" var="edit" />
            <spring:message code="customer.list.table.action.delete" var="delete" />
            <spring:message code="customer.list.table.name" var="name"/>
            <spring:message code="customer.list.table.postcode" var="postcode"/>
            <spring:message code="customer.list.table.city" var="city"/>
            <spring:message code="customer.list.table.street" var="street" />
            <spring:message code="customer.list.table.action" var="action" />
            <spring:message code="customer.list.table.next" var="next" />
            <spring:message code="customer.list.table.back" var="back" />
            <spring:message code="customer.list.table.search" var="search" />

            <form class="quick_search_custom">
                <input type="text" value="${search}" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
            </form>

            <article class="module width_full">
                <header><h3 class="tabs_involved"><spring:message code="customerVat.list.title"/></h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th><spring:message code="customer.list.table.name"/></th>
                                <th><spring:message code="customer.list.table.city" /></th>
                                <th><spring:message code="customer.list.table.postcode" /></th>
                                <th><spring:message code="customer.list.table.street" /></th>
                                <th><spring:message code="customer.list.table.number" /></th>
                                <th><spring:message code="customer.list.table.action" /></th>
                            </tr>
                            </thead>
                            <c:forEach var="customer" items="${customersPage.content}">
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td><c:out value="${customer.name}"/></td>
                                        <td><c:out value="${customer.address.city}"/></td>
                                        <td><c:out value="${customer.address.postCode}"/></td>
                                        <td><c:out value="${customer.address.street}"/></td>
                                        <td><c:out value="${customer.address.number}"/></td>
                                        <td>
                                            <a href="${contextPath}/customer/edit-vat/${customer.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_edit.png"/>" title="${edit}"></a>
                                            <form id="deleteForm" method="post" action="${contextPath}/customer/delete-vat" onsubmit="return confirmDelete();">
                                                <input name="id" type="hidden" value="${customer.id}"/>
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
                            <spring:param name="page" value="${customersPage.number + 1}"></spring:param>
                        </spring:url>
                        <spring:url value="" var="prevUrl">
                            <spring:param name="page" value="${customersPage.number - 1}"></spring:param>
                        </spring:url>

                        <c:if test="${customersPage.number != 0 && !customersPage.firstPage}">
                            <a href="${prevUrl}"><input id="back" type="button" value="${back}"/></a>
                        </c:if>
                        <c:if test="${customersPage.totalPages > 1 && !customersPage.lastPage}">
                            <a href="${nextUrl}"><input id="next" type="button" value="${next}"/></a>
                        </c:if>
                    </div>
                </footer>
            </article><!-- end of content manager article -->
        </c:when>
        <c:otherwise>
            <h4 class="alert_info"><spring:message code="customer.list.msg.nodata" /></h4>
        </c:otherwise>
    </c:choose>

</section>