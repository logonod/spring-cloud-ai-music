package me.liuzeyu.aimusic.ffmpeg.event;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.ffmpeg.caculator.FfmpegCaculator;
import me.liuzeyu.aimusic.ffmpeg.feign.MusicService;
import me.liuzeyu.aimusic.music.api.beans.UpdateMusicInfo;
import me.liuzeyu.aimusic.music.api.enums.StatusType;
import me.liuzeyu.aimusic.spleeter.api.beans.SvcResponse;
import me.liuzeyu.aimusic.svc.api.beans.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
@Service
public class FfmpegConsumer {
    @Autowired
    private FfmpegCaculator ffmpegCaculator;

    @Autowired
    private MusicService musicService;

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Value("${file.upload.path}")
    private String path;

    @Bean
    public Consumer<Music> addFfmpeg() {
        return request -> {
            log.info("received: {}", request);
            try {
                ffmpegCaculator.run(request.getHash(), path, ffmpegPath);

                UpdateMusicInfo music = UpdateMusicInfo
                        .builder()
                        .hash(request.getHash())
                        .user(request.getUser())
                        .status(StatusType.FINISHED.getCode())
                        .build();
                musicService.updateMusic(music);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
