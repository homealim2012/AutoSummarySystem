/**
 * @date 2016年2月21日 下午9:28:17
 * @version V1.0
 */
package tmfst.crawler.common;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

/**
 * @author ffftzh
 * @ClassName: DonothingDuplicateRemover
 * @Description: TODO
 * @date 2016年2月21日 下午9:28:17
 */
public class DonothingDuplicateRemover implements DuplicateRemover {

    /* (non-Javadoc)
     * @see us.codecraft.webmagic.scheduler.component.DuplicateRemover#isDuplicate(us.codecraft.webmagic.Request, us.codecraft.webmagic.Task)
     */
    public boolean isDuplicate(Request request, Task task) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see us.codecraft.webmagic.scheduler.component.DuplicateRemover#resetDuplicateCheck(us.codecraft.webmagic.Task)
     */
    public void resetDuplicateCheck(Task task) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see us.codecraft.webmagic.scheduler.component.DuplicateRemover#getTotalRequestsCount(us.codecraft.webmagic.Task)
     */
    public int getTotalRequestsCount(Task task) {
        // TODO Auto-generated method stub
        return 0;
    }

}
