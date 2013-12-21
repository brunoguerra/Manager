<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<spring:message code="customer.list.msg.q.delete" var="qDelete"/>

<script type="text/javascript">
    function confirmDelete() {
        if (confirm("${qDelete}")) {
//            $("#deleteForm").submit();
            alert('tak');
            return true;
        }
        alert('nie');
        return false;
    }
</script>
<section id="main" class="column">
    <c:if test="${customerAdded}">
        <h4 class="alert_success"><spring:message code="customer.add.form.msg.success"/></h4>
    </c:if>
    <c:if test="${customerUpdated}">
        <h4 class="alert_success"><spring:message code="customer.edit.form.msg.success"/></h4>
    </c:if>
    <c:if test="${customerDeleted}">
        <h4 class="alert_success"><spring:message code="customer.delete.form.msg.success"/></h4>
    </c:if>
    <c:choose>
        <c:when test="${not empty customers}">
            <spring:message code="customer.list.table.action.edit" var="edit" />
            <spring:message code="customer.list.table.action.delete" var="delete" />
            <spring:message code="customer.list.table.secondandfirstname" var="secongandfirstname"/>
            <spring:message code="customer.list.table.postcode" var="postcode"/>
            <spring:message code="customer.list.table.city" var="city"/>
            <spring:message code="customer.list.table.street" var="street" />
            <spring:message code="customer.list.table.action" var="action" />
            <article class="module width_full">
                <header><h3 class="tabs_involved"><spring:message code="customer.list.title"/></h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">
                        <%--<datatables:table id="customers" data="${customers}" cdn="true" row="customer" theme="bootstrap2" cssClass="tablesorter" paginate="true" info="false"  paginationType="two_button">--%>
                            <%--<datatables:column title="${secongandfirstname}">--%>
                                <%--<c:out value="${customer.firstName} ${customer.lastName}"></c:out>--%>
                            <%--</datatables:column>--%>
                            <%--<datatables:column title="${city}">--%>
                                <%--<c:out value="${customer.address.city}"></c:out>--%>
                            <%--</datatables:column>--%>
                            <%--<datatables:column title="${postcode}">--%>
                                <%--<c:out value="${customer.address.postCode}"></c:out>--%>
                            <%--</datatables:column>--%>
                            <%--<datatables:column title="${street}">--%>
                                <%--<c:out value="${customer.address.street}"></c:out>--%>
                            <%--</datatables:column>--%>
                            <%--<datatables:column title="${action}">--%>
                                <%--<a href="${contextPath}/customer/edit/${customer.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_edit.png"/>" title="${edit}"></a>--%>
                                <%--<form id="deleteForm" method="post" action="${contextPath}/customer/delete">--%>
                                <%--<input name="id" type="hidden" value="${customer.id}"/>--%>
                                <%--<a href="#" onclick="confirmDelete();">--%>
                                <%--<input id="delete" type="image" src="<c:url value="/resources/images/icn_trash.png"/>" title="${delete}">--%>
                                <%--</a>--%>
                                <%--</form>--%>
                            <%--</datatables:column>--%>
                        <%--</datatables:table>--%>

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th><spring:message code="customer.list.table.secondandfirstname"/></th>
                                <th><spring:message code="customer.list.table.city" /></th>
                                <th><spring:message code="customer.list.table.postcode" /></th>
                                <th><spring:message code="customer.list.table.street" /></th>
                                <th><spring:message code="customer.list.table.number" /></th>
                                <th><spring:message code="customer.list.table.action" /></th>
                            </tr>
                            </thead>
                            <c:forEach var="customer" items="${customers}">
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td><c:out value="${customer.lastName} ${customer.firstName}"/></td>
                                        <td><c:out value="${customer.address.city}"/></td>
                                        <td><c:out value="${customer.address.postCode}"/></td>
                                        <td><c:out value="${customer.address.street}"/></td>
                                        <td></td>
                                        <td>
                                            <a href="${contextPath}/customer/edit/${customer.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_edit.png"/>" title="${edit}"></a>
                                            <form id="deleteForm" method="post" action="${contextPath}/customer/delete">
                                                <input name="id" type="hidden" value="${customer.id}"/>
                                                <a href="#" onclick="confirmDelete();">
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

            </article><!-- end of content manager article -->
        </c:when>
        <c:otherwise>
            <h4 class="alert_info"><spring:message code="customer.list.msg.nodata" /></h4>
        </c:otherwise>
    </c:choose>

</section>