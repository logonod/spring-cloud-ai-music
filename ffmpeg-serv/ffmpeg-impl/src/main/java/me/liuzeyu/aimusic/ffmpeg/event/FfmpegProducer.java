package me.liuzeyu.aimusic.ffmpeg.event;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.ffmpeg.api.beans.Music;
import me.liuzeyu.aimusic.ffmpeg.api.beans.FfmpegResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
public class FfmpegProducer {
    @Autowired
    private StreamBridge streamBridge;

    public FfmpegResponse sendFfmpeg(@Valid Music music) {
        log.info("sent: {}", music);
        try {
            streamBridge.send(EventConstant.ADD_FFMPEG_EVENT, music);
            return FfmpegResponse.builder().status("ok").build();
        } catch (Exception e) {
            return FfmpegResponse.builder().status(e.toString()).build();
        }
    }
}
