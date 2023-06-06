package me.liuzeyu.me.aimusic.svc.controller;

import me.liuzeyu.aimusic.svc.api.beans.Music;
import me.liuzeyu.aimusic.svc.api.beans.SvcResponse;
import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.me.aimusic.svc.event.SvcProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("svc")
public class SvcController {

    @Autowired
    private SvcProducer svcProducer;

    @PostMapping("request")
    public SvcResponse request(@Valid @RequestBody Music request) {
        log.info("received svc request: data={}", request);
        return svcProducer.sendSvc(request);
    }

}
