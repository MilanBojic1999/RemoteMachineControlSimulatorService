package raf.web.Domaci3.controlers;

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

import java.util.List;
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
    public ResponseEntity<PermissionsResponse> getPermissions(@RequestParam("id") long id){
        try{
            Optional<User> userOptional = userRepository.findByUserId(id);
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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        try{
            Optional<User> userOptional = userRepository.findByUserId(id);
            if(!userOptional.isPresent())
                throw new Exception("There is no user with given email");

            User user = userOptional.get();

            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping("/change")
    public User updateUser(@RequestParam User user){
        return userRepository.save(user);
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestParam User user){
        userRepository.delete(user);
    }

}
