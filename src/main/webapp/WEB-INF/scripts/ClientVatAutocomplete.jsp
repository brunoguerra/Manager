<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code="order.add.search.nodata" var="nodata" />
<spring:message code="order.add.search" var="search" />
<script type="text/javascript">



    $(document).ready(function() {
        //attach autocomplete
        $("#search").autocomplete({
            minLength: 1,
            delay: 100,
            //define callback to format results
            source: function (request, response) {
                $.getJSON("${contextPath}/customer/getCustomersVatForQuery", request, function(result) {
                    response($.map(result, function(item) {
                        return {
                            // following property gets displayed in drop down
                            label: item.name,
                            // following property gets entered in the textbox
                            id: item.id,
                            clientName: item.name,
                            clientAddress: item.address.postCode + " " + item.address.city + " " + item.address.street + " " + item.address.number,
                            clientNip: item.nip
                        }
                    }));
                });
            },

            //todo: intetrnationalization
            //define select handler
            select : function(event, ui) {
                if(ui.item.value == "${nodata}") {
                    window.location.replace('${contextPath}/customer/add-vat?redirect=true&target=/invoice/add');
                    return false;
                }
                if (ui.item) {
                    event.preventDefault();
                    $("#clientName").text(ui.item.clientName);
                    $("#clientAddress").text(ui.item.clientAddress);
                    $("#clientNip").text(ui.item.clientNip);
                    $("#order\\.customer\\.id").val(ui.item.id);
                    $("#search").val("${search}");
                    $("#add").focus();
                    return false;
                }
            },

            response: function(event, ui) {
                if (!ui.content.length) {
                    var noResult = { value:"${nodata}",label:"${nodata}" };
                    ui.content.push(noResult);
                }
            }
        });
    });

    //set autocomplete off
    $(document).ready(function(){
        $('#search').attr('autocomplete','on');
    });

</script>