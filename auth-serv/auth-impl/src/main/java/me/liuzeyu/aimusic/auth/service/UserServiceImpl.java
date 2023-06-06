package me.liuzeyu.aimusic.auth.service;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.auth.api.beans.MusicUserDetails;
import me.liuzeyu.aimusic.auth.dao.UserDao;
import me.liuzeyu.aimusic.auth.dao.entity.User;
import me.liuzeyu.aimusic.auth.service.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public String loginUser(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        User user = findByUsername(username)
                .map(MusicUserDetails::new)
                .orElse(null);
        usernamePasswordAuthenticationToken.setDetails(user);
        Authentication authentication = authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public User registerUser(User user) {
        log.info("registering user {}", user.getUsername());

        if(userDao.existsByUsername(user.getUsername())) {
            log.warn("username {} already exists.", user.getUsername());

            throw new RuntimeException(
                    String.format("username %s already exists", user.getUsername()));
        }

        if(userDao.existsByEmail(user.getEmail())) {
            log.warn("email {} already exists.", user.getEmail());

            throw new RuntimeException(
                    String.format("email %s already exists", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        return userDao.save(user);
    }

}
