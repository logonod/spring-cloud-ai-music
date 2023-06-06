package me.liuzeyu.me.aimusic.svc.event;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.svc.api.beans.Music;
import me.liuzeyu.aimusic.svc.api.beans.SvcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
public class SvcProducer {
    @Autowired
    private StreamBridge streamBridge;

    public SvcResponse sendSvc(@Valid Music music) {
        log.info("sent: {}", music);
        try {
            streamBridge.send(EventConstant.ADD_SVC_EVENT, music);
            return SvcResponse.builder().status("ok").build();
        } catch (Exception e) {
            return SvcResponse.builder().status(e.toString()).build();
        }
    }
}
