package raf.web.Domaci3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.PermissionsEnum;
import raf.web.Domaci3.services.UserService;
import raf.web.Domaci3.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

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
        System.out.println("+++"+request.getRequestURL()+"-----"+request.getRemoteUser()); // iz getRequestURI izvalcimo tačan zahtev

        String jwt;
        String email = null;

        if(authHeader != null && authHeader.startsWith(Tokens.PREFIX)) {
            jwt = authHeader.replace(Tokens.PREFIX,"");
            email = jwtUtil.extractEmail(jwt);
        }

        if(email!=null) {
            UserDetails userDetails = service.loadUserByUsername(email);

            Collection<PermissionsEnum> permissions = (Collection<PermissionsEnum>) userDetails.getAuthorities();

            String uri = request.getRequestURI();
            System.out.println(uri);

            UsernamePasswordAuthenticationToken auth = null;
            boolean flagAuth = false;
            if(uri.startsWith(Paths.USER_PATH)){
                uri = uri.replace(Paths.USER_PATH,"");
                if (uri.startsWith(Paths.SHOW_USERS_PATH)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_READ_USERS);
                } else if (uri.startsWith(Paths.EDIT_USERS_PATH)) {
                    if (uri.endsWith("/delete"))
                        flagAuth = permissions.contains(PermissionsEnum.CAN_DELETE_USERS);
                    else
                        flagAuth = permissions.contains(PermissionsEnum.CAN_UPDATE_USERS);
                } else if (uri.startsWith(Paths.ADD_USERS_PATH)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_CREATE_USERS);
                } else {
                    System.err.println("IDK what is this "+Paths.USER_PATH+uri);
                    flagAuth = true;
                }
            }else if(uri.startsWith(Paths.MACHINE_PATH)){
                uri = uri.replace(Paths.MACHINE_PATH,"");
                if (uri.startsWith(Paths.SEARCH_MACHINE)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_SEARCH_MACHINE);
                } else if (uri.startsWith(Paths.START_MACHINE)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_START_MACHINE);
                } else if (uri.startsWith(Paths.STOP_MACHINE)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_STOP_MACHINE);
                } else if (uri.startsWith(Paths.RESTART_MACHINE)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_RESTART_MACHINE);
                } else if (uri.startsWith(Paths.CREATE_MACHINE)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_CREATE_MACHINE);
                } else if (uri.startsWith(Paths.DESTROY_MACHINE)) {
                    flagAuth = permissions.contains(PermissionsEnum.CAN_DESTROY_MACHINE);
                } else {
                    System.err.println("IDK what is this "+Paths.MACHINE_PATH+uri);
                    flagAuth = true;
                }
            }

            if (flagAuth)
                auth = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

}
