package me.liuzeyu.me.aimusic.svc.caculator.impl;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.me.aimusic.svc.caculator.AbstractSvc;
import me.liuzeyu.me.aimusic.svc.caculator.SvcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ZhoujielunSvc extends AbstractSvc implements SvcTemplate {
    static final String COMMAND = "{0} {1}/inference_main.py -m {1}/logs/44k/zhoujielun.pth -c {1}/configs/zhoujielun.json -n {2} -s zhoujielun -cm {1}/logs/44k/zhoujielun.pt";

    @Override
    public String getCommand() {
        return COMMAND;
    }
}
