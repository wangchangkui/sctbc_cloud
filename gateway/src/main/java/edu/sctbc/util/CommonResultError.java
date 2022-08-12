package edu.sctbc.util;

import java.util.Arrays;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月09日 17:56:00
 */
public enum CommonResultError {

    /**
     * 没有任何服务
     */
    NO_CONNECTION_("Connection refused: no further information", "服务下线或者拒绝"),
    /**
     * 没有可用的服务器2
     */
    NO_SERVICE("503 SERVICE_UNAVAILABLE", "没有可用的服务"),
    /**
     * TIMEOUT_504 远程调用超时
     */
    TIMEOUT_504("504 GATEWAY_TIMEOUT", "服务请求超时");


    private String message;
    private String returnMessage;

    CommonResultError(String message, String returnMessage) {
        this.message = message;
        this.returnMessage = returnMessage;
    }

    public static String transFromMessage(String message) {
        return Arrays.stream(CommonResultError.values()).filter(s -> message.contains(s.message)).map(h -> h.returnMessage).findAny().orElse(message);
    }
}
