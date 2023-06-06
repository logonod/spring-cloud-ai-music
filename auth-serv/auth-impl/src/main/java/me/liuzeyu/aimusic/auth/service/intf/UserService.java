package me.liuzeyu.aimusic.auth.service.intf;

import me.liuzeyu.aimusic.auth.dao.entity.User;

import java.util.Optional;

public interface UserService {
    String loginUser(String username, String password);

    Optional<User> findByUsername(String username);

    User registerUser(User user);
}
