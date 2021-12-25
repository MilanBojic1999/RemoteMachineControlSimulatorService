package raf.web.Domaci3.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.PermissionsEnum;
import raf.web.Domaci3.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JWTAuthorization extends OncePerRequestFilter {

    private UserService service;
    private JwtUtil jwtUtil;

    @Autowired
    public JWTAuthorization( UserService service) {
        this.service = service;
        this.jwtUtil = new JwtUtil();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader(Tokens.HEADER);
        System.out.println("+++"+request.getRequestURI()+"-----"+request.getRemoteUser()); // iz getRequestURI izvalcimo tačan zahtev

        String jwt;
        String email = null;

        if(authHeader != null && authHeader.startsWith(Tokens.PREFIX)) {
            jwt = authHeader.replace(Tokens.PREFIX,"");
            email = jwtUtil.extractEmail(jwt);
        }

        UserDetails userDetails = service.loadUserByUsername(email);

        Collection<PermissionsEnum> permissions = (Collection<PermissionsEnum>) userDetails.getAuthorities();

        String uri = request.getRequestURI();
        System.out.println(uri);

        UsernamePasswordAuthenticationToken auth = null;
        boolean flagAuth;
        if(uri.startsWith(Paths.SHOW_USERS_PATH)){
            flagAuth = permissions.contains(PermissionsEnum.CAN_READ_USERS);
        }else if(uri.startsWith(Paths.EDIT_USERS_PATH)){
            if(uri.endsWith("/delete"))
                flagAuth = permissions.contains(PermissionsEnum.CAN_DELETE_USERS);
            else
                flagAuth = permissions.contains(PermissionsEnum.CAN_UPDATE_USERS);
        }else if(uri.startsWith(Paths.ADD_USERS_PATH)){
            flagAuth = permissions.contains(PermissionsEnum.CAN_CREATE_USERS);
        }else{
            System.err.println("IDK what is this");
            flagAuth = true;
        }

        if(flagAuth)
            auth = new UsernamePasswordAuthenticationToken(email,null,userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}
