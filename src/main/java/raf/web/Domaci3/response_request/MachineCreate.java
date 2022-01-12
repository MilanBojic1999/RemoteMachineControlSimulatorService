package raf.web.Domaci3.response_request;

public class MachineCreate {

    private String name;
    private String date;

    public MachineCreate(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public MachineCreate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
