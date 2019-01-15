package com.lokiy.wf.service;

import com.lokiy.wf.dao.UserRepository;
import com.lokiy.wf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * discription:
 *
 * @author Lokiy
 * @date 2019-01-04
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> save(User user){
        return userRepository.save(user)
                .onErrorResume(e ->
                    userRepository.findByUsername(user.getUsername())
                        .flatMap(ori -> {
                                user.setId(ori.getId());
                                return userRepository.save(user);
                        }));
    }

    public Mono<Long> deleteByUsername(String username){
        return userRepository.deleteByUsername(username);
    }

    public Mono<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Flux<User> findAll(){
        return userRepository.findAll();
    }
}
