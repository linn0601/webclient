package org.linn.webclient.controller;

import org.linn.webclient.api.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * created by linn  20/3/2 15:20
 */
@RestController
class TestController {

    @Autowired
    private IUserApi userApi;

    @GetMapping("/get")
    public void test(){
        //测试信息
        /*userApi.getUsers();
        userApi.getUserById("111");
        userApi.addUser(Mono.just(User.builder().name("linn").age(333).build()));*/
        //直接调用
      /*  Flux<User> user = userApi.getUsers();
        user.subscribe(System.out::println);*/

        String id = "1";
        userApi.getUserById(id).subscribe(u -> {
            System.out.println("getUserById: " + u);
        }, e -> {
            System.err.println("找不到用户: " + e.getMessage());
        });

        /*userApi.addUser(Mono.just(User.builder().name("张大庄").age(19).build())).subscribe(System.out::println);*/
    }

}
