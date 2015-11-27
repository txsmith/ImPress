[#include "head.ftl"]
[#include "header.ftl"]

<div class="profile center">
[#if user.hasProfilePicture()]
    <div class="profile-image" style="background-image:url(/image/user/${user.username});"></div>
[#else]
    <div class="profile-image" style="background-image:url(/static/img/profile.png);"></div>
[/#if]

    <div class="username">${user.username}</div>
    <div class="uploadCount">${userImages?size} uploads</div>
    <div class="bio">${user.bio}</div>
</div>
<div id="imagegrid">
[#list userImages as imageMeta]
    <div class="flexImg" data-image-url="/image/m/${imageMeta.imageID}">
        <a href="/view/${imageMeta.imageID}"></a>
        <div class="image-title">
            <span>${imageMeta.title}</span>
            <div class="btn-group" style="float: right;">
                <a role="button" class="btn btn-primary" href="/image/o/${imageMeta.imageID}" download="${imageMeta.imageID}.jpg">
                    <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Download
                </a>
            </div>
        </div>

    </div>
[/#list]
</div>
<script>
    $(function() {
        makeGrid({
            containerId: "#imagegrid",
            fixedHeight  : 300,
            clipThreshold : 0.5,
            margin: 10,
            animate: true
        });
    });
</script>
[#include "end.ftl"]
