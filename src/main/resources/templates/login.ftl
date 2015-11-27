[#include "head.ftl"]
[#include "header.ftl"]

<div class="container">

    <form id="loginForm" class="form-horizontal center" method="post">
        <h2 class="form-signin-heading">Please log in</h2>

        [#if error??]
            <div class="alert alert-danger" role="alert">
            ${error.getMessage()}
            </div>
        [/#if]

        <div class="form-group">
            <label class="col-xs-3 control-label">Username</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" name="username" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">Password</label>
            <div class="col-xs-9">
                <input type="password" class="form-control" name="password" />
            </div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Log In!</button>
    </form>
</div>

[#include "end.ftl"]