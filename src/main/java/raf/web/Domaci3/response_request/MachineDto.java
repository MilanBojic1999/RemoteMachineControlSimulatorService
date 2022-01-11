package raf.web.Domaci3.response_request;

import raf.web.Domaci3.model.StatusEnum;

import java.util.Date;

public class MachineDto {

    private long id;
    private StatusEnum status;
    private Date date;


    public MachineDto(long id, StatusEnum status, Date date) {
        this.id = id;
        this.status = status;
        this.date = date;
    }

    public MachineDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
