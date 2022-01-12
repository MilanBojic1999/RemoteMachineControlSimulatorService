package raf.web.Domaci3.scheduling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.ErrorMassageService;
import raf.web.Domaci3.services.MachineService;

import java.util.Optional;

public class MachineDeleteRunnable implements Runnable{


    private long id;
    private MachineService machineService;
    private ErrorMassageService errorMassageService;
    private User user;

    public MachineDeleteRunnable(long id,User user, MachineService machineService, ErrorMassageService errorMassageService) {
        this.id = id;
        this.machineService = machineService;
        this.errorMassageService = errorMassageService;
        this.user = user;
    }

    @Override
    public void run() {
        Machine machine = null;
        try {
            Optional<Machine> machineOptional = machineService.findById(id);

            if (!machineOptional.isPresent())
                throw new Exception("Couldn't find machine");

            machine = machineOptional.get();

            if (machine.getStatus() == StatusEnum.RUNNING)
                throw  new Exception("Machine is RUNNING. can't delete machine");

            machine.setActive(false);
            machineService.save(machine);
        }catch (Exception e){
            ErrorMassage em = new ErrorMassage(e.getMessage(),machine,user);
            errorMassageService.save(em);
        }
    }
}
