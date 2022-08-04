package edu.sctbc.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.w3c.dom.Node;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月03日 17:47:00
 */
public class Encoder {

    public static String encoder(String content,String ras,String iv) {
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，可以自定义
                ras.getBytes(),
                // iv加盐，按照实际需求添加
                iv.getBytes());
        return  aes.encryptHex(content);

    }

    public static String decoder(String coder,String ras,String iv){
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，可以自定义
                ras.getBytes(),
                // iv加盐，按照实际需求添加
                iv.getBytes());
        return aes.decryptStr(coder, CharsetUtil.CHARSET_UTF_8);

    }

}
