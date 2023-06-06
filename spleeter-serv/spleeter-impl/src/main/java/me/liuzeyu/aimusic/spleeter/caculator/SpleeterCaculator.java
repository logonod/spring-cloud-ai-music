package me.liuzeyu.aimusic.spleeter.caculator;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SpleeterCaculator {

    public void run(String hash, String path, String command) throws IOException {

        String str;

        String outputPath = Paths.get(path).toString();

        String inputPath = Paths.get(path, hash+".mp3").toString();

        List<String> envp = new ArrayList<String>();
        for( Object o : System.getenv().entrySet()  ){
            envp.add(o.toString());
        }
        String[] envs = envp.stream().toArray(String[]::new);

        File dir = new File(path);

        Process p = Runtime.getRuntime().exec(String.format(command, outputPath, inputPath), envs, dir);

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
            throw new RuntimeException("spleeter error");
        }

    }
}
