package appframe.network.request;

/**
 * 有分页的接口使用,必须继承RequestParams  不然token 的刷新会有问题
 * Created by RB-cgy on 2016/9/28.
 */
public class PageRequestParams<T> extends RequestParams<T> {

    public int pageNumber;//由于int类型在gson生成json的时候不会为null 导致必定会生成该字段 所以单独列出来
    public Integer pageSize;
}
