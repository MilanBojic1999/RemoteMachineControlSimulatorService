package raf.web.Domaci3.controlers;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.repositories.IMachineRepository;
import raf.web.Domaci3.security.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping(Paths.MACHINE_PATH)
public class MachineController {

    private IMachineRepository repository;
    private JwtUtil jwtUtil;
    private Gson gson;

    @PutMapping(path = Paths.START_MACHINE)
    public ResponseEntity<String> startMachine(@RequestParam("id") long id){
        return null;
    }

    @PutMapping(path = Paths.STOP_MACHINE)
    public ResponseEntity<String> stopMachine(@RequestParam("id") long id){
        return null;
    }

    @PutMapping(path = Paths.RESTART_MACHINE)
    public ResponseEntity<String> restartMachine(@RequestParam("id") long id){
        return null;
    }

    @PostMapping(path = Paths.CREATE_MACHINE)
    public ResponseEntity<Boolean> createMachine(@RequestParam("id") long id){
        return null;
    }

    @PostMapping(path = Paths.DESTROY_MACHINE)
    public ResponseEntity<Boolean> destroyMachine(@RequestParam("id") long id){
        return null;
    }
}
