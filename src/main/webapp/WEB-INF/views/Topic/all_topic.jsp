<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .topic-item {
        height: 30%;
        width: 24%;
        flex: 0 0 auto;
    }
</style>

<div style="display: flex;flex-wrap:wrap; justify-content: space-around; align-content:space-around;height:90%"
     id="topic-contain">
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
    <canvas class='topic-item' id='stopic_10'></canvas>
    <canvas class='topic-item' id='stopic_11'></canvas>
</div>

<script>
    var options = {
        gridSize: Math.round($('#stopic_6').width() * 8 / 1024),
        weightFactor: function (size) {
            return Math.pow(size, 1.4) * $('#stopic_6').width() / 1024;
        },
        color: 'random-light',
        fontFamily: 'Microsoft Yahei',
        backgroundColor: 'transparent'
    };
    for (var i = 0; i < topic_num; i++) {
        options.list = getList(i)
        WordCloud(document.getElementById('stopic_' + i), options);
    }


    function getList(topic_id) {
        var list = T2W_Map[topic_id];
        var result = []
        for (var key in list) {
            result.push([I2W[key], Math.log(list[key] * 100)]);
        }
        result.sort(function (a, b) {
            return b[1] - a[1];
        })
        result = result.slice(0, 35);

        return result;
    }
    <!--
    $("#topic-contain").on('hover','a.delete',function(){

    });
    -->

</script>