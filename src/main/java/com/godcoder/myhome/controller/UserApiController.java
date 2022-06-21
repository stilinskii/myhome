package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserRepository repository;



    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all(String content) {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

//    @GetMapping("/users/{id}")
//    User one(@PathVariable Long id) {
//
//        return repository.findById(id).orElse(null);
//    }
//
//    @GetMapping("/users/{title}")
//    User one(@PathVariable String title) {
//        return repository.findAll().stream().filter(e -> e.getTitle().equals(title)).findAny().get();
//
//    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
                    user.setBoards(newUser.getBoards());
                    for (Board board : user.getBoards()) {
                        board.setUser(user);
                    }

                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
