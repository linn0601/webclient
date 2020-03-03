package org.linn.webclient.Integer;

import org.linn.webclient.bean.MethodInfo;
import org.linn.webclient.bean.ServiceInfo;

/**
 * created by linn  20/3/2 16:18
 **/
public interface RestHandler {

    /**
     * 初始化服务器信息
     * @param serviceInfo
     */
    void init(ServiceInfo serviceInfo);

    /**
     * 调用rest
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
