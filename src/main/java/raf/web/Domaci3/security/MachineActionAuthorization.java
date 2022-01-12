package raf.web.Domaci3.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.response_request.MachineAction;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;
import raf.web.Domaci3.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MachineActionAuthorization extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private MachineService machineService;
    private UserService userService;

    private Gson gson;

    @Autowired
    public MachineActionAuthorization(JwtUtil jwtUtil, MachineService machineService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.machineService = machineService;
        this.userService = userService;
        this.gson = new Gson();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        System.out.println(uri);
        if(uri.startsWith(Paths.MACHINE_PATH) && !(uri.replace(Paths.MACHINE_PATH,"").startsWith(Paths.CREATE_MACHINE) || uri.replace(Paths.MACHINE_PATH,"").startsWith(Paths.SEARCH_MACHINE) ||
                uri.replace(Paths.MACHINE_PATH,"").startsWith(Paths.MACHINE_ERRORS) )) {

            long id = Long.parseLong(request.getHeader("id"));

            String authHeader = request.getHeader(Tokens.HEADER);

            String jwt;
            String email = null;

            if (authHeader != null && authHeader.startsWith(Tokens.PREFIX)) {
                jwt = authHeader.replace(Tokens.PREFIX, "");
                email = jwtUtil.extractEmail(jwt);
            }

            Optional<Machine> machine = machineService.findById(id);
            User user = userService.getUserByEmail(email);

            UsernamePasswordAuthenticationToken auth;
            boolean flagAuth = machine.isPresent() && machine.get().isActive() && machine.get().getCreatedBy().equals(user);


            if(flagAuth)
                auth = new UsernamePasswordAuthenticationToken(email, null, user.getPermissionsList());
            else {
                auth = null;
                System.err.println(machine + "\n\n" + user);
            }

            SecurityContextHolder.getContext().setAuthentication(auth);

        }else{
            System.out.println("LOLOLO "+uri);
        }

        chain.doFilter(request, response);


    }
}
