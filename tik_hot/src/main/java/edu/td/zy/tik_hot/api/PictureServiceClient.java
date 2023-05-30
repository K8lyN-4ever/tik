package edu.td.zy.tik_hot.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author K8lyN
 * @date 2023年4月10日 12:25:13
 * @version 1.0
 */
@FeignClient(value = "tik-picture", path = "/api/v1/picture")
public interface PictureServiceClient {
}
