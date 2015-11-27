function makeGrid(conf) {
    var config = sanitize(conf);

    var $container  = $(config.containerId),
        $images     = $container.children(),
        loaded      = new Array($images.length),
        count       = 0;

    $images.addClass("invisible");
    if (config.animate) {
        $images.addClass("animate");
    }
    $images.each(function(i) {
        var $that = $(this);
        $('<img />').load(function() {
            loaded[i] = {
                $elem: $that,
                url:   $(this).attr('src'),
                size:  this.width / this.height
            };
            count++;
            if (count === $images.length) {
                nextRow(loaded, $container, config);
            }
        }).attr('src', $(this).attr('data-image-url'));
    });

    $(window).resize(function() {
        $container.empty();
        nextRow(loaded, $container, config);
    });
}

function nextRow(images, $container, config) {
    $newRow = createFlexRow(config);
    $container.append($newRow);
    var remainingImages = fillRow(images, $newRow, config);

    if (remainingImages.length > 0) {
       nextRow(remainingImages, $container, config);
    }
}

function fillRow(images, $row, config) {
    var maxSize = $row.outerWidth(),
        renderSize = 0,
        areaVisible = 0;

    for (var i = 0, currentSize = 0;
        i < images.length; 
        i++, currentSize += renderSize) 
    {
        renderSize = config.fixedHeight * images[i].size;
        areaVisible = (maxSize - currentSize) / renderSize;
        if (areaVisible > 1-config.clipThreshold) {
            var $img = createFlexImageDiv(images[i], config);
            if (i == images.length-1) {
                $img = $img.css("flex-grow", "");
            }
            $row.append($img);
            var delay = config.animate ? 100*i : 10;
            setTimeout(show($img), delay);
        } else {
            break;
        }
    }
    return images.slice(i);
}

function show($img) {
    return function() {
        $img.removeClass("invisible");
        $img.addClass("visible");
    }
}

function createFlexRow(config) {
    return $("<div class='flexRow'></div>")
            .css("display", "flex")
            .css("flex-flow", "row nowrap")
            .css("height", config.fixedHeight+2*config.margin+"px")
            .css("width", "100%");
}

function createFlexImageDiv(image, config) {
    return image.$elem
            .css("background-image", "url("+image.url+")")
            .css("background-position", "center")
            .css("background-size", "cover")
            .css("flex-grow", image.size)
            .css("width", image.size*config.fixedHeight + "px")
            .css("height", config.fixedHeight)
            .css("margin", config.margin + "px");
}

function sanitize(config) {
    var defaults = {
        containerId   : undefined,
        fixedHeight   : 300,
        clipThreshold : 0.6,
        animate       : true,
        margin        : 0
    };

    var newConf = jQuery.extend({}, defaults, config);
    checkNotUndefined(newConf.containerId, "Grid container is undefined.");

    return newConf;
}

function checkNotUndefined(o, err) {
    checkNot(o, undefined, err);
}

function checkNot(o, eq, err) {
    if (o === eq) {
        throw err
    }
}
