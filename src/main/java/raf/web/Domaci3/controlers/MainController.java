package raf.web.Domaci3.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.form.LoginForm;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;

import java.util.Optional;

@RestController
@RequestMapping("")
public class MainController {

    private IUserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public MainController(IUserRepository userRepository,BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping(Paths.LOGIN_PATH)
    public ResponseEntity<String> login(@RequestBody LoginForm form){
        try{
            Optional<User> userOptional = userRepository.findByEmail(form.getEmail());
            if(!userOptional.isPresent())
                throw new Exception("There is no user with given email");

            User user = userOptional.get();


            return ResponseEntity.ok(user.toString());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
