package me.liuzeyu.aimusic.spleeter.event;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.music.api.beans.UpdateMusicInfo;
import me.liuzeyu.aimusic.music.api.enums.StatusType;
import me.liuzeyu.aimusic.spleeter.api.beans.Music;
import me.liuzeyu.aimusic.spleeter.api.beans.SpleeterResponse;
import me.liuzeyu.aimusic.spleeter.api.beans.SvcResponse;
import me.liuzeyu.aimusic.spleeter.caculator.SpleeterCaculator;
import me.liuzeyu.aimusic.spleeter.feign.MusicService;
import me.liuzeyu.aimusic.spleeter.feign.SvcService;
import org.bouncycastle.dvcs.DVCSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
@Service
public class SpleeterConsumer {

    @Autowired
    private SpleeterCaculator spleeterCaculator;

    @Autowired
    private SvcService svcService;

    @Autowired
    private MusicService musicService;

    @Value("${spleeter.command}")
    private String command;

    @Value("${file.upload.path}")
    private String path;

    @Bean
    public Consumer<Music> addSpleeter() {
        return request -> {
            log.info("received: {}", request);
            try {
                spleeterCaculator.run(request.getHash(), path, command);

                SvcResponse svcResponse = svcService.requestSvc(request);
                if(!svcResponse.getStatus().equals("ok")) {
                    throw new RuntimeException("svc message queue request failed");
                }
                UpdateMusicInfo music = UpdateMusicInfo
                        .builder()
                        .hash(request.getHash())
                        .user(request.getUser())
                        .status(StatusType.SPLITTED.getCode())
                        .build();
                musicService.updateMusic(music);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
