package me.liuzeyu.aimusic.music.service.intf;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import me.liuzeyu.aimusic.music.api.beans.*;
import me.liuzeyu.aimusic.music.dao.entity.Music;

public interface MusicService {

    MusicInfo updateMusic(UpdateMusicInfo request);

    MusicInfo createMusic(MusicInfo request, MultipartFile upload) throws IOException, NoSuchAlgorithmException;

    MusicListInfo otherMusic(Long id);

    MusicListInfo pendingMusic(Long id);

    MusicListInfo finishedMusic(Long id);

}
