package me.liuzeyu.aimusic.ffmpeg.controller;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.ffmpeg.api.beans.Music;
import me.liuzeyu.aimusic.ffmpeg.api.beans.FfmpegResponse;
import me.liuzeyu.aimusic.ffmpeg.event.FfmpegProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("ffmpeg")
public class FfmpegController {
    @Autowired
    private FfmpegProducer ffmpegProducer;

    @PostMapping("request")
    public FfmpegResponse request(@Valid @RequestBody Music request) {
        log.info("received svc request: data={}", request);
        return ffmpegProducer.sendFfmpeg(request);
    }

}
