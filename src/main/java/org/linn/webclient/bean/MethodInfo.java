package org.linn.webclient.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * created by linn  20/3/2 16:11
 * 方法调用信息类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 参数
     */
    private Map<String,Object> params;

    /**
     * 请求体
     */
    private Mono body;

    /**
     * 返回的时Mono还是flux
     */
    private boolean result;

    /**
     * 返回对象的类型
     */
    private Class<?> returnElementType;

    /**
     * 请求bodyElement
     */
    private Class<?> bodyElementType;
}
