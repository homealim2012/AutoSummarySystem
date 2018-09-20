<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .viewportssd svg {
        display: block;
    }

    .viewportssd svg text {
        pointer-events: none;
        font-family: 'Lato', Arial;
        font-weight: 500;
        font-size: 1em;
        fill: #444;
    }
</style>

<div class="sub-container">
    <div style="text-align: center" id="my-color">

    </div>
    <div class="viewportssd" style="height: 85%;width: 100%;"></div>
</div>

<script>
    var colors = ["#f75353", "#f753cc", "#9a53f7", "#53d9f7", "#53f760", "#f4f753", "#f79753", "#f2eada", "#6495ED", "#9AC0CD",
        "#FF1493", "#EE82EE"]
    var str = "";
    for (var i = 0; i < topic_num; i++) {
        str += "<div class='square' style='background-color:" + colors[i] + "'>|主题" + (i + 1) + "|</div>";
    }
    $("#my-color").html(str);

    function doProcess(tsneez, $, d3, karpathy_tsne, data) {
        var DO_TIME = true
        var METHOD = 'tsneez'
        var buildDir = '/tsneez'
        var scienceaiWorkerPath = buildDir + '/javascripts/scienceai-worker.js'
        var stepnum = 0
        var PERPLEXITY = 30


        // Multiplex between methods
        var T, getEmbedding, initData, stepEmbedding
        switch (METHOD) {
            case 'tsneez':
                T = new tsneez.TSNEEZ({
                    theta: 0.5,
                    perplexity: PERPLEXITY,
                    randomProjectionInitialize: false
                })
                initData = function (vecs) {
                    T.initData(vecs)
                }
                stepEmbedding = function () {
                    stepnum++;
                    return T.step()
                }
                getEmbedding = function () {
                    return T.Y
                }
                break
            case 'karpathy':
                T = new karpathy_tsne.tSNE({
                    perplexity: PERPLEXITY
                })
                initData = function (vecs) {
                    T.initDataRaw(vecs)
                }
                stepEmbedding = function () {
                    stepnum++;
                    return T.step()
                }
                getEmbedding = function () {
                    // Return wrapper around the nested arrays to match ndarray API
                    var Y = T.getSolution()
                    return {
                        get: function (i, d) {
                            return Y[i][d]
                        }
                    }
                }
                break
            case 'scienceai':
                var Tworker = new Worker(scienceaiWorkerPath)
                var Ycurrent = null
                var tic = performance.now()
                Tworker.onmessage = function (e) {
                    var msg = e.data
                    switch (msg.type) {
                        case 'PROGRESS_STATUS':
                            break
                        case 'PROGRESS_ITER':
                            break
                        case 'PROGRESS_DATA':
                            // Do our own custom profiling
                            Ycurrent = msg.data
                            stepnum++
                            break
                        case 'STATUS':
                            break
                        case 'DONE':
                            Ycurrent = msg.data
                            break
                        default:
                    }
                }

                initData = function (vecs) {
                    Tworker.postMessage({
                        type: 'INPUT_DATA',
                        data: vecs
                    })
                    Tworker.postMessage({
                        type: 'RUN',
                        data: {
                            perplexity: 30,
                            earlyExaggeration: 4,
                            learningRate: 10,
                            nIter: 1000,
                            metric: 'euclidean'
                        }
                    })
                }
                stepEmbedding = function () {
                    return
                }
                getEmbedding = function () {
                    if (Ycurrent === null) {
                        return {
                            get: function () {
                                return null
                            }
                        }
                    } else {
                        return {
                            get: function (i, d) {
                                return Ycurrent[i][d]
                            }
                        }
                    }
                }
                // Turn off normal profiling
                DO_TIME = false
                break
        }

        // Update d3 embedding on a step
        function updateEmbedding() {
            var Y = getEmbedding()
            if (Y === null) return  // scienceai might not be ready
            var s = svg.selectAll('.u')
                    .data(data.words)
                    .attr('transform', function (d, i) {
                        if (!d.rotate) {
                            d.rotate = (Math.random() - 0.5) * 10
                        } else {
                            d.rotate = d.rotate
                        }
                        return 'translate(' +
                                ((Y.get(i, 0) * 200 * ss + tx) + 400) + ',' +
                                ((Y.get(i, 1) * 200 * ss + ty) + 400) + ')' +
                                'rotate(' + d.rotate + ')'
                    })

            s.selectAll('rect').style('fill-opacity', function (d) {
                if (d.init === true && fadeOld > 0) {
                    return Math.max(Math.min(0.9 - Math.sqrt(fadeOld * 0.09), 0.9), 0)
                } else {
                    return 0.9
                }
            })
            fadeOld--
        }

        // Resize the viewport in response to window resize
        function resize() {
            var width = $('.viewportssd').width()
            var height = $('.viewportssd').height()
            console.log(width, height)
            svg.attr('width', width).attr('height', height)
        }

        // Set up visualization
        var svg
        var fadeOld = 0
        var zoomListener = d3.behavior.zoom()
                .scaleExtent([0.0005, 10])
                .center([0, 0])
                .on('zoom', zoomHandler)
        var tx = 0
        var ty = 0
        var ss = 1

        function zoomHandler() {
            tx = d3.event.translate[0]
            ty = d3.event.translate[1]
            ss = d3.event.scale
        }

        // (re-)Draw the visualization
        function draw() {
            var g = svg.selectAll('.b')
                    .data(data.words)//, function (d) { return d.str })
                    .enter().append('g')
                    .attr('class', 'u')
            g.append('rect')
                    .attr('width', function (d) {
                        return (d.str.length * 8) + 10
                    })
                    .attr('height', 20)
                    .attr('rx', 5)
                    .attr('ry', 5)
                    .style('fill', function (d) {
                        return colors[d.topic]
                    })

            g.append('text')
                    .attr('text-anchor', 'middle')
                    .attr('x', function (d) {
                        return (d.str.length * 4) + 5
                    })
                    .attr('y', 10)
                    .attr('alignment-baseline', 'central')
                    .attr('fill', '#333')
                    .text(function (d) {
                        return d.str
                    })
        }

        // Draw initial embedding
        function drawEmbedding() {
            var div = d3.select('.viewportssd')
            svg = div.append('svg') // svg is global
            draw()
            zoomListener(svg)
            d3.select(window).on('resize', resize)
            resize()
        }

        // Step the embedding and visualization
        var count = 0

        function step() {
            stepEmbedding()
            updateEmbedding()

            if (count++ < 2000) {
                window.requestAnimationFrame(step)
            }
        }

        initData(data.vecs)

        // Initialize the visualization
        drawEmbedding()
        window.requestAnimationFrame(step)

        // Set up listener for adding points

    }

    var words = []
    for (var index in I2W) {
        words.push({str: '|' + I2W[index] + '|', init: true, topic: W2T[index]})
    }
    var data = {
        words: words.slice(0, 500),
        vecs: W2T_Vec.slice(0, 500),
    }

    setTimeout(function () {
        doProcess(tsneez, $, d3, tsnejs, data)
    }, 800)


</script>
