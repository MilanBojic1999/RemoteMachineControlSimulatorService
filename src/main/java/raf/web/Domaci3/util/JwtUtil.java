package raf.web.Domaci3.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import raf.web.Domaci3.model.PermissionsEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.security.Tokens;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    public String generateToken(String email, List<PermissionsEnum> permissions){
        return Tokens.PREFIX+JWT.create().withExpiresAt(new Date(System.currentTimeMillis()+(2*60*60*1000))).withClaim("email",email).withClaim("permissions",permissions.stream().map(Enum::toString).collect(Collectors.toList())).sign(Algorithm.HMAC512(Tokens.SECRET.getBytes()));
    }

    public String extractEmail(String token){
        token = token.replace(Tokens.PREFIX,"");
        return JWT.decode(token).getClaim("email").asString();
    }

    public boolean validateToken(String token, UserDetails user){
        return (user.getUsername().equals(extractEmail(token)) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return JWT.decode(token).getExpiresAt().before(new Date());
    }
}
