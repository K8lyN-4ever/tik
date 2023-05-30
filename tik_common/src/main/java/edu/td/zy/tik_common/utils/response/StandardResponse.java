package edu.td.zy.tik_common.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/25 10:07
 */
@Data
@AllArgsConstructor
public class StandardResponse<T> {

    @Data
    @AllArgsConstructor
    static class SimpleResponse {
        private ResponseStatus status;
        private String message;

        @Override
        public String toString() {
            return "{\"status\":" + status.getCode() + ",\"message\":\"" + message + "\"" + "}";
        }
    }

    /**
     * param status 状态码 200成功 400参数错误 500服务器错误
     * */
    private ResponseStatus status;
    private String message;
    private T data;

    @Override
    public String toString() {
        if(data == null) {
            return "{\"status\":" + status.getCode() + ",\"message\":\"" + message + "\"}";
        }
        return "{\"status\":" + status.getCode() + ",\"message\":\"" + message + "\",\"data\":\"" + data.toString() + "\"}";
    }

    public String toString(boolean isJson) {
        return "{\"status\":" + status.getCode() + ",\"message\":\"" + message + "\",\"data\":" + data.toString() + "}";
    }

    public static String getSimpleSuccess(String mes) {
        return new SimpleResponse(ResponseStatus.OK, mes).toString();
    }

    public static String getSimpleSuccess(ResponseStatus status, String mes) {
        return new SimpleResponse(status, mes).toString();
    }

    public static String getSimpleFail(String mes) {
        return new SimpleResponse(ResponseStatus.BAD_REQUEST, mes).toString();
    }

    public static String getSimpleFail(ResponseStatus status, String mes) {
        return new SimpleResponse(status, mes).toString();
    }

    public boolean ok() {
        return status == ResponseStatus.OK;
    }

}