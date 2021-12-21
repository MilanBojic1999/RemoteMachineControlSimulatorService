package raf.web.Domaci3.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorization extends BasicAuthenticationFilter {

    private UserService service;
    private JwtUtil jwtUtil;

    @Autowired
    public JWTAuthorization(AuthenticationManager authenticationManager, UserService service) {
        super(authenticationManager);
        this.service = service;
        this.jwtUtil = new JwtUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader(Tokens.HEADER);
        System.out.println("+++"+request.getRequestURI()+"-----"+request.getRemoteUser()); // iz getRequestURI izvalcimo tačan zahtev

        String jwt = null;
        String email = null;

        if(authHeader != null && authHeader.startsWith(Tokens.PREFIX)) {
            jwt = authHeader.replace(Tokens.PREFIX,"");
            email = jwtUtil.extractEmail(jwt);
        }

        UserDetails userDetails = service.loadUserByUsername(email);

        chain.doFilter(request, response);
    }
}
