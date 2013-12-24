<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<aside id="sidebar" class="column">
    <form class="quick_search">
        <input type="text" value="Quick Search" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
    </form>
    <hr/>
    <h3>Content</h3>
    <ul class="toggle">
        <li class="icn_new_article"><a href="#">New Article</a></li>
        <li class="icn_edit_article"><a href="#">Edit Articles</a></li>
        <li class="icn_categories"><a href="#">Categories</a></li>
        <li class="icn_tags"><a href="#">Tags</a></li>
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
    <h3>Admin</h3>
    <ul class="toggle">
        <li class="icn_settings"><a href="#">Options</a></li>
        <li class="icn_security"><a href="#">Security</a></li>
        <li class="icn_jump_back"><a href="#">Logout</a></li>
    </ul>

    <footer>
        <hr />
        <p><strong>Copyright &copy; 2011 Website Admin</strong></p>
        <p>Theme by <a href="http://www.medialoot.com">MediaLoot</a></p>
    </footer>
</aside><!-- end of sidebar -->