package raf.web.Domaci3.controlers;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.security.JwtUtil;
import raf.web.Domaci3.security.Tokens;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;

import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping(Paths.MACHINE_PATH)
public class MachineController {

    private MachineService machineService;
    private UserService userService;
    private JwtUtil jwtUtil;
    private Gson gson;

    private Random random;

    @PutMapping(path = Paths.START_MACHINE)
    public ResponseEntity<String> startMachine(@RequestParam("id") long id){
        try{
            Optional<Machine> machineOptional = machineService.findById(id);
            if(!machineOptional.isPresent())
                throw new Exception("Couldn't find machine: "+id);

            Machine machine = machineOptional.get();
            if(machine.getStatus()== StatusEnum.RUNNING)
                throw new Exception("Machine is RUNNING, but shouldn't");
            Runnable runnable = () -> {
                int sleep_time = 10 + random.nextInt(10);
                try {
                    Thread.sleep(sleep_time*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                machine.setStatus(StatusEnum.RUNNING);
            };

            Thread thread = new Thread(runnable);
            thread.start();
            return new ResponseEntity<>("Machine ("+id+") should start",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<Boolean> createMachine(@RequestParam("id") long id,@RequestHeader(Tokens.HEADER) String jwt){
        try{
            String email = jwtUtil.extractEmail(jwt);
            User user = userService.getUserByEmail(email);

            Machine machine = new Machine(user);
            machineService.save(machine);

            return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = Paths.DESTROY_MACHINE)
    public ResponseEntity<?> destroyMachine(@RequestParam("id") long id){
        Optional<Machine> machineOptional = machineService.findById(id);
        if(!machineOptional.isPresent())
            return new ResponseEntity<String>("Couldn't find machine: "+id,HttpStatus.BAD_REQUEST);

        Machine machine = machineOptional.get();

        if(machine.getStatus()==StatusEnum.RUNNING)
            return new ResponseEntity<String>("Machine is RUNNING. can't delete machine",HttpStatus.BAD_REQUEST);

        machine.setActive(false);
        machineService.save(machine);

        return ResponseEntity.ok().body("Deleted machine: "+id);
    }
}
