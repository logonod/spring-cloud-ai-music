package me.liuzeyu.aimusic.music.converter;


import me.liuzeyu.aimusic.music.api.beans.MusicInfo;
import me.liuzeyu.aimusic.music.dao.entity.Music;

public class MusicConverter {
    public static MusicInfo convertToMusicInfo(Music music) {
        return MusicInfo.builder()
                .id(music.getId())
                .user(music.getUser())
                .title(music.getTitle())
                .artist(music.getArtist())
                .ai(music.getAi())
                .build();
    }
}
