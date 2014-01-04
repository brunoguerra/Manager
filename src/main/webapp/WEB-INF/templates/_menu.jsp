<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<aside id="sidebar" class="column">
    <form class="quick_search">
        <input type="text" value="Quick Search" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
    </form>
    <hr/>
    <h3><spring:message code="menu.order" /></h3>
    <ul class="toggle">
        <li class="icn_new_article"><a href="${contextPath}/order/add"><spring:message code="menu.order.add" /></a></li>
        <li class="icn_categories"><a href="${contextPath}/order/list"><spring:message code="menu.order.list" /></a></li>
    </ul>
    <h3><spring:message code="menu.customer"/></h3>
    <ul class="toggle">
        <li class="icn_add_user"><a href="${contextPath}/customer/add"><spring:message code="menu.customer.add"/></a></li>
        <li class="icn_view_users"><a href="${contextPath}/customer/list"><spring:message code="menu.customer.list"/></a></li>
    </ul>
    <h3><spring:message code="menu.item" /></h3>
    <ul class="toggle">
        <li class="icn_new_article"><a href="${contextPath}/item/add"><spring:message code="menu.item.add" /></a></li>
        <li class="icn_categories"><a href="${contextPath}/item/list"><spring:message code="menu.item.list" /></a></li>
    </ul>
    <sec:authorize access="hasRole('ROLE_JURASZ')">
        <h3><spring:message code="menu.invoice" /></h3>
        <ul class="toggle">
            <li class="icn_new_article"><a href="${contextPath}/invoice/add"><spring:message code="menu.invoice.add" /></a></li>
            <li class="icn_categories"><a href="${contextPath}/invoice/list"><spring:message code="menu.invoice.list" /></a></li>
        </ul>
    </sec:authorize>
    <h3><spring:message code="menu.report" /></h3>
    <ul class="toggle">
        <li class="icn_new_article"><a href="${contextPath}/report/add"><spring:message code="menu.report.add" /></a></li>
        <li class="icn_categories"><a href="${contextPath}/report/list"><spring:message code="menu.report.list" /></a></li>
    </ul>
    <h3><spring:message code="menu.account" /></h3>
    <ul class="toggle">
        <form id="logout" action="${contextPath}/j_spring_security_logout" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <li class="icn_jump_back"><a href="#" onclick="return $('#logout').submit();"><spring:message code="menu.login.logout" /></a></li>
        </form>
    </ul>

    <footer>
        <hr />
        <p><strong>Copyright &copy; 2011 Website Admin</strong></p>
        <p>Theme by <a href="http://www.medialoot.com">MediaLoot</a></p>
    </footer>
</aside><!-- end of sidebar -->