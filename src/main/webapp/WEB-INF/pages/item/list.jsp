<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="item.list.msg.q.delete" var="qDelete"/>

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

</script>
<section id="main" class="column">
    <c:if test="${itemAdded}">
        <h4 class="alert_success"><spring:message code="item.add.form.msg.success"/></h4>
    </c:if>
    <c:if test="${itemDeleted}">
        <h4 class="alert_success"><spring:message code="item.delete.form.msg.success"/></h4>
    </c:if>
    <c:if test="${itemEdited}">
        <h4 class="alert_success"><spring:message code="item.edit.form.msg.success"/></h4>
    </c:if>
    <c:choose>
        <c:when test="${not empty items}">
            <spring:message code="item.list.table.action.edit" var="edit" />
            <spring:message code="item.list.table.action.delete" var="delete" />
            <spring:message code="item.list.table.action.editState" var="editState" />
            <spring:message code="item.list.table.name" var="name"/>
            <spring:message code="item.list.table.priceGross" var="priceGross"/>
            <spring:message code="item.list.table.priceNet" var="priceNet" />
            <spring:message code="item.list.table.priceExcise" var="priceExcise" />
            <spring:message code="item.list.table.state" var="state"/>
            <spring:message code="item.list.table.action" var="action" />
            <spring:message code="item.delete.table.default.currentState" var="currentStateDefault"/>
            <spring:message code="item.list.stateChange.save" var="save"/>
            <spring:message code="item.list.stateChange.cancel" var="cancel"/>


            <article class="module width_full">
                <header><h3 class="tabs_involved"><spring:message code="item.list.title"/></h3></header>

                <div class="tab_container">
                    <div id="tab1" class="tab_content">

                        <table class="tablesorter" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>${name}</th>
                                <th>${priceGross}</th>
                                <th>${priceNet}</th>
                                <th>${priceExcise}</th>
                                <th>${state}</th>
                                <th>${action}</th>
                            </tr>
                            </thead>
                            <c:forEach var="item" items="${items}">
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td><c:out value="${item.name}"/></td>
                                        <td><c:out value="${item.priceGross}"/></td>
                                        <td><c:out value="${item.priceNet}"/></td>
                                        <td><c:out value="${item.priceExcise}"/></td>
                                        <td>
                                            <a id="currentState_${item.state.id}" href="${contextPath}/item/state/${item.state.id}/history"><c:out value="${item.state.currentState.intValue()}" default="${currentStateDefault}"/></a>
                                            <input type="button" id="stateChangeButton" value="+" onclick="return show(${item.state.id});">

                                        </td>
                                        <td>
                                            <a href="${contextPath}/item/edit/${item.id}" style="float: left;" ><input type="image" src="<c:url value="/resources/images/icn_edit.png"/>" title="${edit}"></a>
                                            <form id="deleteForm" method="post" action="${contextPath}/item/delete" onsubmit="return confirmDelete();">
                                                <input name="id" type="hidden" value="${item.id}"/>
                                                <a href="#">
                                                    <input id="delete" type="image" src="<c:url value="/resources/images/icn_trash.png"/>" title="${delete}">
                                                </a>
                                            </form>
                                        </td>
                                    </tr>
                                    <tr id="extraRow_${item.state.id}" style="display: none;">
                                        <td colspan="7">
                                            <div style="margin-left: 60%">
                                                <form id="stateChange">
                                                    <label><spring:message code="item.list.stateChange.label" />: </label>
                                                    <input type="text" id="stateChangeValue_${item.state.id}" name="stateChangeValue_${item.state.id}" />
                                                    <input id="stateChangeSave_${item.state.id}" type="button" value="${save}" class="alt_btn" onclick="return send(${item.state.id});" />
                                                    <input id="stateChangeCancel_${item.state.id}" type="button" value="${cancel}" onclick="return cancel(${item.state.id});" />
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div><!-- end of #tab1 -->
                </div><!-- end of .tab_container -->
                <footer>
                    <div></div>
                </footer>
            </article><!-- end of content manager article -->
        </c:when>
        <c:otherwise>
            <h4 class="alert_info"><spring:message code="item.list.msg.nodata" /></h4>
        </c:otherwise>
    </c:choose>

</section>