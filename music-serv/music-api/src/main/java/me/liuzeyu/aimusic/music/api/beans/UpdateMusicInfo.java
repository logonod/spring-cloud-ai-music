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
public class UpdateMusicInfo {
    @NotNull
    private Long user;

    @NotNull
    private String hash;

    @NotNull
    private String status;
}
