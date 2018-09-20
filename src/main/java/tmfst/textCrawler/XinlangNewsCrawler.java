package tmfst.textCrawler;

import tmfst.crawler.handler.handler_XinlangNews;
import edu.as.sys.model.Comment;
import edu.as.sys.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 2017/2/22.
 */
public class XinlangNewsCrawler implements CommentCrawler {
    //static Logger mylog = LoggerFactory.getLogger(XinlangNewsCrawler.class.getName());

    public List<News> get_source_list(String keyword) throws UnsupportedEncodingException {
        String searchUrl = "http://api.search.sina.com.cn/?q=" + URLEncoder.encode(keyword, "UTF-8") + "&c=news&sort=time&ie=utf-8";

        long before = System.currentTimeMillis();
        List<News> list = new ArrayList<News>();
        handler_XinlangNews.crawler_source(searchUrl, list);

        //mylog.error("#####################" + (System.currentTimeMillis() - before) / 1000 + "############################");
        return list;
    }
}
