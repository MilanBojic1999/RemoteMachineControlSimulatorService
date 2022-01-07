package raf.web.Domaci3.scheduling;

import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.MachineService;

public class MachineCreateRunnable implements Runnable{

    private User user;
    private MachineService machineService;


    public MachineCreateRunnable(User user,MachineService machineService) {
        this.user = user;
        this.machineService = machineService;
    }

    @Override
    public void run() {
        Machine machine = new Machine(user);
        machineService.save(machine);
    }
}
