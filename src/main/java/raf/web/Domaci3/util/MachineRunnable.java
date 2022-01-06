package raf.web.Domaci3.util;

import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;

public class MachineRunnable implements Runnable{


    private Machine machine;
    private StatusEnum statusEnum;
    private int sec2Sleep;

    public MachineRunnable(Machine machine, StatusEnum statusEnum, int sec2Sleep) {
        this.machine = machine;
        this.statusEnum = statusEnum;
        this.sec2Sleep = sec2Sleep;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sec2Sleep* 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(statusEnum);
    }
}
