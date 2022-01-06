package raf.web.Domaci3.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.web.Domaci3.services.UserService;
import raf.web.Domaci3.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private UserService userService;

    public JwtFilter(UserService userService)
    {
        jwtUtil = new JwtUtil();
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getRequestURI());
        String authHeader = request.getHeader(Tokens.HEADER);
        String jwt = null;
        String email = null;
        System.out.println(authHeader);
        if(authHeader != null && authHeader.startsWith(Tokens.PREFIX)) {
            jwt = authHeader.replace(Tokens.PREFIX,"");
            email = jwtUtil.extractEmail(jwt);
            System.out.println(email);
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userService.loadUserByUsername(email);
            System.out.println(userDetails);
            if(jwtUtil.validateToken(jwt,userDetails)){
                System.out.println(jwt);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
