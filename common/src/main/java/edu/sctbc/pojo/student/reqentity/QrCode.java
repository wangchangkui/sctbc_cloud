package edu.sctbc.pojo.student.reqentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月08日 10:27:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCode<T> {
    /**
     * 二维码图片
     */
    private String base64Image;

    /**
     * 临时token
     */
    private String tempToken;

    /**
     * 图片过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 修改token的值
     */
    private T value;

    public QrCode(String base64Image, String tempToken, LocalDateTime expireTime) {
        this.base64Image = base64Image;
        this.tempToken = tempToken;
        this.expireTime = expireTime;
    }
}
