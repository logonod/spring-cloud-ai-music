package me.liuzeyu.aimusic.ffmpeg.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Music implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long user;

    @NotNull
    private String hash;

    @NotNull
    private String title;

    @NotNull
    private String artist;

    @NotNull
    private String ai;

    @NotNull
    private String status;

    @NotNull
    private Date createdTime;

    @NotNull
    private Date updatedTime;

}
