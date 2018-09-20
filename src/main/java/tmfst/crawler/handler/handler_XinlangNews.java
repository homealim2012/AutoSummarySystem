package tmfst.crawler.handler;

import tmfst.crawler.processor.crawler_XinlangNews;
import edu.as.sys.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Created by dell on 2016/5/6.
 */
public class handler_XinlangNews {

    private static Logger mylog = LoggerFactory.getLogger(crawler_XinlangNews.class.getName());

    public static void crawler_source(String url, List<News> list) {
        crawler_XinlangNews processor = new crawler_XinlangNews(list);
        Spider spider = Spider.create(processor)
                .addUrl(url)
                .thread(2);
        spider.run();
        //判断是否结束：status有3种：Init, Running, Stopped
        while (true) {
            if (spider.getStatus().toString().equals("Stopped")) {
                System.out.println("Spider stopped");
                break;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
