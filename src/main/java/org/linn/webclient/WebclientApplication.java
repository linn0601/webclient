package org.linn.webclient;

import org.linn.webclient.Integer.ProxyCreator;
import org.linn.webclient.api.IUserApi;
import org.linn.webclient.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebclientApplication.class, args);
    }

    //创建JDKProxyCreator
    @Bean
    ProxyCreator jdkProxyCreator(){
        return new JDKProxyCreator();
    }

    @Bean
    FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator){
        return new FactoryBean<IUserApi>() {
            //返回代理对象
            @Override
            public IUserApi getObject() throws Exception {
                return (IUserApi)proxyCreator.createProxy(this.getObjectType());
            }

            //获取对象类型
            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }
}
