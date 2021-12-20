package raf.web.Domaci3.controlers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.form.LoginForm;
import raf.web.Domaci3.form.PermissionsResponse;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.security.JwtUtil;
import raf.web.Domaci3.security.Tokens;

import java.util.Optional;

@RestController
@RequestMapping("")
public class MainController {

    private IUserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private JwtUtil jwtUtil;

    @Autowired
    public MainController(IUserRepository userRepository,BCryptPasswordEncoder encoder,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(Paths.LOGIN_PATH)
    public ResponseEntity<String> login(@RequestBody LoginForm form){
        try{
            Optional<User> userOptional = userRepository.findByEmail(form.getEmail());
            if(!userOptional.isPresent())
                throw new Exception("There is no user with given email");

            User user = userOptional.get();


            return new ResponseEntity<>(jwtUtil.generateToken(form.getEmail()),HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/permms")
    public ResponseEntity<PermissionsResponse> getPermissions(@RequestParam String email){
        try{
            Optional<User> userOptional = userRepository.findByEmail(email);
            if(!userOptional.isPresent())
                throw new Exception("There is no user with given email");

            User user = userOptional.get();

            PermissionsResponse pr = new PermissionsResponse(user.getPermissionsList());

            return new ResponseEntity<>(pr,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
