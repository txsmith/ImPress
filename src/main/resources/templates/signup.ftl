[#include "head.ftl"]
[#include "header.ftl"]

<div class="container">
    <form id="signupForm" class="form-horizontal center" method="post">
        <h2 class="form-signup-heading">Sign up:</h2>

        <div class="form-group">
            <label for="username" class="col-xs-3 control-label">Username</label>
            <div class="col-xs-9">
                <input id="username" type="text" class="form-control" name="username" />
            </div>
        </div>

        <div class="form-group">
            <label for="password" class="col-xs-3 control-label">Password</label>
            <div class="col-xs-9">
                <input id="password" type="password" class="form-control" name="password" />
            </div>
        </div>

        <div class="form-group">
            <label for="confirmPassword" class="col-xs-3 control-label">Retype password</label>
            <div class="col-xs-9">
                <input id="confirmPassword" type="password" class="form-control" name="confirmPassword" />
            </div>
        </div>

        <div class="form-group">
            <label for="bio" class="col-xs-12 control-label">Tell us something about yourself</label>
            <div class="col-xs-12">
                <textarea id="bio" class="form-control" name="bio"></textarea>
            </div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up!</button>
    </form>
</div>

<script src="/static/formValidation.min.js"></script>
<script src="/static/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        $('#signupForm').formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                password: {
                    validators: {
                        notEmpty: {
                            message: 'Enter a password'
                        },
                        stringLength: {
                            message: 'Enter at least 5 characters',
                            min: 5
                        }
                    }
                },
                confirmPassword: {
                    validators: {
                        identical: {
                            field: 'password',
                            message: 'The two password must be the same'
                        },
                        notEmpty: {
                            message: 'Enter a password'
                        }
                    }
                },
                username: {
                    validators: {
                        regexp: {
                            regexp: /^[a-z]+$/i,
                            message: 'The username can consist of alphabetical characters only'
                        },
                        notEmpty: {
                            message: 'Enter a username'
                        }
                    }
                },
                bio: {
                    validators: {
                        notEmpty: {
                            message: 'Enter at least 3 characters'
                        },
                        stringLength: {
                            message: 'Enter at least 3 characters',
                            min: 3
                        }
                    }
                }
            }
        });
    });
</script>
[#include "end.ftl"]
