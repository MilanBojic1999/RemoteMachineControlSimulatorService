package raf.web.Domaci3.scheduling;

import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.ErrorMassageService;
import raf.web.Domaci3.services.MachineService;
import raf.web.Domaci3.services.UserService;

public class MachineCreateRunnable implements Runnable{

    private String email;
    private MachineService machineService;
    private UserService userService;
    private String name;
    private ErrorMassageService errorMassageService;
    private User user;

    public MachineCreateRunnable(String email,String name,User user,MachineService machineService,UserService userService, ErrorMassageService errorMassageService) {
        this.email = email;
        this.machineService = machineService;
        this.userService = userService;
        this.name = name;
        this.errorMassageService = errorMassageService;
        this.user = user;
    }

    @Override
    public void run() {
        Machine machine = null;
        try{

            User user = userService.getUserByEmail(email);

            machine = new Machine(user,name);
            machineService.save(machine);

        }catch (Exception e){
            ErrorMassage em = new ErrorMassage(e.getMessage(),"Create",machine,user);
            errorMassageService.save(em);
        }
    }
}
