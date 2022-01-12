package raf.web.Domaci3.scheduling;

import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IMachineRepository;
import raf.web.Domaci3.services.ErrorMassageService;
import raf.web.Domaci3.services.MachineService;

import java.util.Optional;

public class MachineStartStopRunnable implements Runnable{


    private long id;
    private StatusEnum statusEnum;
    private int sec2Sleep;
    private MachineService machineService;
    private ErrorMassageService errorMassageService;
    private User user;

    public MachineStartStopRunnable(MachineService machineService, long id, StatusEnum statusEnum, int sec2Sleep,User user, ErrorMassageService errorMassageService) {
        System.out.println("CREATED RUNNABLE");
        this.id = id;
        this.statusEnum = statusEnum;
        this.sec2Sleep = sec2Sleep;
        this.machineService = machineService;
        this.errorMassageService = errorMassageService;
        this.user = user;
    }

    @Override
    public void run() {
        Machine machine = null;
        try{
            System.out.println("STATGGGGG kjdf");
            System.out.flush();
            Optional<Machine> machineOptional = machineService.findById(id);

            if (!machineOptional.isPresent())
                throw new Exception("Couldn't find machine");

            machine = machineOptional.get();

            if(machine.getStatus() == this.statusEnum){
                throw new Exception("This machine ("+id+") is "+this.statusEnum.toString()+", so can't do operation");
            }

            System.out.println("Machine:" + machine + " sleep " + sec2Sleep);


            Thread.sleep(sec2Sleep*1000L);

            machine.setStatus(statusEnum);
            machineService.save(machine);
        } catch (Exception e) {
            ErrorMassage em = new ErrorMassage(e.getMessage(),machine,user);
            errorMassageService.save(em);
        }

    }
}
