package raf.web.Domaci3.controlers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.form.LoginForm;
import raf.web.Domaci3.form.PermissionsResponse;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IUserRepository;
import raf.web.Domaci3.response_request.UserDto;
import raf.web.Domaci3.security.JwtUtil;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("")
public class MainController {

    private IUserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private JwtUtil jwtUtil;
    private Gson gson;

    @Autowired
    public MainController(IUserRepository userRepository,BCryptPasswordEncoder encoder,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.gson = new Gson();
    }

    @PostMapping(Paths.LOGIN_PATH)
    public ResponseEntity<String> login(@RequestBody LoginForm form){
        try{
            Optional<User> userOptional = userRepository.findByEmail(form.getEmail());
            if(!userOptional.isPresent())
                throw new Exception("There is no user with given email");

            User user = userOptional.get();
            System.out.println("Logged user "+user.getUserId());
            String jwt = jwtUtil.generateToken(user.getEmail());
            Map<String,String> map = new HashMap<>();
            map.put("jwt",jwt);
            String jsn = gson.toJson(map);

            return new ResponseEntity<>(jsn,HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/permits")
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

    @GetMapping(Paths.INFO_USERS_PATH+"/{id}")
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

    @GetMapping(Paths.SHOW_USERS_PATH+"/all")
    public List<UserDto> getAllUsers(){
        List<User> users =  userRepository.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    public UserDto userToDto(User user){
        return new UserDto(user.getUserId(),user.getFirstname(), user.getLastname(), user.getEmail(), user.getPermissionsList());
    }

    @PostMapping(Paths.ADD_USERS_PATH)
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping(Paths.EDIT_USERS_PATH)
    public User updateUser(@RequestParam User user){
        return userRepository.save(user);
    }

    @PostMapping(Paths.EDIT_USERS_PATH+"/delete")
    public void deleteUser(@RequestParam User user){
        userRepository.delete(user);
    }

}
