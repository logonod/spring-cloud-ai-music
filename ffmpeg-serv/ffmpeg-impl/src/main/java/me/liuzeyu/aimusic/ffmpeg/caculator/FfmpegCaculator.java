package me.liuzeyu.aimusic.ffmpeg.caculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FfmpegCaculator {

    public static final String COMMAND = "{0} -loglevel error -i {1}/ai-vocals.wav -i {1}/accompaniment.wav -filter_complex amix=inputs=2:duration=longest {2}";

    public void run(String hash, String path, String ffmpegPath) throws IOException {

        String str;

        String outputPath = Paths.get(path, hash+".wav").toString();

        String inputPath = Paths.get(path, hash).toString();

        List<String> envp = new ArrayList<String>();
        for( Object o : System.getenv().entrySet()  ){
            envp.add(o.toString());
        }
        String[] envs = envp.stream().toArray(String[]::new);

        File dir = new File(path);

        Process p = Runtime.getRuntime().exec(MessageFormat.format(COMMAND, ffmpegPath, inputPath, outputPath), envs, dir);

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
