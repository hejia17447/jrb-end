package org.hejia.jrb.base.utils;


import io.jsonwebtoken.*;
import org.hejia.common.exception.BusinessException;
import org.hejia.common.result.ResponseEnum;
import org.springframework.util.ObjectUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    private static final long tokenExpiration = 24*60*60*1000;
    private static final String tokenSignKey = "A1t2g3uigu123456";

    private static Key getKeyInstance(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(tokenSignKey);
        return new SecretKeySpec(bytes,signatureAlgorithm.getJcaName());
    }

    public static String createToken(Long userId, String userName) {
        return Jwts.builder()
                .setSubject("SRB-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith( getKeyInstance(), SignatureAlgorithm.HS512)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * 判断token是否有效
     * @param token 待验证token
     * @return 验证结果
     */
    public static boolean checkToken(String token) {
        if(ObjectUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(getKeyInstance()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static Long getUserId(String token) {
        Claims claims = getClaims(token);
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    public static String getUserName(String token) {
        Claims claims = getClaims(token);
        return (String)claims.get("userName");
    }

    public static void removeToken(String token) {
        // jwtToken无需删除，客户端扔掉即可。
    }

    /**
     * 校验token并返回Claims
     * @param token 待校验的token
     * @return 返回Claims
     */
    private static Claims getClaims(String token) {
        if(ObjectUtils.isEmpty(token)) {
            // LOGIN_AUTH_ERROR(-211, "未登录"),
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getKeyInstance()).build().parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
    }
}

