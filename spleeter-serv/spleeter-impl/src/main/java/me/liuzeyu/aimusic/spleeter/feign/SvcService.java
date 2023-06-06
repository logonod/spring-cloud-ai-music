package me.liuzeyu.aimusic.spleeter.feign;

import me.liuzeyu.aimusic.spleeter.api.beans.Music;
import me.liuzeyu.aimusic.spleeter.api.beans.SpleeterResponse;
import me.liuzeyu.aimusic.spleeter.api.beans.SvcResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "svc-serv", path = "/svc")
public interface SvcService {

    // 创建spleeter-serv干声分离任务
    @PostMapping("/request")
    SvcResponse requestSvc(Music music);

}