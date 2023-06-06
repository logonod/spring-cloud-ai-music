package me.liuzeyu.me.aimusic.svc.feign;

import me.liuzeyu.aimusic.ffmpeg.api.beans.FfmpegResponse;
import me.liuzeyu.aimusic.svc.api.beans.Music;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "ffmpeg-serv", path = "/ffmpeg")
public interface FfmpegService {

    // 创建干声伴奏合成任务
    @PostMapping("/request")
    FfmpegResponse requestFfmpeg(Music music);

}