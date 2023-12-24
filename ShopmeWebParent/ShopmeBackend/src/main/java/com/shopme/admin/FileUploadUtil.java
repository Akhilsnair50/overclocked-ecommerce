package com.shopme.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);

    public static void saveFile(String uploadDir , String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream , filePath , StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex){
            throw new IOException("Couldn't save the file : "+ fileName,ex);
        }
    }

    public static void cleanDir(String dir){
        Path dirPath = Paths.get(dir);

        try{
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)){
                    try {
                        Files.delete(file);
                    }catch (IOException ex){
                        LOGGER.error("Couldnt delete file : "+file);
//                        System.out.println("Couldnt delete file : "+file);
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.error("Couldnt  list directory: " + dirPath);
//            System.out.println("Couldnt list directory: " + dirPath);
        }
    }
    public static void removeDir(String dir){
        cleanDir(dir);
        try {
            Files.delete(Paths.get(dir));
        } catch (IOException e) {
            LOGGER.error("Coulnd delete or remove directory: "+ dir);
        }
    }
}
