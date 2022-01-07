package raf.web.Domaci3.scheduling;

import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.services.MachineService;

import java.util.Optional;

public class MachineRestartRunnable implements Runnable{

    private long id;
    private double halfSec2Sleep;
    private MachineService machineService;

    public MachineRestartRunnable(long id, int sec2Sleep,MachineService machineService) {
        this.id = id;
        this.halfSec2Sleep = sec2Sleep/2.0;
        this.machineService = machineService;
    }

    @Override
    public void run() {
        try{
            Optional<Machine> machineOptional = machineService.findById(id);

            if (!machineOptional.isPresent())
                throw new Exception("Couldn't find machine");

            Machine machine = machineOptional.get();

            if (machine.getStatus() == StatusEnum.STOPPED)
                throw  new Exception("Machine is STOPPED. can't stop machine");

            Thread.sleep((long)(halfSec2Sleep* 1000));

            machine.setStatus(StatusEnum.STOPPED);

            if (machine.getStatus() == StatusEnum.RUNNING)
                throw  new Exception("Machine is RUNNING. can't start machine");

            Thread.sleep((long)(halfSec2Sleep* 1000));

            machine.setStatus(StatusEnum.RUNNING);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
