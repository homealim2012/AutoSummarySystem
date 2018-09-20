<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<div class="sub-container" id="abstract_jltmmr">
    <!--
    <textarea id="abstract_jlmlmr" readonly="readonly" name="text" rows="20" cols="80"  wrap="SOFT" tabindex="0" dir="ltr" spellcheck="false" autocapitalize="off" autocomplete="off" autocorrect="off" style="font-size:24px; color:black; box-sizing: border-box; overflow-y: auto; overflow-x: auto;"></textarea>
    -->

</div>
<script>
    var upImgSrc = "resources/image/up.png";
    var up2ImgSrc = "resources/image/up2.png";
    var downImgSrc = "resources/image/down.png";
    var down2ImgSrc = "resources/image/down2.png";

    var sentences = jlmlmr.split("\n");
    var htmlContent = "";
    for (var i=0; i<sentences.length; i++) {
        if (htmlContent.length > 0) {
            htmlContent += "\n";
        }
        htmlContent += "<li style='font-size: 30px;'>" +
                            "<a onclick='clickSentence("+i+", 1)'>" +
                                "<img id='up_sentence_"+i+"' src='"+upImgSrc+"' height='30' width='30'/>" +
                            "</a>" +
                            "<a onclick='clickSentence("+i+", -1)'>" +
                                "<img id='down_sentence_"+i+"' src='"+downImgSrc+"' height='30' width='30'/>" +
                            "</a>" +
                            sentences[i] +
                        "</li>";
    }
    document.getElementById('abstract_jltmmr').innerHTML = htmlContent;

    for (var i=0; i<sentences.length; i++) {
        //change image
        if (zanCaiSentenceStatus[i] == 0) {
            document.getElementById("up_sentence_" + i).src = upImgSrc;
            document.getElementById("down_sentence_" + i).src = downImgSrc;
        }
        else if (zanCaiSentenceStatus[i] == -1) {
            document.getElementById("up_sentence_" + i).src = upImgSrc;
            document.getElementById("down_sentence_" + i).src = down2ImgSrc;
        }
        else if (zanCaiSentenceStatus[i] == 1) {
            document.getElementById("up_sentence_" + i).src = up2ImgSrc;
            document.getElementById("down_sentence_" + i).src = downImgSrc;
        }
    }

    function clickSentence(sentenceIndex, value) {
        // getNowStatus
        var nowStatus = zanCaiSentenceStatus[sentenceIndex]; //0 means no, 1 means zan, -1 means cai

        var feedbackStatus = 2; //1 should, 0 should not, 2 maybe
        if (value == 1) { //zan
            if (nowStatus == 0) {
                feedbackStatus = 1;
                document.getElementById("up_sentence_" + sentenceIndex).src = up2ImgSrc; //change image
                zanCaiSentenceStatus[sentenceIndex] = 1;
            }
            else if (nowStatus == -1) {
                feedbackStatus = 1;
                document.getElementById("up_sentence_" + sentenceIndex).src = up2ImgSrc; //change image
                zanCaiSentenceStatus[sentenceIndex] = 1;
            }
            else  if (nowStatus == 1) { //cancel zan
                feedbackStatus = 2;
                document.getElementById("up_sentence_" + sentenceIndex).src = upImgSrc; //change image
                zanCaiSentenceStatus[sentenceIndex] = 0;
            }
            document.getElementById("down_sentence_" + sentenceIndex).src = downImgSrc; //change image
        }
        else if (value == -1) { //cai
            if (nowStatus == 0) {
                feedbackStatus = 0;
                document.getElementById("down_sentence_" + sentenceIndex).src = down2ImgSrc; //change image
                zanCaiSentenceStatus[sentenceIndex] = -1;
            }
            else if (nowStatus == -1) { //cancel cai
                feedbackStatus = 2;
                document.getElementById("down_sentence_" + sentenceIndex).src = downImgSrc; //change image
                zanCaiSentenceStatus[sentenceIndex] = 0;
            }
            else  if (nowStatus == 1) {
                feedbackStatus = 0;
                document.getElementById("down_sentence_" + sentenceIndex).src = down2ImgSrc; //change image
                zanCaiSentenceStatus[sentenceIndex] = -1;
            }
            document.getElementById("up_sentence_" + sentenceIndex).src = upImgSrc; //change image
        }
        // ajax click
        $.ajax({
            url: "<%=basePath%>/Input/sentence_feedback",
            type: "post",
            async: true,
            data: {"sentence": sentences[sentenceIndex], "feedback_status": feedbackStatus},
            //dataType:'text',
            success: function(data){
            }
        });
    }
</script>