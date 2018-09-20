package edu.as.sys.common;

import edu.as.sys.model.ModelResult;

/**
 * Created by dell on 2016/12/19.
 */
public class CommentResult extends ResponseResult {
    public String product_id = "";
    public String platform = "";
    public ModelResult model;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public ModelResult getModel() {
        return model;
    }

    public void setModel(ModelResult model) {
        this.model = model;
    }
}
