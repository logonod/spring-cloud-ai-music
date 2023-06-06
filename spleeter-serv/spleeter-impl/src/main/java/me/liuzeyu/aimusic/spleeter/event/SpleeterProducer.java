package me.liuzeyu.aimusic.spleeter.event;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.spleeter.api.beans.Music;
import me.liuzeyu.aimusic.spleeter.api.beans.SpleeterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpleeterProducer {
    @Autowired
    private StreamBridge streamBridge;

    public SpleeterResponse sendSpleeter(Music music) {
        log.info("sent: {}", music);
        try {
            streamBridge.send(EventConstant.ADD_SPLEETER_EVENT, music);
            return SpleeterResponse.builder().status("ok").build();
        } catch (Exception e) {
            return SpleeterResponse.builder().status(e.toString()).build();
        }
    }
}
