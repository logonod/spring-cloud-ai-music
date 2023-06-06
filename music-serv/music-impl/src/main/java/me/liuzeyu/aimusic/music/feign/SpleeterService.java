package me.liuzeyu.aimusic.music.feign;

import me.liuzeyu.aimusic.music.dao.entity.Music;
import me.liuzeyu.aimusic.spleeter.api.beans.SpleeterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@FeignClient(value = "spleeter-serv", path = "/spleeter")
public interface SpleeterService {

    // 创建spleeter-serv干声分离任务
    @PostMapping("/request")
    SpleeterResponse requestSpleeter(Music music);

}
