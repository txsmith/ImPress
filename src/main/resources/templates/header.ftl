<nav class="navbar navbar-default">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">ImPress</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-collapse-2">
            <ul class="nav navbar-nav navbar-right">
                [#if user??]
                    <li>
                        <a class="btn btn-default btn-outline btn-circle collapsed" aria-expanded="false" href="/upload">Create new post</a>
                    </li>
                    <li><a href="/me">My Profile</a></li>
                    <li><a href="/logoff">Log off</a></li>
                [#else]
                    <li><a class="btn btn-default" href="/signup">Sign up</a></li>
                    <li>
                        <a class="btn btn-default" href="/login">Log in</a>
                    </li>
                [/#if]
            </ul>

            <div class="nav navbar-nav nav-collapse slide-down collapse" id="nav-collapse2" aria-expanded="false" style="height: 0px;">
                <form class="navbar-form navbar-right form-inline" role="form" action="/login" method="post">
                    <div class="form-group">
                        <label class="sr-only" for="username">Email</label>
                        <input type="text" class="form-control" id="username" placeholder="Username" autofocus="" required="" name="username">
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="Password">Password</label>
                        <input type="password" class="form-control" id="Password" placeholder="Password" required="" name="password">
                    </div>
                    <button type="submit" class="btn btn-success">Log in</button>
                </form>
            </div>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container -->
</nav>