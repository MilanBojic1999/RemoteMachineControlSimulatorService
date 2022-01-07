package raf.web.Domaci3.scheduling;

import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.repositories.IMachineRepository;
import raf.web.Domaci3.services.MachineService;

import java.util.Optional;

public class MachineStartStopRunnable implements Runnable{


    private long id;
    private StatusEnum statusEnum;
    private int sec2Sleep;
    private MachineService machineService;

    public MachineStartStopRunnable(MachineService machineService, long id, StatusEnum statusEnum, int sec2Sleep) {
        this.id = id;
        this.statusEnum = statusEnum;
        this.sec2Sleep = sec2Sleep;
        this.machineService = machineService;
    }

    @Override
    public void run() {
        try{
            Optional<Machine> machineOptional = machineService.findById(id);

            if (!machineOptional.isPresent())
                throw new Exception("Couldn't find machine");

            Machine machine = machineOptional.get();

            if(machine.getStatus() == this.statusEnum){
                throw new Exception("This machine ("+id+") is "+this.statusEnum.toString()+", so can't do operation");
            }

            System.out.println("Machine:" + machine + " sleep " + sec2Sleep);


            Thread.sleep(sec2Sleep*1000L);

            machine.setStatus(statusEnum);
            machineService.save(machine);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
