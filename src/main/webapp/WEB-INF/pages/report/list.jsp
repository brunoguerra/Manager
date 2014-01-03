<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<spring:message code="report.list.msg.q.delete" var="qDelete"/>

<script type="text/javascript">

    function confirmDelete() {
        if (confirm("${qDelete}")) {
            return true;
        }
        return false;
    }

</script>
<section id="main" class="column">
    <c:if test="${reportAdded}">
        <h4 class="alert_success"><spring:message code="report.add.msg.success"/></h4>
    </c:if>
    <c:if test="${reportDeleted}">
        <h4 class="alert_success"><spring:message code="report.delete.form.msg.success"/></h4>
    </c:if>
    <c:choose>
        <c:when test="${not empty reportPage.content}">
            <spring:message code="report.list.action.print" var="print" />
            <spring:message code="report.list.action.delete" var="delete" />
            <spring:message code="report.list.creationDate" var="creationDate" />
            <spring:message code="report.list.startDate" var="startDate" />
            <spring:message code="report.list.endDate" var="endDate" />
            <spring:message code="report.list.action" var="action" />
            <spring:message code="report.list.table.next" var="next" />
            <spring:message code="report.list.table.back" var="back" />


            <article class="module width_full">
                <header><h3 class="tabs_involved"><spring:message code="report.list.title"/></h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>${creationDate}</th>
                                <th>${startDate}</th>
                                <th>${endDate}</th>
                                <th>${action}</th>
                            </tr>
                            </thead>
                            <c:forEach var="report" items="${reportPage.content}">
                                <tbody>
                                    <tr>
                                        <td>
                                            <a href="${contextPath}/report/show/${report.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_print.png"/>" title="${print}"></a>
                                        </td>
                                        <td><joda:format value="${report.creationDate}" pattern="dd/MM/yyyy"/></td>
                                        <td><joda:format value="${report.startDate}" pattern="dd/MM/yyyy"/></td>
                                        <td><joda:format value="${report.endDate}" pattern="dd/MM/yyyy"/></td>
                                        <td>
                                            <form id="deleteForm" method="post" action="${contextPath}/report/delete" onsubmit="return confirmDelete();">
                                                <input name="id" type="hidden" value="${report.id}"/>
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
                            <spring:param name="page" value="${reportPage.number + 1}"></spring:param>
                        </spring:url>
                        <spring:url value="" var="prevUrl">
                            <spring:param name="page" value="${reportPage.number - 1}"></spring:param>
                        </spring:url>

                        <c:if test="${reportPage.number != 0 && !reportPage.firstPage}">
                            <a href="${prevUrl}"><input id="back" type="button" value="${back}"/></a>
                        </c:if>
                        <c:if test="${reportPage.totalPages > 1 && !reportPage.lastPage}">
                            <a href="${nextUrl}"><input id="next" type="button" value="${next}"/></a>
                        </c:if>
                    </div>
                </footer>
            </article><!-- end of content manager article -->
        </c:when>
        <c:otherwise>
            <h4 class="alert_info"><spring:message code="report.list.msg.nodata" /></h4>
        </c:otherwise>
    </c:choose>

</section>