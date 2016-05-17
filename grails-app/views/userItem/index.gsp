<html>
<head>
    <meta name="layout" content="main"/>
    <title>GeekLister Items</title>
</head>

<body>

<div class="row">

    <div class="large-12 columns">
        <h2>Items for ${sec.username()}</h2>

        <h3>H-Index is: ${hindex}</h3>
        <br/>

        <g:render template="/partials/geeklist_table"/>

</div>
<g:render template="/partials/tableinit"/>
</body>
</html>