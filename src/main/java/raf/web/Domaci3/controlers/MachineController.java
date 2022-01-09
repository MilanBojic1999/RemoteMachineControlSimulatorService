package raf.web.Domaci3.controlers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;
import raf.web.Domaci3.Paths;
import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.scheduling.MachineCreateRunnable;
import raf.web.Domaci3.scheduling.MachineDeleteRunnable;
import raf.web.Domaci3.scheduling.MachineRestartRunnable;
import raf.web.Domaci3.scheduling.MachineStartStopRunnable;
import raf.web.Domaci3.search.MachineCriteria;
import raf.web.Domaci3.search.MachineCriteriaList;
import raf.web.Domaci3.search.MachineSpecification;
import raf.web.Domaci3.services.ErrorMassageService;
import raf.web.Domaci3.services.MachineAsyncService;
import raf.web.Domaci3.util.JwtUtil;
import raf.web.Domaci3.security.Tokens;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    private ErrorMassageService errorMassageService;

    private TaskScheduler taskScheduler;

    private JwtUtil jwtUtil;
    private Gson gson;

    private Random random;
    DateTimeFormatter formatter;

    @Autowired
    public MachineController(MachineAsyncService asyncService, MachineService machineService, UserService userService,ErrorMassageService errorMassageService,TaskScheduler taskScheduler) {
        this.machineService = machineService;
        this.userService = userService;
        this.asyncService = asyncService;
        this.errorMassageService = errorMassageService;

        this.taskScheduler = taskScheduler;

        this.jwtUtil = new JwtUtil();
        this.gson = new Gson();
        this.random = new Random();

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    private static final Specification<Machine> isActive = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"),true));


    @PostMapping(Paths.SEARCH_MACHINE)
    public ResponseEntity<List<Machine>> searchMachines(@RequestHeader(Tokens.HEADER) String jwt, @RequestBody MachineCriteriaList criteriaList){
        try {
            String email = jwtUtil.extractEmail(jwt);
            User user = userService.getUserByEmail(email);

            Specification<Machine> goodId = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), user));

            Specification<Machine> spec = new MachineSpecification(criteriaList.getList().get(0));
            for (MachineCriteria mc : criteriaList.getList()) {
                spec = spec.and(new MachineSpecification(mc));
            }

            List<Machine> machines = machineService.getRepository().findAll(spec.and(goodId).and(isActive));
            return new ResponseEntity<>(machines, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(Paths.SEARCH_MACHINE+"/all")
    public List<Machine> getAllMachines(@RequestHeader(Tokens.HEADER) String jwt){
        String email = jwtUtil.extractEmail(jwt);
        User user = userService.getUserByEmail(email);

        Specification<Machine> goodId = ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"),user));
        return machineService.getRepository().findAll(goodId.and(isActive));
    }

    public Machine getMachineById(long id){
        Optional<Machine> machineOptional = machineService.findById(id);
        return machineOptional.orElse(null);

    }

    @PutMapping(path = Paths.START_MACHINE)
    public ResponseEntity<String> startMachine(@RequestParam("id") long id,@RequestParam(value = "date",required = false) String date){
        try{
            if(date == null) {
                Machine machine = getMachineById(id);
                if (machine == null)
                    throw new Exception("Couldn't find machine: " + id);

                if (machine.getStatus() == StatusEnum.RUNNING)
                    throw new Exception("Machine is RUNNING, but shouldn't");

                int time = 10 + random.nextInt(10);

                asyncService.startMachine(machine, time);
            }else{
                LocalDateTime ldt = LocalDateTime.parse(date,formatter);

                int time = 10 + random.nextInt(10);

                taskScheduler.schedule(new MachineStartStopRunnable(this.machineService,id,StatusEnum.RUNNING,time),ldt.atZone(ZoneId.systemDefault()).toInstant());
            }
            return new ResponseEntity<>("Machine ("+id+") should start",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = Paths.STOP_MACHINE)
    public ResponseEntity<String> stopMachine(@RequestParam("id") long id,@RequestParam(value = "date",required = false) String date){
        try{
            if(date == null) {
                Machine machine = getMachineById(id);
                if (machine == null)
                    throw new Exception("Couldn't find machine: " + id);

                if (machine.getStatus() == StatusEnum.STOPPED)
                    throw new Exception("Machine is STOPPED, but shouldn't");

                int time = 10 + random.nextInt(10);

                asyncService.stopMachine(machine, time);
            }else {
                LocalDateTime ldt = LocalDateTime.parse(date,formatter);

                int time = 10 + random.nextInt(10);

                taskScheduler.schedule(new MachineStartStopRunnable(this.machineService,id,StatusEnum.STOPPED,time),ldt.atZone(ZoneId.systemDefault()).toInstant());
            }
            return new ResponseEntity<>("Machine ("+id+") should stop",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = Paths.RESTART_MACHINE)
    public ResponseEntity<String> restartMachine(@RequestParam("id") long id,@RequestParam(value = "date",required = false) String date){
        try{
            if(date == null) {
                Machine machine = getMachineById(id);
                if (machine == null)
                    throw new Exception("Couldn't find machine: " + id);


                if (machine.getStatus() == StatusEnum.STOPPED)
                    throw new Exception("Machine is STOPPED, but shouldn't");

                int time = 10 + random.nextInt(10);

                asyncService.restartMachine(machine, time);
            }else{
                LocalDateTime ldt = LocalDateTime.parse(date,formatter);

                int time = 10 + random.nextInt(10);

                taskScheduler.schedule(new MachineRestartRunnable(id,time,this.machineService),ldt.atZone(ZoneId.systemDefault()).toInstant());
            }
            return new ResponseEntity<>("Machine ("+id+") should stop",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(path = Paths.CREATE_MACHINE)
    public ResponseEntity<Boolean> createMachine(@RequestParam(value = "date",required = false) String date,@RequestHeader(Tokens.HEADER) String jwt){
        try{
            String email = jwtUtil.extractEmail(jwt);
            if(date == null) {
                User user = userService.getUserByEmail(email);

                Machine machine = new Machine(user);
                machineService.save(machine);
            }else {

                LocalDateTime ldt = LocalDateTime.parse(date,formatter);
                taskScheduler.schedule(new MachineCreateRunnable(email,this.machineService,this.userService),ldt.atZone(ZoneId.systemDefault()).toInstant());

            }
            return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = Paths.DESTROY_MACHINE)
    public ResponseEntity<?> destroyMachine(@RequestParam("id") long id,@RequestParam(value = "date",required = false) String date){

        if(date == null) {
            Machine machine = getMachineById(id);
            if (machine == null)
                return new ResponseEntity<>("Couldn't find machine: " + id, HttpStatus.BAD_REQUEST);


            if (machine.getStatus() == StatusEnum.RUNNING)
                return new ResponseEntity<>("Machine is RUNNING. can't delete machine", HttpStatus.BAD_REQUEST);

            machine.setActive(false);
            machineService.save(machine);
        }else {

            LocalDateTime ldt = LocalDateTime.parse(date,formatter);
            taskScheduler.schedule(new MachineDeleteRunnable(id,this.machineService),ldt.atZone(ZoneId.systemDefault()).toInstant());
        }
        return ResponseEntity.ok().body("Deleted machine: "+id);
    }


    public List<ErrorMassage> getErrors(@RequestHeader(Tokens.HEADER) String jwt){
        try {
            String email = jwtUtil.extractEmail(jwt);
            User user = userService.getUserByEmail(email);

            return errorMassageService.findByUser(user);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
