package me.liuzeyu.me.aimusic.svc.caculator;

import java.io.IOException;

public interface SvcTemplate {
    String getCommand();

    void run(String hash, String path, String pythonPath, String svcPath) throws IOException;
}
