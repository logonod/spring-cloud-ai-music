package me.liuzeyu.aimusic.ffmpeg.feign;

import me.liuzeyu.aimusic.music.api.beans.MusicInfo;
import me.liuzeyu.aimusic.music.api.beans.UpdateMusicInfo;
import me.liuzeyu.aimusic.spleeter.api.beans.SvcResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "music-serv", path = "/music")
public interface MusicService {
    // 修改任务状态
    @PostMapping("/update")
    MusicInfo updateMusic(UpdateMusicInfo music);
}
