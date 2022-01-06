package raf.web.Domaci3.util;

import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.StatusEnum;

public class MachineRestartRunnable implements Runnable{

    private Machine machine;
    private double halfSec2Sleep;

    public MachineRestartRunnable(Machine machine, int sec2Sleep) {
        this.machine = machine;
        this.halfSec2Sleep = sec2Sleep/2.0;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long)(halfSec2Sleep* 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(StatusEnum.STOPPED);
        try {
            Thread.sleep((long)(halfSec2Sleep* 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machine.setStatus(StatusEnum.RUNNING);
    }
}
