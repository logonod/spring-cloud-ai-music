package me.liuzeyu.aimusic.music.service;



import me.liuzeyu.aimusic.music.api.enums.StatusType;
import me.liuzeyu.aimusic.music.dao.MusicDao;
import me.liuzeyu.aimusic.music.dao.entity.Music;
import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.music.feign.SpleeterService;
import me.liuzeyu.aimusic.spleeter.api.beans.SpleeterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import me.liuzeyu.aimusic.music.api.beans.*;
import me.liuzeyu.aimusic.music.service.intf.MusicService;
import me.liuzeyu.aimusic.music.converter.FileConverter;
import me.liuzeyu.aimusic.music.converter.MusicConverter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券模板类相关操作
 */
@Slf4j
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicDao musicDao;

    @Autowired
    private SpleeterService spleeterService;

    @Value("${file.upload.path}")
    private String path;

    @Override
    public MusicInfo updateMusic(UpdateMusicInfo request) {
        Music music = musicDao.findByUserAndHash(request.getUser(), request.getHash())
                .orElseThrow(() -> new IllegalArgumentException("invalid user ID and hash"));
        music.setStatus(StatusType.convert(request.getStatus()));
        musicDao.save(music);
        return MusicConverter.convertToMusicInfo(music);
    }

    @Override
    public MusicInfo createMusic(MusicInfo request, MultipartFile upload) throws IOException, NoSuchAlgorithmException {
        File uuidFile = FileConverter.convertToMP3(FileConverter.convertFromMultipartFile(upload, path));

        Music music = Music.builder()
                .user(request.getUser())
                .hash(FileConverter.convertToHash(upload, uuidFile, path, request.getUser(), request.getAi()))
                .title(request.getTitle())
                .artist(request.getArtist())
                .ai(request.getAi())
                .status(StatusType.convert("0"))
                .build();
        music = musicDao.save(music);

        SpleeterResponse spleeterResponse = spleeterService.requestSpleeter(music);

        if(!spleeterResponse.getStatus().equals("ok")) {
            throw new RuntimeException("spleeter message queue request failed");
        }

        return MusicConverter.convertToMusicInfo(music);
    }

    @Override
    public MusicListInfo otherMusic(Long id) {
        List<Music> musics = musicDao.findByUserNotAndStatus(id, StatusType.FINISHED);
        List<MusicResponse> list = musics.stream().map(m -> MusicResponse.builder()
                .id(m.getId())
                .ai(m.getAi())
                .title(m.getTitle())
                .user(m.getUser())
                .artist(m.getArtist())
                .hash(m.getHash())
                .status(m.getStatus().getCode())
                .build()).collect(Collectors.toList());
        MusicListInfo musicListInfo = MusicListInfo.builder()
                .list(list)
                .build();
        return musicListInfo;
    }

    @Override
    public MusicListInfo pendingMusic(Long id) {
        List<Music> musics = musicDao.findByUserAndStatusNot(id, StatusType.FINISHED);
        List<MusicResponse> list = musics.stream().map(m -> MusicResponse.builder()
                .id(m.getId())
                .ai(m.getAi())
                .title(m.getTitle())
                .user(m.getUser())
                .artist(m.getArtist())
                .hash(m.getHash())
                .status(m.getStatus().getCode())
                .build()).collect(Collectors.toList());
        MusicListInfo musicListInfo = MusicListInfo.builder()
                .list(list)
                .build();
        return musicListInfo;
    }

    @Override
    public MusicListInfo finishedMusic(Long id) {
        List<Music> musics = musicDao.findByUserAndStatus(id, StatusType.FINISHED);
        List<MusicResponse> list = musics.stream().map(m -> MusicResponse.builder()
                .id(m.getId())
                .ai(m.getAi())
                .title(m.getTitle())
                .user(m.getUser())
                .artist(m.getArtist())
                .hash(m.getHash())
                .status(m.getStatus().getCode())
                .build()).collect(Collectors.toList());
        MusicListInfo musicListInfo = MusicListInfo.builder()
                .list(list)
                .build();
        return musicListInfo;
    }
}
