package me.liuzeyu.aimusic.spleeter.controller;


import me.liuzeyu.aimusic.spleeter.api.beans.Music;
import me.liuzeyu.aimusic.spleeter.api.beans.SpleeterResponse;
import me.liuzeyu.aimusic.spleeter.event.SpleeterProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("spleeter")
public class SpleeterController {

    @Autowired
    private SpleeterProducer spleeterProducer;

    @PostMapping("request")
    public SpleeterResponse request(@Valid @RequestBody Music request) {
        log.info("received spleeter request: data={}", request);
        return spleeterProducer.sendSpleeter(request);
    }

}
