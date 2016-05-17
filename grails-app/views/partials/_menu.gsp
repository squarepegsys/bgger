<nav class="top-bar" data-topbar role="navigation">
    <ul class="title-area">
        <li class="name">
            <h1><a href="/">BGGer</a></h1>
        </li>
        <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
        <li class="toggle-topbar menu-icon">
        <a href="#">
            <span>Menu</span></a>a></li>
    </ul>

    <section class="top-bar-section">

        <!-- Left Nav Section -->
        <ul class="left">
        <li class="has-form">
            <g:form controller="search" action="search" method="post">
                <div class="row collapse">
                    <div class="large-8 small-9 columns">
                        <input type="text" placeholder="Find Stuff" name="searchTerm">
                    </div>

                    <div class="large-4 small-3 columns">
                        <input type="submit" class="alert button expand" value="Search"/>
                    </div>
                </div>
            </g:form>
        </li>
            <li><g:link controller="userItem" absolute="index">My Item Info</g:link></li>

        </ul>

        <!-- Right Nav Section -->
        <ul class="right">
        </ul>

    </section>
</nav>