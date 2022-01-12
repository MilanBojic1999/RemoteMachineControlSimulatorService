package raf.web.Domaci3.response_request;

import java.util.Date;

public class ErrorDto {

    private long id;
    private String massage;
    private long machineId;
    private Date date;

    public ErrorDto(long id,String massage, long machineId,Date date) {
        this.id = id;
        this.massage = massage;
        this.machineId = machineId;
        this.date = date;
    }

    public ErrorDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
