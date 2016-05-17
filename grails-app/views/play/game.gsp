<%--
  Created by IntelliJ IDEA.
  User: mikeh
  Date: 2/9/16
  Time: 8:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Plays for ${game.name}</title>
</head>

<body>

<div class="row">

    <div class="large-12 columns">

        <h2>${game.name}</h2>

        <table class="fancy display  no-wrap" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Date</th>
                <th>Quantity</th>
                <th>Comments</th>
            </tr>
            </thead>

            <tbody>

            <g:each in="${plays}" var="play">
                <tr>
                    <td>${play.playedOnDate}
                    </td>
                    <td>${play.quantity}</td>
                    <td>${play.comments}</td>

                </tr>
            </g:each>

            </tbody>

        </table>

    </div>
</div>
</body>
</html>
