package me.liuzeyu.me.aimusic.svc.event;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.ffmpeg.api.beans.FfmpegResponse;
import me.liuzeyu.aimusic.music.api.beans.UpdateMusicInfo;
import me.liuzeyu.aimusic.music.api.enums.StatusType;
import me.liuzeyu.aimusic.svc.api.beans.Music;
import me.liuzeyu.me.aimusic.svc.caculator.CalculationFactory;
import me.liuzeyu.me.aimusic.svc.caculator.SvcTemplate;
import me.liuzeyu.me.aimusic.svc.feign.FfmpegService;
import me.liuzeyu.me.aimusic.svc.feign.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
@Service
public class SvcConsumer {
    @Autowired
    private CalculationFactory calculationFactory;

    @Autowired
    private FfmpegService ffmpegService;

    @Autowired
    private MusicService musicService;

    @Value("${python.path}")
    private String pythonPath;

    @Value("${svc.svcPath}")
    private String svcPath;

    @Value("${file.upload.path}")
    private String path;

    @Bean
    public Consumer<Music> addSvc() {
        return request -> {
            log.info("received: {}", request);
            try {
                SvcTemplate svc = calculationFactory.getSvc(request);
                svc.run(request.getHash(), path, pythonPath, svcPath);
                FfmpegResponse ffmpegResponse = ffmpegService.requestFfmpeg(request);
                if(!ffmpegResponse.getStatus().equals("ok")) {
                    throw new RuntimeException("svc message queue request failed");
                }
                UpdateMusicInfo music = UpdateMusicInfo
                        .builder()
                        .hash(request.getHash())
                        .user(request.getUser())
                        .status(StatusType.CONVERTTED.getCode())
                        .build();
                musicService.updateMusic(music);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
