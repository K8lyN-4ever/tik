package edu.td.zy.tik_common.utils.media;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/10 16:32
 */
public class Avatar extends MediaUtil {

//    public static void upload(MultipartFile file, String fileName, String path) throws IOException {
//
//        File targetFile = new File(path, fileName);
//        if(targetFile.exists()) {
//            if(!targetFile.delete()) {
//                throw new IOException();
//            }
//        }
//        file.transferTo(targetFile);
//    }
//
//    public static byte[] download(String fileName, String path) throws IOException {
//        File targetFile = new File(path, fileName);
//        FileInputStream inputStream = new FileInputStream(targetFile);
//        return IOUtils.toByteArray(inputStream);
//    }

}
