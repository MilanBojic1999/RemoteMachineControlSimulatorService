package raf.web.Domaci3.scheduling;

import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;

public class MachineCreateRunnable implements Runnable{

    private String email;
    private MachineService machineService;
    private UserService userService;
    private String name;

    public MachineCreateRunnable(String email,String name,MachineService machineService,UserService userService) {
        this.email = email;
        this.machineService = machineService;
        this.userService = userService;
        this.name = name;
    }

    @Override
    public void run() {
        try{

            User user = userService.getUserByEmail(email);

            Machine machine = new Machine(user,name);
            machineService.save(machine);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
