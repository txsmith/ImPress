[#include "head.ftl"]
[#include "header.ftl"]

<div class="container">
    <form id="uploadForm" class="form-horizontal center" action="/upload" method="post" enctype="multipart/form-data">
        <h2 class="form-upload-heading">Upload a new photo</h2>

        <div class="form-group">
            <div class="col-xs-12">
                <input type="file" name="uploadFile" id="uploadFile" class="col-xs-12 file-input" data-multiple-caption="{count} files selected" multiple="" size="10" accept="image/png,image/jpeg">
                <label class="col-xs-12" for="uploadFile">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 20 17"><path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"></path></svg>
                    <span>Choose an image file…</span>
                </label>
            </div>
        </div>

        <div class="form-group">
            <label for="title" class="col-xs-12">Photo Title:</label>
            <div class="col-xs-12">
                <input id="title" type="text" class="form-control" name="title" />
            </div>
        </div>

        <div class="form-group">
            <label for="text" class="col-xs-12">Tell us something about it:</label>
            <div class="col-xs-12">
                <textarea id="text" type="text" class="form-control" name="text" rows="3"></textarea>
            </div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Upload</button>
    </form>
</div>

<script src="/static/formValidation.min.js"></script>
<script src="/static/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        $('#uploadForm').formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                title: {
                    validators: {
                        notEmpty: {
                            message: 'Enter a photo title'
                        },
                        stringLength: {
                            message: 'Enter at least 3 characters',
                            min: 3
                        }
                    }
                },
                text: {
                    validators: {
                        notEmpty: {
                            message: 'Enter a description'
                        }
                    }
                }
            }
        });
    });

    'use strict';

    ;( function( $, window, document, undefined )
    {
        $( '.file-input' ).each( function()
        {
            var $input	 = $( this ),
                    $label	 = $input.next( 'label' ),
                    labelVal = $label.html();

            $input.on( 'change', function( e )
            {
                var fileName = '';

                if( this.files && this.files.length > 1 )
                    fileName = ( this.getAttribute( 'data-multiple-caption' ) || '' ).replace( '{count}', this.files.length );
                else if( e.target.value )
                    fileName = e.target.value.split( '\\' ).pop();

                if( fileName )
                    $label.find( 'span' ).html( fileName );
                else
                    $label.html( labelVal );
            });

            // Firefox bug fix
            $input
                    .on( 'focus', function(){ $input.addClass( 'has-focus' ); })
                    .on( 'blur', function(){ $input.removeClass( 'has-focus' ); });
        });
    })( jQuery, window, document );
</script>
[#include "end.ftl"]
