package me.liuzeyu.aimusic.music.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicResponse {
    private Long id;

    @NotNull
    private Long user;

    @NotNull
    private String title;

    @NotNull
    private String artist;

    @NotNull
    private String ai;

    @NotNull
    private String status;

    @NotNull
    private String hash;

}