[#include "head.ftl"]
[#include "header.ftl"]

<div class="container">
    <form id="editForm" class="form-horizontal center" method="post" enctype="multipart/form-data">
        <h2 class="form-signup-heading">Edit profile information:</h2>

        <div class="form-group">
            <div class="col-xs-12">
                <input type="file" name="profileImage" id="profileImage" class="col-xs-12 file-input" data-multiple-caption="{count} files selected" multiple="" size="10" accept="image/png,image/jpeg">
                <label class="col-xs-12" for="profileImage">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 20 17"><path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"></path></svg>
                    <span>Choose profile picture</span>
                </label>
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
                <textarea id="bio" class="form-control" name="bio">${user.bio}</textarea>
            </div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
    </form>
</div>

<script src="/static/formValidation.min.js"></script>
<script src="/static/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        $('#editForm').formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                confirmPassword: {
                    validators: {
                        identical: {
                            field: 'password',
                            message: 'The two password must be the same'
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
