package edu.sctbc.pojo.reqentity;

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
public class QrCode {
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

}
