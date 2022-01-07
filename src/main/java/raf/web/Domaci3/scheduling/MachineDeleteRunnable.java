package raf.web.Domaci3.scheduling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.services.MachineService;

import java.util.Optional;

public class MachineDeleteRunnable implements Runnable{


    private long id;
    private MachineService machineService;

    public MachineDeleteRunnable(long id, MachineService machineService) {
        this.id = id;
        this.machineService = machineService;
    }

    @Override
    public void run() {
        try {
            Optional<Machine> machineOptional = machineService.findById(id);

            if (!machineOptional.isPresent())
                throw new Exception("Couldn't find machine");

            Machine machine = machineOptional.get();

            if (machine.getStatus() == StatusEnum.RUNNING)
                throw  new Exception("Machine is RUNNING. can't delete machine");

            machine.setActive(false);
            machineService.save(machine);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
