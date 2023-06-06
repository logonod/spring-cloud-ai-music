package me.liuzeyu.me.aimusic.svc.caculator;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractSvc implements SvcTemplate {

    @Override
    public void run(String hash, String path, String pythonPath, String svcPath) throws IOException {
        String str;

        String inputPath = Paths.get(path, hash, "vocals.wav").toString();

        List<String> envp = new ArrayList<String>();
        for( Object o : System.getenv().entrySet()  ){
            envp.add(o.toString());
        }
        String[] envs = envp.stream().toArray(String[]::new);

        File dir = new File(svcPath);

        Process p = Runtime.getRuntime().exec(MessageFormat.format(getCommand(), pythonPath, svcPath, inputPath), envs, dir);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

        boolean isError = false;

        // read the output from the command
        while ((str = stdInput.readLine()) != null) {
            log.info(str);
            if (str.contains("ERROR")) {
                isError = true;
            }
        }

        // read any errors from the attempted command
        while ((str = stdError.readLine()) != null) {
            log.error(str);
            isError = true;
        }
        if (isError) {
            throw new RuntimeException("svc error");
        }

    }
}
