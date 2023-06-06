package me.liuzeyu.aimusic.music.dao;

import jdk.net.SocketFlow;
import me.liuzeyu.aimusic.music.api.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import me.liuzeyu.aimusic.music.dao.entity.Music;

import java.util.List;
import java.util.Optional;

public interface MusicDao
        extends JpaRepository<Music, Long> {
    List<Music> findAllByUser(Long userId);

    Optional<Music> findByUserAndHash(Long user, String hash);

    List<Music> findByUserNotAndStatus(Long userId, StatusType status);

    List<Music> findByUserAndStatusNot(Long userId, StatusType status);

    List<Music> findByUserAndStatus(Long userId, StatusType status);
}
