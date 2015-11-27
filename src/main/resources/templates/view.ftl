[#include "head.ftl"]
[#include "header.ftl"]

<div class="image-view center">
    <img src="/image/m/${viewImage.imageID}"/>
    <div id="left-panel">
        <h2>${viewImage.title}</h2>
        <span>${viewImage.description}</span>
    </div>
    <div id="right-panel">
        <div class="user-info">
            <a href="/user/${uploader.username}">
            [#if uploader.hasProfilePicture()]
                <div class="profile-image" style="background-image: url(/image/user/${user.username});"></div>
            [#else]
                <div class="profile-image" style="background-image: url(/static/img/profile.png);"></div>
            [/#if]
                <h3>${uploader.username}</h3>
            </a>

        </div>
        <div class="download-button">
            <div class="btn-group">
                <a role="button" class="btn btn-primary" href="/image/t/${viewImage.imageID}" download="${viewImage.imageID}-thumbnail.jpg">
                    <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Thumbnail <span class="image-size"></span>
                </a>
            </div>
        </div>
        <div class="download-button">
            <div class="btn-group">
                <a role="button" class="btn btn-primary" href="/image/m/${viewImage.imageID}" download="${viewImage.imageID}-medium.jpg">
                    <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Medium <span class="image-size"></span>
                </a>
            </div>
        </div>
        <div class="download-button">
            <div class="btn-group">
                <a role="button" class="btn btn-primary" href="/image/o/${viewImage.imageID}" download="${viewImage.imageID}-full.jpg">
                    <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Full size <span class="image-size"></span>
                </a>
            </div>
        </div>
    </div>
</div>

[#include "end.ftl"]
