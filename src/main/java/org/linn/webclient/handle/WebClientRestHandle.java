package org.linn.webclient.handle;

import org.linn.webclient.Integer.RestHandler;
import org.linn.webclient.bean.MethodInfo;
import org.linn.webclient.bean.ServiceInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by linn  20/3/2 17:09
 */
public class WebClientRestHandle implements RestHandler {

    WebClient webClient;

    /**
     * 初始化webClient
     * @param serviceInfo
     */
    @Override
    public void init(ServiceInfo serviceInfo) {
        //传入服务器地址
        this.webClient = WebClient.create(serviceInfo.getUrl());
    }

    /**
     * 处理rest请求
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        //定义返回结果
        Object result = null;
        WebClient.RequestBodySpec request = this.webClient.method(methodInfo.getMethod())
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                .accept(MediaType.APPLICATION_JSON);
        WebClient.ResponseSpec retrieve = null;
        if (methodInfo.getBody()!= null){
            retrieve = request.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        }else {
            retrieve = request.retrieve();
        }
        //处理异常
        retrieve.onStatus(status -> status.value() == 404, response ->
            Mono.just(new RuntimeException("Not find")));

        //处理body
        if (methodInfo.isResult()){
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        }else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }
        return result;
    }


}
