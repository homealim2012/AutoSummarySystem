package tmfst.crawler.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by dell on 2016/5/5.
 */
public class weiboCrawler implements PageProcessor {

    private static Logger mylog = LoggerFactory.getLogger(weiboCrawler.class.getName());
    private Site site = Site.me()
            .setSleepTime(500)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8;charset=utf-8")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

    @Override
    public void process(Page page) {
//        page.addTargetRequest(page.getUrl().toString());
//        try {
//            JSONArray result = JSONObject.parseObject(page.getRawText()).getJSONArray("statuses");
//            for (Object item : result) {
//                JSONObject weiboOne = (JSONObject) item;
//                JSONObject userOne = weiboOne.getJSONObject("user");
//                if (!User.exist(userOne.getLong("id"))) {
//                    User user = new User();
////                    user.user_image_position = PictureDownloader.downImgs(userOne.getString("avatar_hd"));
//                    user.user_id = userOne.getLong("id");
//                    user.user_name = userOne.getString("name");
//                    user.user_city = userOne.getString("city");
//                    user.user_province = userOne.getString("province");
//                    user.user_location = userOne.getString("location");
//                    user.user_description = userOne.getString("description");
//                    user.user_image_url = userOne.getString("avatar_hd");
//                    user.user_gender = userOne.getString("gender").equals("f") ? 1 : 0;
//                    user.user_friends_count = userOne.getInteger("friends_count");
//                    user.user_followers_count = userOne.getInteger("followers_count");
//                    user.user_creation_time = Utils.String2Date(userOne.getString("created_at"), "EEE MMM dd HH:mm:ss Z yyyy");
//                    user.user_verified = userOne.getBoolean("verified") ? 1 : 0;
//                    user.user_statuses_count = userOne.getInteger("statuses_count");
//                    System.out.println("用户名:" + user.user_name);
//                    user.save_model();
//                }
//                Weibo weibo = new Weibo();
//                weibo.weibo_id = weiboOne.getLong("id");
//                weibo.weibo_time = Utils.String2Date(weiboOne.getString("created_at"), "EEE MMM dd HH:mm:ss Z yyyy");
//                weibo.weibo_content = weiboOne.getString("text");
//                System.out.println("微博内容:" + weibo.weibo_content);
//                weibo.user_id = userOne.getLong("id");
//                weibo.save_model();
//
//            }
//        } catch (Exception e) {
//            mylog.error("", e);
//        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
