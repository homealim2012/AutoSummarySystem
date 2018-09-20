package edu.as.sys.model;

//import com.jfinal.plugin.activerecord.Model;
//import edu.cn.nlsde.tmfst.config.Config;

import edu.as.sys.config.DBUitil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2016/5/6.
 */
public class Info {

    public int id;
    public String time_stamp; //时间文件夹
    public String ori_sentence; //原文(每个句子\n隔开)
    public String jlmlmr; //jlmlmr生成的摘要
    public String jlmlmr_abs; //jlmlmr_abs生成的摘要
    public String singlemr; //singlemr生成的摘要
    public String random_choose; //随机选择句子生成的摘要
    public String filter_sentence; //原文分词后的，每个句子以\n隔开，对应于ori_sentence
    public String topic_words; //每行以\n隔开，每行代表一个主题，由以空格隔开的top30个主题词组成
    public String search_query; //用户输入的搜索句子
    public String urls; //对应搜索出来的新浪news的文章url，每行以\n隔开，一行一个url
    public String topic_words_score; //每行以\n隔开，每行代表一个主题，由以空格隔开的top30个主题词的分数组成，对应topic_words
}
