package edu.as.sys.controller;

import edu.as.sys.model.DBOperation;
import edu.as.sys.model.Info;
import edu.as.sys.model.News;
import nlp.util.NLP;
import tmfst.textCrawler.CommentCrawler;
import tmfst.textCrawler.XinlangNewsCrawler;
import edu.as.sys.common.FileDirectory;
import edu.as.sys.common.FileIO;
import edu.as.sys.common.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mathworks.toolbox.javabuilder.MWException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dell on 2016/12/13.
 */
@Controller
@RequestMapping("/Input")
public class InputController {

    final boolean testOrNot = false; //测试

    public static final int wordsLenLimit = 500;

    @RequestMapping(value = "/search_news", method = RequestMethod.GET)
    public String searchNews() {
        return "Input/search_news";
    }

    @RequestMapping(value = "/manual_input", method = RequestMethod.GET)
    public String manualInput() {
        return "Input/manual_input";
    }

    @ResponseBody
    @RequestMapping(value = "/handle_manual_input", method = RequestMethod.POST)
    public ResponseResult handleManualInput(String source) throws IOException, InterruptedException {
        ArrayList<String> sourceList = new ArrayList<String>();
        sourceList.add(source);
        ResponseResult result = commonWork(sourceList, null);
        if (result.status == true) {
            DBOperation.insertInfo(result.info);
        }
        else {
            result.msg = "文本摘要过短，请输入最少500个字";
        }
        System.out.println("done");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/handle_search_news", method = RequestMethod.POST)
    public ResponseResult handleSearchNews(String keyword) throws IOException, InterruptedException {
        //crawl
        List<News> newsList = null;
        try {
            CommentCrawler cc = new XinlangNewsCrawler();
            newsList = cc.get_source_list(keyword);
            System.out.println("finished crawl source");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = new ResponseResult();
            result.status = false;
            result.msg = "爬取内容失败";
            return result;
        }
        System.out.println("Crawl news num: " + newsList.size());
        ArrayList<String> sourceList = new ArrayList<String>();
        ArrayList<String> publishTimeList = new ArrayList<String>();
        String urls = "";
        for (int i=0; i<Math.min(10, newsList.size()); i++) { //10篇文章
            if (newsList.get(i).content == "") { //没有content的跳过
                continue;
            }
            sourceList.add(newsList.get(i).title + "。 " + newsList.get(i).content);
            publishTimeList.add(newsList.get(i).publishTime);
            if (urls.length() > 0) {
                urls += "\n";
            }
            urls += newsList.get(i).url;
        }
        ResponseResult result = commonWork(sourceList, publishTimeList);
        result.info.search_query = keyword;
        result.info.urls = urls;
        if (result.status == true) {
            DBOperation.insertInfo(result.info);
        }
        else {
            result.msg = "文本爬取结果过少，请换另一些关键词试试";
        }
        System.out.println("done");
        return result;
    }

    public ResponseResult commonWork(ArrayList<String> sourceList, ArrayList<String> publishTimeList) throws IOException, InterruptedException {
        ResponseResult result = new ResponseResult();
        String nowTime = getTime();
        if (testOrNot == true) {
            nowTime = "2017";
        }
        System.out.println(nowTime);
        int allContentLen = 0;
        for (int i=0; i<sourceList.size(); i++) {
            String source = sourceList.get(i);
            source = source.replace("\r\n", " ");
            source = source.replace("\n", " ");
            source = source.replace("\t", " ");
            sourceList.set(i, source);
            allContentLen += source.length();
        }
        if (allContentLen < wordsLenLimit) {
            result.status = false;
            result.msg = ""; //文本摘要过短，请输入最少500个字
            return result;
        }
        if (publishTimeList == null) { //添加现在时间
            publishTimeList = new ArrayList<String>();
            publishTimeList.add(nowTime);
        }
        ArrayList<String> inputContentList = new ArrayList<String>();
        for (int i=0; i<sourceList.size(); i++) {
            inputContentList.add(publishTimeList.get(i)+"\t"+sourceList.get(i));
        }

       

        //调用shell命令，执行JLTMMR
        if (testOrNot == false) {
        	try {
				NLP.run(sourceList);
			} catch (MWException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
        }

        //读取output
        //String jlmlmrAbstractText = readOutput(FileDirectory.filePathJoin(runningDir, "output_jltmmr"));
        //String singlemrAbstractText = readOutput(FileDirectory.filePathJoin(runningDir, "output_singlemr"));
        //String randomChooseAbstractText = readOutput(FileDirectory.filePathJoin(runningDir, "output_random_choose"));
        //String jtmmrAbstractText = readOutput(FileDirectory.filePathJoin(runningDir, "output_jtmmr"));
        
        String jlmlmrAbstractText="";
        String singlemrAbstractText="" ;
        String randomChooseAbstractText="";
        String jtmmrAbstractText=""; 
        //构造info
        Info info = new Info();
        if (testOrNot == false) { //system
            ArrayList<String> time_sentence_filterSentence=null;
            info.time_stamp = nowTime;
            info.ori_sentence = getContent(time_sentence_filterSentence, 1);
            info.jlmlmr = jlmlmrAbstractText;
            info.jlmlmr_abs = jtmmrAbstractText;
            info.singlemr = singlemrAbstractText;
            info.random_choose = randomChooseAbstractText;
            info.filter_sentence = getContent(time_sentence_filterSentence, 2);
            ArrayList<String> topic_words = getTopicWords(FileDirectory.filePathJoin(null, "U100_1_0.txt"), FileDirectory.filePathJoin(FileDirectory.filePathJoin(null, "JLMLMR_running"), "wordmap_input_1.txt"), 30);
            info.topic_words = topic_words.get(0);
            info.search_query = "";
            info.urls = "";
            info.topic_words_score = topic_words.get(1);
        }
        else { //test
            info.time_stamp = nowTime;
            info.ori_sentence = "hey there\nwhat good\nbad news";
            info.jlmlmr = "hey there";
            info.jlmlmr_abs = "";
            info.singlemr = "wwww";
            info.random_choose = "123";
            info.filter_sentence = "hey there\ngood\nbad news";
            info.topic_words = "hey there\ngood\nbad news\nhey news";
            info.search_query = "";
            info.urls = "";
            info.topic_words_score = "0.9 0.8\n0.8\n1.0 0.2\n0.5 0.88";
        }
        result.status = true;
        result.info = info;
        System.out.println("done commonWork");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/topic_feedback", method = RequestMethod.POST)
    public boolean topicFeedback(String time_stamp, String topic_id, String add_num) {
        /*
        String timeStamp = request.getParameter("time_stamp").toString();
        String topicId = request.getParameter("topic_id").toString();
        String addNum = request.getParameter("add_num").toString();
        */
        System.out.println(time_stamp+" "+topic_id+" "+add_num);

        String runningDir = FileDirectory.filePathJoin("DealWithOriginText", time_stamp);
        ArrayList<String> topic_words = getTopicWords(FileDirectory.filePathJoin(runningDir, "U100_1_0.txt"), FileDirectory.filePathJoin(FileDirectory.filePathJoin(runningDir, "JLMLMR_running"), "wordmap_input_1.txt"), 10);
        String[] topicWords = topic_words.get(0).split("\n")[Integer.parseInt(topic_id)].split(" "); //\n隔开每个topic，空格隔开主题词

        String bigWordmapFilePath = FileDirectory.filePathJoin(FileDirectory.filePathJoin("DealWithOriginText", "JLMLMR_running"), "wordmap_wiki_cn_top1000000_en_top1000000.txt");
        String SMatrixFilePath = FileDirectory.filePathJoin(FileDirectory.filePathJoin("DealWithOriginText", "JLMLMR_running"), "SMatrix_Old2.txt");

        HashSet<Integer> wordmapIndexSet = new HashSet<Integer>(); //start from 0
        HashMap<String, Integer> wordmap = FileIO.fileReadToStringIndexMap(bigWordmapFilePath);
        for (int i=0; i<topicWords.length; i++) {
            wordmapIndexSet.add(wordmap.get(topicWords[i]));
        }

        // change SMatrix
        File file = new File(SMatrixFilePath);
        BufferedReader reader = null;
        try {
            File writename = new File(SMatrixFilePath+"."+time_stamp);
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768);

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
                if (wordmapIndexSet.contains(line)) {
                    String[] info = tempString.split(" ");
                    String lineContent = "";
                    for (int i=0; i<info.length; i++) {
                        String[] content = info[i].split(":");
                        int index = Integer.parseInt(content[0]);
                        double value = Double.parseDouble(content[1]);
                        if (wordmapIndexSet.contains(index-1)) {
                            //System.out.println(line+" "+index+" value from "+value+" to "+(value+Double.parseDouble(add_num)));
                            value += Double.parseDouble(add_num);
                            value = Math.max(value, 0.0);
                        }
                        if (lineContent.length() > 0) {
                            lineContent += " ";
                        }
                        lineContent += index + ":" + value;
                    }
                    out.write(lineContent);
                    out.write("\n");
                    out.flush();
                }
                else {
                    out.write(tempString);
                    out.write("\n");
                    out.flush();
                }
                line++;
            }
            reader.close();
            out.flush();
            out.close();

            //move file
            FileDirectory.moveFile(SMatrixFilePath+"."+time_stamp, SMatrixFilePath);
            System.out.println("topic feedback done");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/sentence_feedback", method = RequestMethod.POST)
    public boolean sentenceFeedback(String sentence, String feedback_status) throws IOException, InterruptedException {
        //feedback_status: 1 should, 0 should not, 2 maybe
        //deal with sentence
        String feedbackContent = "";
        if (sentence.charAt(sentence.length()-1) == '。') { //chinese
            for (int i=0; i<sentence.length()-1; i++) { //split word
                if (feedbackContent.length() > 0) {
                    feedbackContent += " ";
                }
                feedbackContent += sentence.charAt(i);
            }
        }
        else { //english
            feedbackContent = sentence.substring(0, sentence.length()-1); //remove .
        }
        String feedbackFilePath = FileDirectory.filePathJoin(FileDirectory.filePathJoin("DealWithOriginText", "sentence_feedback"), "feedback");
        //find exist or not
        ArrayList<String> feedbackList = FileIO.fileRead(feedbackFilePath);
        boolean changeOrNot = false;
        for (int i=feedbackList.size()-1; i>=0; i--) {
            if (feedbackList.get(i).equals("__label__1 "+feedbackContent) || feedbackList.get(i).equals("__label__0 "+feedbackContent) || feedbackList.get(i).equals("__label__2 "+feedbackContent)) {
                feedbackList.set(i, "__label__"+feedback_status+" "+feedbackContent);
                changeOrNot = true;
                break;
            }
        }
        //add
        if (changeOrNot == false) {
            feedbackList.add("__label__"+feedback_status+" "+feedbackContent);
        }
        //write and move
        String nowTime = getTime();
        FileIO.fileWrite(feedbackFilePath+"."+nowTime, feedbackList);
        FileDirectory.moveFile(feedbackFilePath+"."+nowTime, feedbackFilePath);
        //train fasttext
        String shpath = "./DealWithOriginText/sentence_feedback/fasttext supervised -input DealWithOriginText/sentence_feedback/feedback -output DealWithOriginText/sentence_feedback/fasttext.model -dim 128 -lr 0.015 -wordNgrams 3 -minCount 3 -bucket 100000 -epoch 15 -thread 24 -loss softmax";
        Process ps = Runtime.getRuntime().exec(shpath);
        ps.waitFor();
        return true;
    }

    public String getTime() {
        Date d = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSS");
        String timeStr=sdf.format(d);
        return timeStr;
    }

    public String getContent(ArrayList<String> time_sentence_filterSentence, int column) {
        String content = "";
        for (int i=0; i<time_sentence_filterSentence.size(); i++) {
            if (content.length() > 0) {
                content += "\n";
            }
            content += time_sentence_filterSentence.get(i).split("\t")[column];
        }
        return content;
    }

    public Double[][] getMatrixBySparseFile(String filePath, int M, int N) {
        Double[][] matrix = new Double[M][N];
        for (int i=0; i<M; i++) { //必须初始化，否则为null
            for (int j=0; j<N; j++) {
                matrix[i][j] = 0.0;
            }
        }
        ArrayList<String> content = FileIO.fileRead(filePath, "utf8");
        for (int i=0; i<content.size(); i++) {
            String[] indexValue = content.get(i).split(" ");
            for (int j=0; j<indexValue.length; j++) {
                if (indexValue[j].length() > 0) {
                    String[] info = indexValue[j].split(":");
                    Integer index = Integer.parseInt(info[0]);
                    Double value = Double.parseDouble(info[1]);
                    matrix[i][index-1] = value;
                }
            }
        }
        return matrix;
    }

    public ArrayList<String> getTopicWords(String UFilePath, String wordmapFilePath, int topK) {
        String topicWords = "";
        String topicWordsScore = "";
        ArrayList<String> wordmap = FileIO.fileRead(wordmapFilePath, "utf8");
        final int M = wordmap.size();
        final int K = 100;
        Double[][] U = getMatrixBySparseFile(UFilePath, M, K);
        HashMap<Integer, Double> indexValueMap = new HashMap<Integer, Double>();
        for (int i=0; i<K; i++) {
            indexValueMap.clear();
            for (int j=0; j<M; j++) {
                indexValueMap.put(j, U[j][i]);
            }
            ArrayList<Map.Entry<Integer, Double>> indexValueList = new ArrayList<Map.Entry<Integer, Double>>(indexValueMap.entrySet());
            Collections.sort(indexValueList, new Comparator<Map.Entry<Integer, Double>>() { //big to small
                @Override
                public int compare(Map.Entry<Integer, Double> aa, Map.Entry<Integer, Double> bb) {
                    /* 会卡死....
                    if (aa.getValue() > bb.getValue()) return -1;
                    return 1;
                    */
                    //return (int)((bb.getValue() - aa.getValue())*1000); //也会卡死
                    if (aa.getValue() > bb.getValue()) return -1;
                    else if (aa.getValue() < bb.getValue()) return 1;
                    return 0;
                }
            });
            String lineWords = "";
            String lineScore = "";
            for (int j=0; j<Math.min(indexValueList.size(), topK); j++) {
                if (lineWords.length() > 0) {
                    lineWords += " ";
                }
                lineWords += wordmap.get(indexValueList.get(j).getKey());
                if (lineScore.length() > 0) {
                    lineScore += " ";
                }
                lineScore += indexValueList.get(j).getValue();
            }
            if (topicWords.length() > 0) {
                topicWords += "\n";
            }
            topicWords += lineWords;
            if (topicWordsScore.length() > 0) {
                topicWordsScore += "\n";
            }
            topicWordsScore += lineScore;
        }
        ArrayList<String> topics = new ArrayList<String>();
        topics.add(topicWords);
        topics.add(topicWordsScore);
        return topics;
    }

    public String readOutput(String filePath) {
        String abstractText = "";
        if (testOrNot == false) {
            ArrayList<String> output = FileIO.fileRead(filePath, "utf8");
            for (int i = 0; i < output.size(); i++) {
                if (abstractText.length() > 0 ) {
                    abstractText += "\n";
                }
                abstractText += output.get(i);
            }
        }
        return abstractText;
    }
}
