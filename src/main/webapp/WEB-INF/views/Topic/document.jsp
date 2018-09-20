<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    #preview {
        transition: all 0.3s;
    }

    .shrink {
        transform: scale(0.9);
        opacity: 0;
    }
</style>
<div class="sub-container dummy dummy-text">
    <div style="text-align: center" id="my-color">

    </div>
    <div id="preview" class="preview" style="height:88%;display: flex;align-content: space-around;flex-wrap: wrap">
    </div>
    <div id="slider" class="sp-slider-wrapper">
        <nav>
            <a href="#" class="sp-prev">Previous</a>
            <a href="#" class="sp-next">Next</a>
        </nav>
    </div>

</div>

<script>
    var Docs = ori_sentence.split("\n");

    var $update = $('#preview');
    $update.html("<div class='curved_box'>" + Docs.slice(0, 5).join("</div><div class='curved_box'>") + "</div>");
    $("#slider").pagination({
        total: Math.ceil(Docs.length / 5),
        onChange: function (value) {
            var subdocs = Docs.slice((value - 1) * 5, value * 5);
            $update.addClass("shrink")
            setTimeout(function () {
                $update.html("<div class='curved_box'>" + subdocs.join("</div><div class='curved_box'>") + "</div>");
                $update.removeClass("shrink")
            }, 350)

        }
    });
    $(".ui-corner-all span").css("left", "0");
</script>
