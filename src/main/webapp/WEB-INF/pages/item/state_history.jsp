<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main" class="column">
    <c:choose>
        <c:when test="${not empty historyPage.content}">
            <spring:message code="item.history.back" var="back"/>
            <spring:message code="item.history.next" var="next"/>

            <article class="module width_full">
                <header><h3 class="tabs_involved">[ ${state.item.name} ] <spring:message code="item.history.title"/>: ${state.currentState.intValue()}</h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th><spring:message code="item.history.date"/></th>
                                <th><spring:message code="item.history.description"/></th>
                            </tr>
                            </thead>
                            <c:forEach var="item" items="${historyPage.content}" >
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td><c:out value="${item.date}"/></td>
                                    <td><c:out value="${item.description}"/></td>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div><!-- end of #tab1 -->
                </div><!-- end of .tab_container -->
                <footer>
                    <div class="submit_link">
                        <spring:url value="" var="nextUrl">
                            <spring:param name="page" value="${historyPage.number + 1}"></spring:param>
                        </spring:url>
                        <spring:url value="" var="prevUrl">
                            <spring:param name="page" value="${historyPage.number - 1}"></spring:param>
                        </spring:url>

                        <c:if test="${historyPage.number != 0 && !historyPage.firstPage}">
                            <a href="${prevUrl}"><input id="back" type="button" value="${back}"/></a>
                        </c:if>
                        <c:if test="${historyPage.totalPages > 1 && !historyPage.lastPage}">
                            <a href="${nextUrl}"><input id="next" type="button" value="${next}"/></a>
                        </c:if>
                    </div>
                </footer>
            </article><!-- end of content manager article -->
        </c:when>
    </c:choose>

</section>
