package raf.web.Domaci3.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "user_id", nullable = false)
    private User createdBy;

    private boolean active;

    private Date created;

    public Machine(StatusEnum status, User createdBy, boolean active, Date created) {
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
        this.created = created;
    }

    public Machine(User createdBy){
        this.status = StatusEnum.STOPPED;
        this.active = true;
        this.createdBy = createdBy;
        this.created = new Date(System.currentTimeMillis());
    }


    public Machine() {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
