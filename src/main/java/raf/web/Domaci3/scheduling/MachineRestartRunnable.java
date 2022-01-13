package raf.web.Domaci3.scheduling;

import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.services.ErrorMassageService;
import raf.web.Domaci3.services.MachineService;

import java.util.Optional;

public class MachineRestartRunnable implements Runnable{

    private long id;
    private double halfSec2Sleep;
    private MachineService machineService;
    private ErrorMassageService errorMassageService;
    private User user;

    public MachineRestartRunnable(long id, int sec2Sleep,User user, MachineService machineService, ErrorMassageService errorMassageService) {
        this.id = id;
        this.halfSec2Sleep = sec2Sleep/2.0;
        this.machineService = machineService;
        this.errorMassageService = errorMassageService;
        this.user = user;
    }

    @Override
    public void run() {
        Machine machine = null;
        try{
            Optional<Machine> machineOptional = machineService.findById(id);

            if (!machineOptional.isPresent())
                throw new Exception("Couldn't find machine");

            machine = machineOptional.get();

            if (machine.getStatus() == StatusEnum.STOPPED)
                throw  new Exception("Machine is STOPPED. can't stop machine (1)");

            Thread.sleep((long)(halfSec2Sleep* 1000));

            if (machine.getStatus() == StatusEnum.STOPPED)
                throw  new Exception("Machine is STOPPED. can't stop machine (2)");

            machine.setStatus(StatusEnum.STOPPED);

            if (machine.getStatus() == StatusEnum.RUNNING)
                throw  new Exception("Machine is RUNNING. can't start machine (1)");

            Thread.sleep((long)(halfSec2Sleep* 1000));

            if (machine.getStatus() == StatusEnum.RUNNING)
                throw  new Exception("Machine is RUNNING. can't start machine (2)");

            machine.setStatus(StatusEnum.RUNNING);
        } catch (Exception e) {
            ErrorMassage em = new ErrorMassage(e.getMessage(),"Restart",machine,user);
            errorMassageService.save(em);
        }

    }
}
