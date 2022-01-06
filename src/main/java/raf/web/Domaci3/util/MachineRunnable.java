package raf.web.Domaci3.util;

import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.repositories.IMachineRepository;

public class MachineRunnable implements Runnable{


    private Machine machine;
    private StatusEnum statusEnum;
    private int sec2Sleep;
    private IMachineRepository repository;

    public MachineRunnable(IMachineRepository repository,Machine machine, StatusEnum statusEnum, int sec2Sleep) {
        this.machine = machine;
        this.statusEnum = statusEnum;
        this.sec2Sleep = sec2Sleep;
        this.repository = repository;
    }

    @Override
    public void run() {
        System.out.println("Machine:" + machine + " sleep " + sec2Sleep);
        try {
            Thread.sleep(sec2Sleep*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(statusEnum);
        repository.save(machine);
    }
}
