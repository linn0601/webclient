package org.linn.webclient.proxys;

import lombok.extern.slf4j.Slf4j;
import org.linn.webclient.Integer.ProxyCreator;
import org.linn.webclient.Integer.RestHandler;
import org.linn.webclient.annotation.ApiServer;
import org.linn.webclient.bean.MethodInfo;
import org.linn.webclient.bean.ServiceInfo;
import org.linn.webclient.handle.WebClientRestHandle;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * created by linn  20/3/2 16:03
 * 使用jdk动态代理
 */
@Slf4j
public class JDKProxyCreator implements ProxyCreator {

    @Override
    public Object createProxy(Class<?> type) {
        //根据接口获得api信息
        ServiceInfo serviceInfo = extractServiceInfo(type);
        log.info("serviceInfo : " + serviceInfo);
        //给每一个代理类实现
        RestHandler handler = new WebClientRestHandle();
        //初始化
        handler.init(serviceInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //根据方法和参数得到调用信息
                MethodInfo methodInfo = extractMethodInfo(method, args);
                log.info("methodInfo :" + methodInfo);
                //调用rest
                return handler.invokeRest(methodInfo);
            }

            /**
             * 根据定义方法和调用参数得到调用相关的信息
             * @param method
             * @param args
             * @return
             */
            private MethodInfo extractMethodInfo(Method method, Object[] args) {
                MethodInfo methodInfo = new MethodInfo();
                //获取请求方法 和 参数
                extractUrlAndMethod(method, methodInfo);
                //获取返回值的类型，返回对象
                extractReturnInfo(method,methodInfo);
                //获取所有参;
                extractRequest(method, args, methodInfo);
                return methodInfo;
            }
        });
    }

    /**
     * 提取返回对象信息
     * @param method
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setResult(isFlux);
        //得到返回对象的实际类型
        Class<?> elementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(elementType);
    }

    /**
     * 得到返回对象的实际类型
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    /**
     * 得到请求的url和方法名
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
        //获取请求方法 和 参数
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            //判断是否是get请求
            if (annotation instanceof GetMapping) {
                GetMapping getInfo = (GetMapping) annotation;
                //设置
                methodInfo.setUrl(getInfo.value()[0]);//只取第一个
                methodInfo.setMethod(HttpMethod.GET);
            }
            //判断是否是post请求
            if (annotation instanceof PostMapping) {
                PostMapping getInfo = (PostMapping) annotation;
                //设置
                methodInfo.setUrl(getInfo.value()[0]);//只取第一个
                methodInfo.setMethod(HttpMethod.POST);
            }
            //判断是否是put请求
            if (annotation instanceof PutMapping) {
                PutMapping getInfo = (PutMapping) annotation;
                //设置
                methodInfo.setUrl(getInfo.value()[0]);//只取第一个
                methodInfo.setMethod(HttpMethod.PUT);
            }
            //判断是否是delete请求
            if (annotation instanceof DeleteMapping) {
                DeleteMapping getInfo = (DeleteMapping) annotation;
                //设置
                methodInfo.setUrl(getInfo.value()[0]);//只取第一个
                methodInfo.setMethod(HttpMethod.DELETE);
            }
        }
    }

    /**
     * 得到请求的param 和 body
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractRequest(Method method, Object[] args, MethodInfo methodInfo) {
        Parameter[] paras = method.getParameters();
        //接收参数的map
        Map<String, Object> parameters = new LinkedHashMap<>();
        methodInfo.setParams(parameters);

        for (int i = 0; i < paras.length; i++) {
            //参数是否带PathVariable注解
            PathVariable pathVariable = paras[i].getAnnotation(PathVariable.class);
            //如果这个注解，用mao来接受 id : id
            if (pathVariable != null) {
                parameters.put(pathVariable.value(),args[i]);
            }
            //参数是否有RequestBody
            RequestBody requestBody = paras[i].getAnnotation(RequestBody.class);
            if (requestBody != null){
                methodInfo.setBody((Mono<?>)args[i]);
                //请求对象的实际类型
                methodInfo.setBodyElementType(extractElementType(paras[i].getParameterizedType()));
            }
        }
    }

    /**
     * 获取api注解上服务地址信息
     *
     * @param type
     * @return
     */
    private ServiceInfo extractServiceInfo(Class<?> type) {
        ServiceInfo serviceInfo = new ServiceInfo();
        //获取注解
        ApiServer info = type.getAnnotation(ApiServer.class);
        serviceInfo.setUrl(info.value()); //获取注解上信息
        return serviceInfo;
    }
}
