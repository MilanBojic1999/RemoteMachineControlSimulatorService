package raf.web.Domaci3.model;

import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(name = "timestamp", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "user",referencedColumnName = "userId")
    private User user;

    public ErrorMassage(String massage,Machine machine,User user) {
        this.massage = massage;
        this.machine = machine;
        this.user = user;
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
