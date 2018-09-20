package tmfst.crawler.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.as.sys.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/5/6.
 */
public class crawler_XinlangNews implements PageProcessor {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger mylog = LoggerFactory.getLogger(crawler_XinlangNews.class.getName());
    public List<News> list = null;
    private Site site = Site.me()
            .setSleepTime(1000)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8;charset=utf-8")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
            .setCycleRetryTimes(10)
            .setRetrySleepTime(1000)
            .setUseGzip(true)
            .setTimeOut(10000);
    //private Site site = Site.me().setRetryTimes(10).setSleepTime(3000).setUseGzip(true).setTimeOut(10000);

    public crawler_XinlangNews(List<News> list) {
        this.list = list;
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().toString().contains("api.search.sina.com.cn") == true) { //search页面
            try {
                JSONObject raw = JSONObject.parseObject(page.getRawText());
                JSONArray result = raw.getJSONObject("result").getJSONArray("list");
                for (Object item : result) {
                    JSONObject oneUrl = (JSONObject) item;
                    String newsUrl = oneUrl.getString("url");
                    page.addTargetRequest(newsUrl);
                }
            } catch (Exception e) {
                mylog.error("", e);
            }
        }
        else { //内容页面
            String content1 = page.getHtml().xpath("//*[@id=\"artibody\"]/allText()").toString();
            String content2 = page.getHtml().xpath("//*[@id=\"article\"]/allText()").toString();
            String title = page.getHtml().xpath("//html/body/div/h1[@class=\"main-title\"]/text()").toString();
            String publishTime = page.getHtml().xpath("//*[@id=\"top_bar\"]/div/div[2]/span/text()").toString();
            News news = new News();
            news.url = page.getUrl().toString();
            news.title = (title == null) ? "" : title;
            news.publishTime = (publishTime == null) ? "2099年12月31日 23:59" : publishTime;
            news.content = (content1 == null) ? "" : content1;
            news.content += (content2 == null) ? "" : content2;
            list.add(news);
            System.out.println("work: " + news.url);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
