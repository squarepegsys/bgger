<html>
<head>
    <meta name="layout" content="main"/>
    <title>${game.name}</title>
</head>

<body>

<div class="row">

    <div class="large-12 columns">

     <h2>${game.name}</h2>

        <h4>Total Plays: <g:link controller="play" action="game" id="${game.id}">${playCount}</g:link></h4>
        <h4><a target="${game.bggId}" href="${game.url}">BGG Link</a></h4>
        <table class="fancy display  no-wrap" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Geeklist</th>
                <th>User</th>
                <th>Number of <br/> Comments</th>
                <th>Post Date</th>
            </tr>
            </thead>

            <tbody>

            <g:each in="${geekListItems}" var="item">
                <tr>
                <td><a target="${game.bggId} " href="${item.url}">${item.geeklist.name}</a>
                    <g:render template="/partials/gameInfo" model="${[bggGame:item.bggGame]}"/>
                </td>
                <td>${item.username}</td>
                    <td>${item.comments.size()}</td>
                    <td>${item.postDate}</td>

                </tr>
            </g:each>

            </tbody>

        </table>
    </div>

</div>
<g:render template="/partials/tableinit"/>
</body>
</html>
