package org.linn.webclient.api;

import org.linn.webclient.annotation.ApiServer;
import org.linn.webclient.entitiy.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by linn  20/3/2 13:53
 */
@Repository
@ApiServer("http://localhost:8080/user")
public interface IUserApi {

    @GetMapping("/all")
    Flux<User> getUsers();

    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);

    @PostMapping("")
    Mono<Void> addUser(@RequestBody Mono<User> user);

    @DeleteMapping("/{id}")
    Mono<Void> deleteByUserId(@PathVariable("id") String id);

}
