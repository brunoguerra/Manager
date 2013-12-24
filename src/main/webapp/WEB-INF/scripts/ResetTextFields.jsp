<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    //reset inputboxe values
    $(document).ready(function()
            {
                $("#reset").click(function()
                        {
                            $('.module_content input[type="text"]').val('');
                        }
                );
            }
    );
</script>
