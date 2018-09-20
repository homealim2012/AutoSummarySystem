package tmfst.crawler.common;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by dell on 2016/12/19.
 */
public class CrawlText {
//    private Site site = Site.me()
//            .setSleepTime(500)
//            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8;charset=utf-8")
//            .setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

    public String get_text(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            result = EntityUtils.toString(entity1);
        } finally {
            response1.close();
        }

        return result;
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        String url = "https://rate.tmall.com/list_detail_rate.htm?itemId=541826951378&sellerId=1714128138&order=3&append=0&content=1&tagId=&posi=&picture=&&currentPage=";
        for (int i = 1; i < 80; i++) {
            System.out.print("%%%%%%%%");
            long before = System.currentTimeMillis();
            (new CrawlText()).get_text(url + i);
            System.out.println("#######:\t" + (System.currentTimeMillis() - before));
//            Thread.sleep(100);
        }
    }
}
