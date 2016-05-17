<table class="fancy display  no-wrap" cellspacing="0" width="100%">
    <thead>
    <tr>
        <td>Game</td>
        <td>List</td>
        <td>User</td>
        <td>Updated</td>
    </tr>
    </thead>
    <tbody>
    <g:each var="item" in="${items}">
        <tr>
            <td><a href="${item.url}" target="${item.bggId}">${item.bggGame.name}</a>

                <g:render template="/partials/gameInfo" model="${[bggGame:item.bggGame]}"/>
            </td>
            <td>
                <g:link controller="geekList" action="index" id="${item.geeklist.id}">
               ${item.geeklist.name}
                </g:link>


            </td>
            <td>${item.username}</td>
            <td>
                <g:if test="${item.username==currentUsername}">
                    <g:link controller="userItem">
                        ${item.editDate}
                    </g:link>

                </g:if>
                <g:else>
                    ${item.editDate}
                </g:else>


            </td>
        </tr>
    </g:each>
    </tbody>
</table>