<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>GeekLister</title>
</head>

<body>

<div class="row">

    <div class="large-12 columns">
        <h2>
            ${geekList?.name}
        </h2>

        <g:render template="/partials/geeklist_table" model="['items':geekList?.items]"/>

    </div>

</div>
<g:render template="/partials/tableinit"/>
</body>
</html>