package me.liuzeyu.aimusic.music.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StatusType {
    PENDING("已提交", "0"),
    SPLITTED("干声伴唱分离已完成", "1"),
    CONVERTTED("干声翻唱已完成", "2"),
    FINISHED("完成", "3"),
    FAILED("失败", "-1");

    private String description;

    // 存在数据库里的最终code
    private String code;

    public static StatusType convert(String code) {
        return Stream.of(values())
                .filter(statusType -> statusType.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(PENDING);
    }
}
