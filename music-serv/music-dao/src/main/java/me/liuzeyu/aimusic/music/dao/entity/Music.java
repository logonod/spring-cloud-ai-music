package me.liuzeyu.aimusic.music.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.liuzeyu.aimusic.music.dao.converter.StatusTypeConverter;
import me.liuzeyu.aimusic.music.api.enums.StatusType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "music")
public class Music implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user", nullable = false)
    private Long user;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "ai", nullable = false)
    private String ai;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

}
