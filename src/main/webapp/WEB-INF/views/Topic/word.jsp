<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sub-container">
    <div id="word_count" style="height:800px;"></div>
    <div id="word_idf"></div>
</div>
<script>
    var wordpair = [];

    var wordsNumMap = new Map();
    var sentences = filter_sentence.split("\n");
    for (var i = 0; i < sentences.length; i++) {
        var words = sentences[i].split(" ");
        for (var j = 0; j < words.length; j++) {
            if (!wordsNumMap.has(words[j])) {
                wordsNumMap.set(words[j], 0);
            }
            wordsNumMap.set(words[j], wordsNumMap.get(words[j]) + 1);
        }
    }
    for (var [key, value] of wordsNumMap) {
        wordpair.push([key, value]);
    }

    wordpair.sort(function (a, b) {
        return b[1] - a[1];
    });

    wordpair = wordpair.slice(0, 40);
    wordpair.reverse();

    var words = [], datas = [];
    for (var i in wordpair) {
        words.push(wordpair[i][0]);
        datas.push(wordpair[i][1]);
    }


    var option = {
        title: {
            text: '单词出现次数'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['次数']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: words
        },
        series: [

            {
                name: '单词',
                type: 'bar',
                data: datas
            }
        ]
    };

    var chart = echarts.init(document.getElementById('word_count'), 'dark');
    chart.title = '原文中各单词出现的次数 - 条形图';
    chart.setOption(option);

</script>