<%--
  Created by IntelliJ IDEA.
  User: mikeh
  Date: 9/15/15
  Time: 8:40 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>GeekLister</title>
</head>

<body>

<div class="row">

    <div class="large-12 columns">
        <h2>Most Recent Items</h2>

        <g:render template="/partials/geeklist_table" />

    </div>

</div>
<g:render template="/partials/tableinit"/>
</body>
</html>