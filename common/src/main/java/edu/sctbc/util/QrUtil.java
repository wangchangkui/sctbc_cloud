package edu.sctbc.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.druid.util.StringUtils;
import edu.sctbc.util.redis.RedisCommonKey;
import edu.sctbc.util.redis.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static edu.sctbc.util.redis.RedisCommonKey.THREE_MINUTES;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月08日 10:16:00
 */

@Component
public class QrUtil {
    @Autowired
    private RedisPool redisPool;


    public String createQr(String content){
        RedisCommonKey.setValues(RedisCommonKey.QR+content,"1", THREE_MINUTES,false,redisPool.getConnection());
        BufferedImage generate = QrCodeUtil.generate(content, 300, 300);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ImageIO.write(generate,"png",byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String encode = Base64.encode(bytes);
            byteArrayOutputStream.flush();
            return "data:image/png;base64,"+encode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getQrData(String qrToken){
        try(Jedis jedis =redisPool.getConnection()){
            String s = jedis.get(RedisCommonKey.QR + qrToken);
            if(StringUtils.isEmpty(s)){
                throw new RuntimeException("该二维码已经失效");
            }
            return s;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
