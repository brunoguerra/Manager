<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:message code="customer.add.search.nodata" var="nodata" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript">

    $(document).ready(function() {
        //attach autocomplete
        $("#address\\.city").autocomplete({
            minLength: 1,
            delay: 500,
            //define callback to format results
            source: function (request, response) {
                $.getJSON("${contextPath}/customer/edit/getCities", request, function(result) {
                    response($.map(result, function(item) {
                        return {
                            // following property gets displayed in drop down
                            label: item.city + " (" + item.postCode + ")",
                            // following property gets entered in the textbox
                            city: item.city,
                            code: item.postCode
                        }
                    }));
                });
            },

            //define select handler
            select : function(event, ui) {
                if (ui.item) {
                    event.preventDefault();
                    $("#address\\.city").val(ui.item.city);
                    $("#address\\.postCode").val(ui.item.code);
                    return false;
                }
            },
            response: function(event, ui) {
                if (!ui.content.length) {
                    var noResult = { value:"",label:"${nodata}" };
                    ui.content.push(noResult);
                }
            }
        });
    });

    //set autocomplete off
    $(document).ready(function(){
        $('#address\\.city').attr('autocomplete','on');
    });

</script>