package raf.web.Domaci3.response_request;

public class MachineAction {
    private long id;
    private String date;

    public MachineAction(long id, String date) {
        this.id = id;
        this.date = date;
    }

    public MachineAction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MachineAction{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}
