package me.liuzeyu.me.aimusic.svc.caculator;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.svc.api.beans.Music;
import me.liuzeyu.me.aimusic.svc.caculator.impl.SunyanziSvc;
import me.liuzeyu.me.aimusic.svc.caculator.impl.ZhangxueyouSvc;
import me.liuzeyu.me.aimusic.svc.caculator.impl.ZhoujielunSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CalculationFactory {

    @Autowired
    private SunyanziSvc sunyanziSvc;

    @Autowired
    private ZhangxueyouSvc zhangxueyouSvc;

    @Autowired
    private ZhoujielunSvc zhoujielunSvc;

    public SvcTemplate getSvc(Music music) {
        String ai = music.getAi();

        switch (ai) {
            // 孙燕姿
            case "孙燕姿":
                return sunyanziSvc;
            // 张学友
            case "张学友":
                return zhangxueyouSvc;
            // 周杰伦
            case "周杰伦":
                return zhoujielunSvc;
            // default
            default:
                return sunyanziSvc;
        }
    }
}
