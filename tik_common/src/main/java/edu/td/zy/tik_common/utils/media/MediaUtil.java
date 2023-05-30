package edu.td.zy.tik_common.utils.media;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/2 11:15
 */
public class MediaUtil {

    /**
     * 上传文件
     * @param file 要上传的文件
     * @param fileName 要上传的文件名字
     * @param path 上传路径
     * @return 上传成功后的文件名
     * @throws IOException 可能抛出的异常
     * */
    public static String upload(MultipartFile file, String fileName, String path) throws IOException {
        File targetFile = new File(path, fileName);
        if(targetFile.exists()) {
            if(!targetFile.delete()) {
                throw new IOException();
            }
        }
        file.transferTo(targetFile);
        return fileName;
    }

    /**
     * 下载文件
     * @param fileName 要下载的文件名
     * @param path 下载文件所在路径
     * @return 要下载的文件的字节数组
     * @throws IOException 可能抛出的异常
     * */
    public static byte[] download(String fileName, String path) throws IOException {
        File targetFile = new File(path, fileName);
        FileInputStream inputStream = new FileInputStream(targetFile);
        return IOUtils.toByteArray(inputStream);
    }
}
