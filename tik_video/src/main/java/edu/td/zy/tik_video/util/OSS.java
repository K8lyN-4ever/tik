package edu.td.zy.tik_video.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/4 9:35
 */
@Component
public class OSS {

    public static String getToken(String endpoint, String accessKeyId, String accessKeySecret, String roleArn,
                                  String roleSessionName, String policy, String durationSeconds) throws ClientException {
        Long durationSecond = Long.parseLong(durationSeconds);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setDurationSeconds(durationSecond);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            return new Gson().toJson(response);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSign(String accessKeyId, String accessKeySecret, String endpoint, String bucketName) {
        String host = "http://" + bucketName + "." + endpoint;
//        String dir = new DateTime().toString("yyyy/MM/dd"); // 用户上传文件时指定的前缀。
        Map<String, Object> respMap = null;

        // 创建OSSClient实例。
        com.aliyun.oss.OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "");

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            respMap = new LinkedHashMap<>();
            //这些参数名必须要这样写，与官方文档一一对应
            respMap.put("OSSAccessKeyId", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("Signature", postSignature);
//            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            //让服务端返回200,不然，默认会返回204
            respMap.put("success_action_status", 200);
        }
        catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        finally {
            ossClient.shutdown();
        }
        return new Gson().toJson(respMap);
    }
}
