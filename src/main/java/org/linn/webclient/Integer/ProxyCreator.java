package org.linn.webclient.Integer;

/**
 * created by linn  20/3/2 15:57
 * 创建代理类接口
 */

public interface ProxyCreator {

    //创建代理类
    Object createProxy(Class<?> type);
}
