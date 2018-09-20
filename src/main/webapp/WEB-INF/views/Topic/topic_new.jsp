<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

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
    </div><%--
    <div id="preview" class="preview" style="height:88%;display: flex;align-content: space-around;flex-wrap: wrap">
    </div>--%>
    <div style="display: flex;flex-wrap:wrap; justify-content: space-around; align-content:space-around;height:90%"
         id="topic-contain">
        <div class="column">
            <li>
                <canvas class="topic-item" id="stopic_0" width="600" height="300"></canvas><div class="line">
                <div align="left" style="float:left;">
                    <a onclick="clickTopic(0, 1)">
                        <img id="up_stopic_0" src="resources/image/up.png" height="45" width="45"/>
                    </a>
                </div>
                <div align="right">
                    <a onclick="clickTopic(0, -1)">
                        <img id="down_stopic_0" src="resources/image/down.png" height="45" width="45"/>
                    </a>
                </div>
            </li>
            <li>
                <canvas class="topic-item" id="stopic_1" width="600" height="300"></canvas><div class="line">
                <div align="left" style="float:left;">
                    <a onclick="clickTopic(1, 1)">
                        <img id="up_stopic_1" src="resources/image/up.png" height="45" width="45"/>
                    </a>
                </div>
                <div align="right">
                    <a onclick="clickTopic(1, -1)">
                        <img id="down_stopic_1" src="resources/image/down.png" height="45" width="45"/>
                    </a>
                </div>
            </li>
        </div>
        <div class="column">
            <li>
                <canvas class="topic-item" id="stopic_2" width="600" height="300"></canvas><div class="line">
                <div align="left" style="float:left;">
                    <a onclick="clickTopic(2, 1)">
                        <img id="up_stopic_2" src="resources/image/up.png" height="45" width="45"/>
                    </a>
                </div>
                <div align="right">
                    <a onclick="clickTopic(2, -1)">
                        <img id="down_stopic_2" src="resources/image/down.png" height="45" width="45"/>
                    </a>
                </div>
            </li>
            <li>
                <canvas class="topic-item" id="stopic_3" width="600" height="300"></canvas><div class="line">
                <div align="left" style="float:left;">
                    <a onclick="clickTopic(3, 1)">
                        <img id="up_stopic_3" src="resources/image/up.png" height="45" width="45"/>
                    </a>
                </div>
                <div align="right">
                    <a onclick="clickTopic(3, -1)">
                        <img id="down_stopic_3" src="resources/image/down.png" height="45" width="45"/>
                    </a>
                </div>
            </li>
        </div>
        <!--
        <canvas class='topic-item' id='stopic_0'></canvas>
        <canvas class='topic-item' id='stopic_1'></canvas>
        <canvas class='topic-item' id='stopic_2'></canvas>
        <canvas class='topic-item' id='stopic_3'></canvas>

        <canvas class='topic-item' id='stopic_4'></canvas>

        <canvas class='topic-item' id='stopic_5'></canvas>
        <canvas class='topic-item' id='stopic_6'></canvas>
        <canvas class='topic-item' id='stopic_7'></canvas>
        <canvas class='topic-item' id='stopic_8'></canvas>
        <canvas class='topic-item' id='stopic_9'></canvas>
        -->
    </div>
    <div id="slider" class="sp-slider-wrapper">
        <nav>
            <a href="#" class="sp-prev">Previous</a>
            <a href="#" class="sp-next">Next</a>
        </nav>
    </div>
    <p id="pageNum" hidden="hidden">1</p>

</div>

