<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>面向中心句自动文本摘要系统</title>
    <script src="resources/statistics.js"></script>
    <link rel="shortcut icon" href="resources/favicon.ico"/>
    <link rel="bookmark" href="resources/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="resources/login/css/set1.css"/>
    <link rel="stylesheet" type="text/css" href="resources/particles.css"/>
    <link rel="stylesheet" type="text/css" href="resources/load.css"/>
    <link rel="stylesheet" type="text/css" href="resources/component_button.css"/>
    <link rel="stylesheet" type="text/css" href="resources/login/css/component_modal.css"/>
    <style>
html, body {
	margin: 0;
	height: 100%;
}

h1 {
	margin: 0;
}

.main-container {
	height: 100%;
	width: 100%;
	overflow: hidden;
	position: relative;
}

.middle {
	position: absolute;
	width: 70%;
	text-align: center;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	margin: auto;
	max-height: 400px;
}

.title {
	font: 70px microsoft yahei;
	text-align: center;
	color: #b04b40;
	text-shadow: 0px 2px 3px #555;
	font-weight: bolder;
}

/*.title:hover {*/
/*color: #a0a0a0;*/
/*text-shadow: 0px 5px 8px #2a2a2a;*/
/*}*/
textarea {
	border: 0;
	background-color: transparent;
	/*scrollbar-arrow-color:yellow;  	scrollbar-base-color:lightsalmon;  	overflow: hidden;*/
	color: white;
	height: auto;
	
}
</style>
</head>
<body>
<div class="main-container">
    <div style="text-align: center">
        <div class="logo">
            <h1 class="title">面向中心句自动文本摘要系统</h1>
        </div>
        <div class="input">
            <textarea id="source" name="text" placeholder="请输入文章内容(建议500字及以上)" rows="25" cols="80" 
             wrap="SOFT" tabindex="0" dir="ltr" spellcheck="false" autocapitalize="off" 
             autocomplete="off" autocorrect="off" style="font-size:24px; 
             color:white; box-sizing: border-box; overflow-y: auto; overflow-x: auto;"></textarea>
        </div>
        <div class="hi-icon-wrap hi-icon-effect-8"> <!--图标 http://support.i3dthemes.com/product-components/hover-icons/ -->
            <a href="javascript:void(0)" onclick="handle_submit()" class="hi-icon hi-icon-archive">Archive</a>
            <a href="<%=basePath%>" class="hi-icon hi-icon-contract">Contract</a>
        </div>
    </div>
</div>
<div id="scene" class="scene unselectable"
     data-friction-x="0.1"
     data-friction-y="0.1"
     data-scalar-x="25"
     data-scalar-y="15">
    <div id="particles-js" style="" class="layer" data-depth="0.3"></div>
</div>
<div id="loader-container" class="loader-container">
    <div class='base'>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
        <div class='cube'></div>
    </div>
    <div class="load-title">加载中...</div>
    <%--<div class="mask"></div>--%>
</div>

<input type="button" style="display: none" class="md-trigger" data-modal="modal-1" id="click"/>
<div class="md-modal md-effect-1" id="modal-1">
    <div class="md-content">
        <h3 id="msg_head">警告</h3>
        <div>
            <p id="msg"></p>
            <input type="button" class="md-close" value="关闭"/>
        </div>
    </div>
</div>
<script src="resources/jquery.min.js"></script>
<script src="resources/login/js/parallax.js"></script>
<script src="resources/login/js/classie.js"></script>
<script src="resources/jquery.min.js"></script>
<script src="resources/particles.js"></script>
<script src="resources/login/js/modalEffects.js"></script>

<script>
    function handle_submit() {
        if ($.trim($("#source").val()) != "") {
            $("body").addClass("sp-load");
            $.post("Input/handle_manual_input", {"source": $.trim($("#source").val())}, function (data) {
                $("body").removeClass("sp-load");
                if (data.status) {
                    localStorage.setItem("info", JSON.stringify(data.info));
                    location.href = "Topic/main";
                } else {
                    $("#msg").text("摘要生成失败，" + data.msg);
                    $("#msg_head").text("警告");
                    document.querySelectorAll("#click")[0].click();
                }
            })
        }
    }


    (function () {
        // trim polyfill : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/Trim
        if (!String.prototype.trim) {
            (function () {
                // Make sure we trim BOM and NBSP
                var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
                String.prototype.trim = function () {
                    return this.replace(rtrim, '');
                };
            })();
        }

        [].slice.call(document.querySelectorAll('input.input__field')).forEach(function (inputEl) {
            // in case the input is already filled..
            if (inputEl.value.trim() !== '') {
                classie.add(inputEl.parentNode, 'input--filled');
            }

            // events:
            inputEl.addEventListener('focus', onInputFocus);
            inputEl.addEventListener('blur', onInputBlur);
        });

        function onInputFocus(ev) {
            classie.add(ev.target.parentNode, 'input--filled');
        }

        function onInputBlur(ev) {
            if (ev.target.value.trim() === '') {
                classie.remove(ev.target.parentNode, 'input--filled');
            }
        }

        $scene = $('#scene');
        var parallax = new Parallax($scene[0]);
//        (resize = function() {
//            $scene.css("width",  window.innerWidth + 'px');
//            $scene.css("height", window.innerHeight + 'px');
//
//        })();
    })();


</script>

</body>
</html>