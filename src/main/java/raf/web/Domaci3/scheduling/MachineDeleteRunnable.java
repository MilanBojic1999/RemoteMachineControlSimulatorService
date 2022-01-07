package raf.web.Domaci3.scheduling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.services.MachineService;

public class MachineDeleteRunnable implements Runnable{


    private Machine machine;
    private MachineService machineService;

    public MachineDeleteRunnable(Machine machine, MachineService machineService) {
        this.machine = machine;
        this.machineService = machineService;
    }

    @Override
    public void run() {
        if(machine.getStatus()== StatusEnum.RUNNING)
            System.err.println("Machine is RUNNING. can't delete machine");

        machine.setActive(false);
        machineService.save(machine);
    }
}
