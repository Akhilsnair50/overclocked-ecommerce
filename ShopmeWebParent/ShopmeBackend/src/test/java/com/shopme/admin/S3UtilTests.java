package com.shopme.admin;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class S3UtilTests {

//    @Test
//    public void testListFolder(){
//        String folderName = "user-photos/15";
//        AmazonS3Util.listFolder(folderName);
//    }

    @Test
    public void testListFolder() {
        String folderName = "test-upload";
        List<String> listKeys = AmazonS3Util.listFolder(folderName);
        listKeys.forEach(System.out::println);
    }

    @Test
    public void testUploadFile() throws FileNotFoundException {
        String folderName = "test-upload/one/two";
        String fileName = "Screenshot 2023-10-25 202634.png";
        String filePath = "C:\\test\\" + fileName;

        InputStream inputStream = new FileInputStream(filePath);

        AmazonS3Util.uploadFile(folderName, fileName, inputStream);
    }

    @Test
    public void testDeleteFile() {
        String fileName = "test-upload/one/two/Screenshot 2023-10-25 202634.png";
        AmazonS3Util.deleteFile(fileName);
    }

    @Test
    public void testRemoveFolder() {
        String folderName = "test-upload";
        AmazonS3Util.removeFolder(folderName);
    }
}
