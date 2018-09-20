package tmfst.textCrawler;

import edu.as.sys.model.News;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by dell on 2016/12/19.
 */
public interface CommentCrawler {

    List<News> get_source_list(String keyword) throws UnsupportedEncodingException;
}
