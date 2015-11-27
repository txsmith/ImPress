[#include "head.ftl"]
[#include "header.ftl"]

<div id="imagegrid">
    [#list recentImages as imageMeta]
    <div class="flexImg" data-image-url="/image/m/${imageMeta.imageID}">
        <a href="/view/${imageMeta.imageID}"></a>
        <div class="image-title">
            <span>${imageMeta.title}</span>
            <div class="btn-group" style="float: right;">
                <a role="button" class="btn btn-primary" href="/user/${imageMeta.uploader}">
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${imageMeta.uploader}
                </a>
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