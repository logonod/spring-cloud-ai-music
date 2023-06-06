package me.liuzeyu.aimusic.music.converter;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.nio.file.Paths;

public class FileConverter {

    public static String convertToUUID() {
        return UUID.randomUUID().toString();
    }

    public static String convertToHash(MultipartFile upload, File uuidFile, String path, Long user, String ai) throws IOException, NoSuchAlgorithmException {
        byte[] uploadBytes = upload.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(uploadBytes);
        String hashString = new BigInteger(1, digest).toString(16);
        String finalString = hashString+user.toString()+ai;
        byte[] bytesOfMessage = finalString.getBytes("UTF-8");
        byte[] theMD5digest = md5.digest(bytesOfMessage);
        String finalHashString = new BigInteger(1, theMD5digest).toString(16);
        uuidFile.renameTo(new File(Paths.get(path, finalHashString+".mp3").toString()));
        return finalHashString;
    }

    public static File convertFromMultipartFile(MultipartFile upload, String path) throws IOException {
        Path filePath = Paths.get(path, convertToUUID());
        Files.copy(upload.getInputStream(), filePath);
        return new File(filePath.toString());
    }

    public static File convertToMP3(File file) throws IOException {
        Tika tika = new Tika();
        String type = tika.detect(file);
        if(!type.equals("audio/mpeg")) {
            throw new RuntimeException("Upload file is not mp3");
        }
        return file;
    }

}
