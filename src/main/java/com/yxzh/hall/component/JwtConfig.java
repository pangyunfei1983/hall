package com.yxzh.hall.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtConfig {

    private String secret;
    private long expire;
    private String header;

    public String getToken (String identityId){

        LocalDateTime nowTime= LocalDateTime.now();
        Date nowDate = Date.from(nowTime.atZone(ZoneId.systemDefault()).toInstant());
        //过期时间
        Date expireDate = Date.from(nowTime.plusSeconds(expire).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(identityId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /*
     * 获取 Token 中注册信息
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
     * Token 是否过期验证
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }



}
