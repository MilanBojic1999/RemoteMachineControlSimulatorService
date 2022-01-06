package raf.web.Domaci3.controlers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.MachineAsyncService;
import raf.web.Domaci3.util.JwtUtil;
import raf.web.Domaci3.security.Tokens;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;
import raf.web.Domaci3.util.MachineRestartRunnable;
import raf.web.Domaci3.util.MachineRunnable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping(Paths.MACHINE_PATH)
@EnableAsync
public class MachineController {

    private MachineService machineService;
    private UserService userService;
    private MachineAsyncService asyncService;
    private JwtUtil jwtUtil;
    private Gson gson;

    private Random random;

    @Autowired
    public MachineController(MachineAsyncService asyncService, MachineService machineService, UserService userService) {
        this.machineService = machineService;
        this.userService = userService;
        this.asyncService = asyncService;
        this.jwtUtil = new JwtUtil();
        this.gson = new Gson();

        this.random = new Random();
    }

    private static final Specification<Machine> isActive = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"),true));


    @GetMapping(Paths.SEARCH_MACHINE)
    public ResponseEntity<List<Machine>> searchMachines(){
        Specification<Machine> goodId = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),1));
        List<Machine> machines = machineService.getRepository().findAll(goodId.and(isActive));
        return null;
    }

    @GetMapping("/all")
    public List<Machine> getAllMachines(){
        Specification<Machine> goodId = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),1));
        return machineService.getRepository().findAll(goodId.and(isActive));
    }

    @PutMapping(path = Paths.START_MACHINE)
    public ResponseEntity<String> startMachine(@RequestParam("id") long id){
        try{
            Optional<Machine> machineOptional = machineService.findById(id);
            if(!machineOptional.isPresent())
                throw new Exception("Couldn't find machine: "+id);

            Machine machine = machineOptional.get();
            if(machine.getStatus()== StatusEnum.RUNNING)
                throw new Exception("Machine is RUNNING, but shouldn't");

            int time = 10 + random.nextInt(10);

            asyncService.startMachine(machine,time);

            return new ResponseEntity<>("Machine ("+id+") should start",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = Paths.STOP_MACHINE)
    public ResponseEntity<String> stopMachine(@RequestParam("id") long id){
        try{
            Optional<Machine> machineOptional = machineService.findById(id);
            if(!machineOptional.isPresent())
                throw new Exception("Couldn't find machine: "+id);

            Machine machine = machineOptional.get();
            if(machine.getStatus()== StatusEnum.STOPPED)
                throw new Exception("Machine is STOPPED, but shouldn't");

            int time = 10 + random.nextInt(10);

            asyncService.stopMachine(machine,time);

            return new ResponseEntity<>("Machine ("+id+") should stop",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = Paths.RESTART_MACHINE)
    public ResponseEntity<String> restartMachine(@RequestParam("id") long id){
        try{
            Optional<Machine> machineOptional = machineService.findById(id);
            if(!machineOptional.isPresent())
                throw new Exception("Couldn't find machine: "+id);

            Machine machine = machineOptional.get();
            if(machine.getStatus()== StatusEnum.STOPPED)
                throw new Exception("Machine is STOPPED, but shouldn't");

            int time = 10 + random.nextInt(10);

            asyncService.restartMachine(machine,time);

            return new ResponseEntity<>("Machine ("+id+") should stop",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
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
