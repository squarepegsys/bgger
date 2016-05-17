<html>
<head>
    <meta name="layout" content="main"/>
    <title>Search Result</title>
</head>

<body>

<div class="row">

    <div class="medium-2 columns"></div>

    <div class="medium-8 columns">

        <h2>What did we find?</h2>
        <ul class="search-result small-block-grid-2">
            <g:each in="${results}" var="result">
                <li><g:link controller="game" action="index"
                            id="${result.game.id}">${result.game.name}</g:link>   <br/>  ${result.total} times</li>

            </g:each>
        </ul>
    </div>

    <div class="medium-2 columns"></div>
</div>

</body>
</html>