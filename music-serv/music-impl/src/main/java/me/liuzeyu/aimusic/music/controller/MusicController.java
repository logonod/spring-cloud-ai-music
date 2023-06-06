package me.liuzeyu.aimusic.music.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import me.liuzeyu.aimusic.music.api.beans.*;
import me.liuzeyu.aimusic.music.dao.entity.Music;
import me.liuzeyu.aimusic.music.service.intf.MusicService;

@Slf4j
@RestController
@RequestMapping("music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PostMapping("update")
    public MusicInfo update(@Valid @RequestBody UpdateMusicInfo request) {
        log.info("Create music: data={}", request);
        return musicService.updateMusic(request);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public MusicInfo create(@RequestPart("info") @Valid MusicInfo request, @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile upload) throws IOException, NoSuchAlgorithmException {
        log.info("Create music: data={}", request);
        return musicService.createMusic(request, upload);
    }

    @GetMapping("other")
    public MusicListInfo other(@RequestParam("id") Long id) {
        log.info("Query other music: user_id={}", id);
        return musicService.otherMusic(id);
    }

    @GetMapping("pending")
    public MusicListInfo pending(@RequestParam("id") Long id) {
        log.info("Query pending music: user_id={}", id);
        return musicService.pendingMusic(id);
    }

    @GetMapping("finished")
    public MusicListInfo finished(@RequestParam("id") Long id) {
        log.info("Query finished music: user_id={}", id);
        return musicService.finishedMusic(id);
    }

}
