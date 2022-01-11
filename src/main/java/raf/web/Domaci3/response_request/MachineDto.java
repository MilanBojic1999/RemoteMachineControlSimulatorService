package raf.web.Domaci3.response_request;

import raf.web.Domaci3.model.StatusEnum;

import java.util.Date;

public class MachineDto {

    private long id;
    private String name;
    private StatusEnum status;
    private Date date;


    public MachineDto(long id,String name, StatusEnum status, Date date) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
