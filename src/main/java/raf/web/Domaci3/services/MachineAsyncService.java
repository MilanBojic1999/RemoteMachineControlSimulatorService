package raf.web.Domaci3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;
import raf.web.Domaci3.repositories.IMachineRepository;

@Service

public class MachineAsyncService {

    private IMachineRepository repository;

    @Autowired
    public MachineAsyncService(IMachineRepository repository) {
        this.repository = repository;
    }

    public void startMachine(Machine machine, int sec2Sleep){
        try {
            Thread.sleep(sec2Sleep*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(StatusEnum.RUNNING);
        repository.save(machine);
    }

    public void stopMachine(Machine machine, int sec2Sleep){
        try {
            Thread.sleep(sec2Sleep*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(StatusEnum.STOPPED);
        repository.save(machine);
    }

    public void restartMachine(Machine machine, int sec2Sleep){

        double time = sec2Sleep / 2.0;

        try {
            Thread.sleep((long) (time*1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(StatusEnum.STOPPED);
        repository.saveAndFlush(machine);

        try {
            Thread.sleep((long) (time*1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(StatusEnum.RUNNING);
        repository.save(machine);

    }

}
