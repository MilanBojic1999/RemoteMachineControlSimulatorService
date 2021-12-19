package raf.web.Domaci3.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private IUserRepository repository;

    public JwtFilter(IUserRepository repository)
    {
        jwtUtil = new JwtUtil();
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Tokens.HEADER);
        String jwt = null;
        String email = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.replace(Tokens.PREFIX,"");
            email = jwtUtil.extractEmail(jwt);
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            Optional<User> optional = this.repository.findByEmail(email);

            if(optional.isPresent()){

            }
        }
    }
}
