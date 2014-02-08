<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" >
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title><tiles:insertAttribute name="title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/layout.css" />" type="text/css" media="screen" />
    <!--[if lt IE 9]>
    <link rel="stylesheet" href=""<c:url value="/resources/css/ie.css" />" type="text/css" media="screen" />
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <%--<script src="<c:url value="/resources/js/jquery-1.5.2.min.js" />" type="text/javascript"></script>--%>
    <script src="<c:url value="/resources/js/hideshow.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.tablesorter.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.equalHeight.js" />"></script>
    <script type="text/javascript">
        $(document).ready(function()
                {
                    $(".tablesorter").tablesorter();
                }

        );
        $(document).ready(function() {
            $( ":input").attr('autocomplete','off');

        });
        $(document).ready(function() {

            //When page loads...
            $(".tab_content").hide(); //Hide all content
            $("ul.tabs li:first").addClass("active").show(); //Activate first tab
            $(".tab_content:first").show(); //Show first tab content

            //On Click Event
            $("ul.tabs li").click(function() {

                $("ul.tabs li").removeClass("active"); //Remove any "active" class
                $(this).addClass("active"); //Add_Coal "active" class to selected tab
                $(".tab_content").hide(); //Hide all tab content

                var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
                $(activeTab).fadeIn(); //Fade in the active ID content
                return false;
            });
        });

        $(document).ready(function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
        });
    </script>
    <script type="text/javascript">
        $(function(){
            $('.column').css("height", "100%");
        });
    </script>
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="menu"/>
<section id="main" class="column">
    <tiles:insertAttribute name="body"/>
</section>
</body>
</html>