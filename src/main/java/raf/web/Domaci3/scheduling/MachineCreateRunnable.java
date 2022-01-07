package raf.web.Domaci3.scheduling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;

public class MachineCreateRunnable implements Runnable{

    private String email;
    private MachineService machineService;
    private UserService userService;

    public MachineCreateRunnable(String email,MachineService machineService,UserService userService) {
        this.email = email;
        this.machineService = machineService;
        this.userService = userService;
    }

    @Override
    public void run() {
        try{

            User user = userService.getUserByEmail(email);

            Machine machine = new Machine(user);
            machineService.save(machine);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
