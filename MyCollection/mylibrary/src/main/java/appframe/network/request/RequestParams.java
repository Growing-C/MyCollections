package appframe.network.request;

import java.io.Serializable;

/**
 * Created by RB-cgy on 2016/9/28.
 */
public class RequestParams<T> implements Serializable {
    public String requestToken;
    public T requestData;

}
