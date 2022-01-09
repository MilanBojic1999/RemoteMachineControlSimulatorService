package raf.web.Domaci3.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "error_massage")
public class ErrorMassage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String massage;

    @Basic
    @Column(name = "timestamp", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id", nullable = false)
    private Machine machine;

    public ErrorMassage(String massage,Machine machine) {
        this.massage = massage;
        this.machine = machine;
    }

    public ErrorMassage() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
