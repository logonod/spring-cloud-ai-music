package me.liuzeyu.aimusic.auth.service;

import me.liuzeyu.aimusic.auth.api.beans.MusicUserDetails;
import me.liuzeyu.aimusic.auth.service.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class MusicUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public MusicUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userService
                .findByUsername(username)
                .map(MusicUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