<script>
    var topic_words_list = topic_words.split("\n");
    var topic_words_score_list = topic_words_score.split("\n");
    var showNumEachPage = 4;
    var widthValue = $('#stopic_0').width();

    var upImgSrc = "resources/image/up.png";
    var up2ImgSrc = "resources/image/up2.png";
    var downImgSrc = "resources/image/down.png";
    var down2ImgSrc = "resources/image/down2.png";

    var options = {
        gridSize: Math.round(widthValue * 8 / 2 / 1024), //$('#stopic_0').width() = 1500, 增加会导致词云中词语间隙增大。size of the grid in pixels for marking the availability of the canvas — the larger the grid size, the bigger the gap between words.
        weightFactor: function (size) {
            return Math.pow(size, 1.4) * widthValue / 1024; //控制字体大小，function to call or number to multiply for size of each word in the list.
        },
        color: 'random-light',
        fontFamily: 'Microsoft Yahei',
        backgroundColor: 'transparent'
    };
    function getList(topic_id) {
        var result = []
        var nowWordsList = topic_words_list[topic_id].split(" ")
        var nowScoreList = topic_words_score_list[topic_id].split(" ")
        for (var i = 0; i < nowWordsList.length; i++) {
            result.push([nowWordsList[i], Math.log((parseFloat(nowScoreList[i])+0.01) * 5000)]);
        }
        result.sort(function (a, b) {
            return b[1] - a[1];
        });
        return result;
    }

    var $update = $('#topic-contain');
    //<canvas class='topic-item' id='stopic_0'></canvas>
    //$update.html("<div class='curved_box'>" + Docs.slice(0, 5).join("</div><div class='curved_box'>") + "</div>");
    for (var i = 0; i < showNumEachPage; i++) {
        options.list = getList(i)
        WordCloud(document.getElementById('stopic_' + i % showNumEachPage), options);
        //change image
        if (zanCaiStatus[i] == 0) {
            document.getElementById("up_stopic_" + i%showNumEachPage).src = upImgSrc;
            document.getElementById("down_stopic_" + i%showNumEachPage).src = downImgSrc;
        }
        else if (zanCaiStatus[i] == -1) {
            document.getElementById("up_stopic_" + i%showNumEachPage).src = upImgSrc;
            document.getElementById("down_stopic_" + i%showNumEachPage).src = down2ImgSrc;
        }
        else if (zanCaiStatus[i] == 1) {
            document.getElementById("up_stopic_" + i%showNumEachPage).src = up2ImgSrc;
            document.getElementById("down_stopic_" + i%showNumEachPage).src = downImgSrc;
        }
    }
    $("#slider").pagination({
        total: Math.ceil(topic_words_list.length / showNumEachPage),
        onChange: function (value) {
            $update.addClass("shrink")
            setTimeout(function () {
                //$update.html("<div class='curved_box'>" + subdocs.join("</div><div class='curved_box'>") + "</div>");
                for (var i = (value - 1) * showNumEachPage; i < Math.min(value * showNumEachPage, topic_words_list.length); i++) {
                    options.list = getList(i)
                    WordCloud(document.getElementById('stopic_' + i%showNumEachPage), options);
                    //change image
                    if (zanCaiStatus[i] == 0) {
                        document.getElementById("up_stopic_" + i%showNumEachPage).src = upImgSrc;
                        document.getElementById("down_stopic_" + i%showNumEachPage).src = downImgSrc;
                    }
                    else if (zanCaiStatus[i] == -1) {
                        document.getElementById("up_stopic_" + i%showNumEachPage).src = upImgSrc;
                        document.getElementById("down_stopic_" + i%showNumEachPage).src = down2ImgSrc;
                    }
                    else if (zanCaiStatus[i] == 1) {
                        document.getElementById("up_stopic_" + i%showNumEachPage).src = up2ImgSrc;
                        document.getElementById("down_stopic_" + i%showNumEachPage).src = downImgSrc;
                    }
                }
                $update.removeClass("shrink")
                document.getElementById("pageNum").innerText = value;
            }, 350)

        }
    });

    $(".ui-corner-all span").css("left", "0");

    function clickTopic(topicId, value) {
        var pageNum = document.getElementById("pageNum").innerText; //[1, 25]
        var topicIndexInAll = (pageNum-1) * showNumEachPage + topicId; //[0, 99]

        // getNowStatus
        var nowStatus = zanCaiStatus[topicIndexInAll]; //0 means no, 1 means zan, -1 means cai

        var addNum = 0;
        if (value == 1) { //zan
            if (nowStatus == 0) {
                addNum = 1;
                document.getElementById("up_stopic_" + topicId).src = up2ImgSrc; //change image
                zanCaiStatus[topicIndexInAll] = 1;
            }
            else if (nowStatus == -1) {
                addNum = 2;
                document.getElementById("up_stopic_" + topicId).src = up2ImgSrc; //change image
                zanCaiStatus[topicIndexInAll] = 1;
            }
            else  if (nowStatus == 1) { //cancel zan
                addNum = -1;
                document.getElementById("up_stopic_" + topicId).src = upImgSrc; //change image
                zanCaiStatus[topicIndexInAll] = 0;
            }
            document.getElementById("down_stopic_" + topicId).src = downImgSrc; //change image
        }
        else if (value == -1) { //cai
            if (nowStatus == 0) {
                addNum = -1;
                document.getElementById("down_stopic_" + topicId).src = down2ImgSrc; //change image
                zanCaiStatus[topicIndexInAll] = -1;
            }
            else if (nowStatus == -1) { //cancel cai
                addNum = 1;
                document.getElementById("down_stopic_" + topicId).src = downImgSrc; //change image
                zanCaiStatus[topicIndexInAll] = 0;
            }
            else  if (nowStatus == 1) {
                addNum = -2;
                document.getElementById("down_stopic_" + topicId).src = down2ImgSrc; //change image
                zanCaiStatus[topicIndexInAll] = -1;
            }
            document.getElementById("up_stopic_" + topicId).src = upImgSrc; //change image
        }
        // ajax click
        if (addNum != 0) {
            $.ajax({
                url: "<%=basePath%>/Input/topic_feedback",
                type: "post",
                async: true,
                data: {"time_stamp": time_stamp, "topic_id": topicIndexInAll, "add_num": addNum},
                //dataType:'text',
                success: function(data){
                }
            });
        }
    }
</script>
