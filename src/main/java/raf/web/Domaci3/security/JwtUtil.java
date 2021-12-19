package raf.web.Domaci3.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    public String generateToken(String email){
        return Tokens.PREFIX+JWT.create().withExpiresAt(new Date(System.currentTimeMillis()+(2*60*60*1000))).withClaim("email",email).sign(Algorithm.HMAC512(Tokens.SECRET.getBytes()));
    }

    public String extractEmail(String token){
        token = token.replace(Tokens.PREFIX,"");
        return JWT.decode(token).getClaim("email").asString();
    }

    public boolean isTokenExpired(String token){
        return JWT.decode(token).getExpiresAt().before(new Date());
    }
}
