package com.morebooks.morebooks.utils;

import com.morebooks.morebooks.domain.dtos.Principal;
import jwt.library.JwtConfig;
import jwt.library.JwtDecoder;
import jwt.library.JwtGenerator;
import jwt.library.JwtMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtWrapper {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.salt}")
    private String jwtSalt;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    private JwtConfig jwtConfig;
    private JwtGenerator jwtGenerator;
    private JwtDecoder jwtDecoder;

    @PostConstruct
    public void startup() {
        jwtConfig = new JwtConfig();
        jwtConfig.setExpiration(24 * 60 * 60 * 1000);
        String secret = jwtSalt + jwtSecret;
        jwtConfig.buildKey(secret);

        jwtGenerator = new JwtGenerator(jwtConfig);
        jwtDecoder = new JwtDecoder(jwtConfig);
    }

    public String createJwt(Principal principal) {
        JwtMetadata metadata = new JwtMetadata();
        UUID uuid = UUID.randomUUID();
        metadata.setId(uuid.toString());
        metadata.setIssuer(jwtIssuer);
        metadata.setSubject(principal.getUsername());

        return this.jwtGenerator.createJwt(metadata);
    }

    public String getJwtUsername(String jwt) {
        Map<String, Object> body = this.jwtDecoder.getJwtBody(jwt);
        return (String) body.get("username");
    }

    public boolean verifyJwt(String jwt) throws Exception {
        Map<String, Object> header = this.jwtDecoder.getJwtHeader(jwt);
        Map<String, Object> body = this.jwtDecoder.getJwtBody(jwt);

        if(header == null || body == null) throw new Exception("Invalid token");

        String alg = (String) header.get("alg");
        if(alg == null || !alg.equals(this.jwtConfig.getSignatureAlgStr())) {
            throw new Exception("Invalid token");
        }

        String issuer = (String) body.get("iss");
        if(issuer == null || !issuer.equals(this.jwtIssuer)) {
            throw new Exception("Invalid Token Issuer");
        }

        long currentTimeMilis = System.currentTimeMillis();
        Long issuedAt = (Long) body.get("iat");
        if(issuedAt == null || issuedAt > currentTimeMilis) {
            throw new Exception("Invalid token");
        }

        Long exp = (Long) body.get("exp");
        if(exp == null || exp > currentTimeMilis) {
            throw new Exception("Invalid token");
        }

        return true;
    }

}
