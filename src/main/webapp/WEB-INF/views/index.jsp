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
    <script src="resources/statistics.js"></script>
    <link rel="shortcut icon" href="resources/favicon.ico"/>
    <link rel="bookmark" href="resources/favicon.ico"/>
    <%--<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.2.0/css/font-awesome.min.css" />--%>
    <!-- <link rel="stylesheet" type="text/css" href="css/demo.css" /> -->
    <link rel="stylesheet" type="text/css" href="resources/login/css/styles.css"/>

    <link rel="stylesheet" type="text/css" href="resources/login/css/set2.css"/>

    <link rel="stylesheet" type="text/css" href="resources/login/css/component.css"/>
    <link rel="stylesheet" type="text/css" href="resources/login/css/component_modal.css"/>
    <title>JLTMMR自动文本摘要系统</title>
</head>
<body>
<div id="container" class="wrapper">
    <ul id="scene" class="scene unselectable"
        data-friction-x="0.1"
        data-friction-y="0.1"
        data-scalar-x="25"
        data-scalar-y="15">
        <li class="layer" data-depth="0.00"></li>
        <li class="layer" data-depth="0.10">
            <div class="background"></div>
        </li>
        <li class="layer" data-depth="0.10">
            <div class="light orange b phase-4"></div>
        </li>
        <li class="layer" data-depth="0.10">
            <div class="light purple c phase-5"></div>
        </li>
        <li class="layer" data-depth="0.10">
            <div class="light orange d phase-3"></div>
        </li>
        <li class="layer" data-depth="0.15">
            <ul class="rope depth-10">
                <li><img src="resources/login/images/rope.png" alt="Rope"></li>
                <li class="hanger position-2">
                    <div class="board cloud-2 swing-1"></div>
                </li>
                <li class="hanger position-4">
                    <div class="board cloud-1 swing-3"></div>
                </li>
                <li class="hanger position-8">
                    <div class="board birds swing-5"></div>
                </li>
            </ul>
        </li>
        <li class="layer" data-depth="0.30">
            <ul class="rope depth-30">
                <li><img src="resources/login/images/rope.png" alt="Rope"></li>
                <li class="hanger position-1">
                    <div class="board cloud-1 swing-3"></div>
                </li>
                <li class="hanger position-5">
                    <div class="board cloud-4 swing-1"></div>
                </li>
            </ul>
        </li>
        <li class="layer" data-depth="0.30">
            <div class="wave paint depth-30"></div>
        </li>
        <li class="layer" data-depth="0.40">
            <div class="wave plain depth-40"></div>
        </li>
        <li class="layer" data-depth="0.50">
            <div class="wave paint depth-50"></div>
        </li>
        <li class="layer" data-depth="0.60">
            <div class="lighthouse depth-60"></div>
        </li>
        <li class="layer" data-depth="0.60">
            <ul class="rope depth-60">
                <li><img src="resources/login/images/rope.png" alt="Rope"></li>
                <li class="hanger position-3">
                    <div class="board birds swing-5"></div>
                </li>
                <li class="hanger position-6">
                    <div class="board cloud-2 swing-2"></div>
                </li>
                <li class="hanger position-8">
                    <div class="board cloud-3 swing-4"></div>
                </li>
            </ul>
        </li>
        <li class="layer" data-depth="0.60">
            <div class="wave plain depth-60"></div>
        </li>
        <li class="layer" data-depth="0.80">
            <div class="wave plain depth-80"></div>
        </li>
        <li class="layer" data-depth="1.0">
            <div class="wave paint depth-100"></div>
        </li>
        <li class="layer" data-depth="0.2">
            <h1 class="title">
                JLTMMR<em>自动文本摘要系统</em>
                <br>
                <br>
                <a style="text-decoration:none;color:white;" href="<%=basePath%>Input/manual_input">点击输入完整文章</a>
                <br>
                <a style="text-decoration:none;color:white;" href="<%=basePath%>Input/search_news">点击输入检索内容</a>
            </h1>
        </li>
    </ul>
    <form style="display: none;">
        <input style="display:none" type="text" name="fakeusernameremembered"/>
        <input style="display:none" type="password" name="fakepasswordremembered"/>
    </form>
    <%--<section id="about" class="wrapper about hide accelerate">--%>
        <%--<div class="cell accelerate">--%>
            <%--<div class="cables center accelerate">--%>
                <%--<div class="panel accelerate">--%>
                    <%--<header>--%>
                        <%--<h1 class="title">--%>
                            <%--JLMLMR<em>自动文本摘要系统</em>--%>
                            <%--<br>--%>
                            <%--<br>--%>
                            <%--<a href="<%=basePath%>Index/index">输入完整文章</a>--%>
                            <%--<br>--%>
                            <%--<a href="<%=basePath%>Index/index">输入检索内容</a>--%>
                        <%--</h1>--%>
                    <%--</header>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</section>--%>
</div>

<script src="resources/login/js/parallax.js"></script>
<script src="resources/login/js/classie.js"></script>
<script src="resources/login/js/modalEffects.js"></script>
<script src="resources/jquery.min.js"></script>
<script>

    var $html = $('html'),
            $toggle = $('#toggle'),
            $about = $('#about'),
            $scene = $('#scene');
    // Add touch or mouse class to html element.
    $html.addClass('mouse');

    // Resize handler.
    (resize = function () {
        $scene.css("width", window.innerWidth + 'px');
        $scene.css("height", window.innerHeight + 'px');

    })();

    function debounce(func, wait, immediate) {
        var timeout, args, context, timestamp, result;

        var later = function () {
            var last = (new Date()).getTime() - timestamp;

            if (last < wait && last >= 0) {
                timeout = setTimeout(later, wait - last);
            } else {
                timeout = null;
                if (!immediate) {
                    result = func.apply(context, args);
                    if (!timeout) context = args = null;
                }
            }
        };

        return function () {
            context = this;
            args = arguments;
            timestamp = (new Date()).getTime();
            var callNow = immediate && !timeout;
            if (!timeout) timeout = setTimeout(later, wait);
            if (callNow) {
                result = func.apply(context, args);
                context = args = null;
            }

            return result;
        };
    }

    // Attach window listeners.
    window.onresize = debounce(resize, 200);
    window.onscroll = debounce(resize, 200);

    function showDetails() {
        $about.removeClass('hide');
        $toggle.removeClass('i');
    }

    function hideDetails() {
        $about.addClass('hide');
        $toggle.addClass('i');
    }

    // Listen for toggle click event.
    $toggle.on('click', function (event) {
        $toggle.hasClass('i') ? showDetails() : hideDetails();
    });

    var parallax = new Parallax($scene[0]);

    $(".github").on("click", function () {
        $(".second-container").addClass("left-offset");
    });
    $(".download").on("click", function () {
        $(".second-container").removeClass("left-offset");
    });
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
    })();
</script>

</body>
</html>