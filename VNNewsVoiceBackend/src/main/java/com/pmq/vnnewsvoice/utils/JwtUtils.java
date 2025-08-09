package com.pmq.vnnewsvoice.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pmq.vnnewsvoice.pojo.CustomUserDetails;

import java.text.ParseException;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Value("${JWT_EXPIRATION}")
    private int jwtExpiration;

    public String generatJwtToken(Authentication authentication){
        try{
            CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userPrincipal.getUsername())
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + jwtExpiration))
                    .build();

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);

            JWSSigner signer = new MACSigner(jwtSecret.getBytes());
            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (JOSEException e) {
            System.err.println("Error generating JWT token: " + e.getMessage());
            return null;
        }
    }

    public  String getUserNameFromJwtToken(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getSubject();
        }
        catch (ParseException e){
            System.err.println("Error parsing JWT token: " + e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken(String authToken){
        try{
            SignedJWT signedJWT = SignedJWT.parse(authToken);
            JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());
            if(!signedJWT.verify(verifier)){
                return false;
            }

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            Date expirationTime = claimsSet.getExpirationTime();
            if(expirationTime != null && expirationTime.before(new Date())){
                return false;
            }
            return true;
        }
        catch (ParseException | JOSEException e){
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

}
