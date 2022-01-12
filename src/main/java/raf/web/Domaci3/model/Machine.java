package raf.web.Domaci3.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column()
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "userId", nullable = false)
    private User createdBy;

    private boolean active;

    private Date created;

    public Machine(StatusEnum status, User createdBy, boolean active, Date created) {
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
        this.created = created;
    }

    public Machine(User createdBy,String name){
        this.status = StatusEnum.STOPPED;
        this.active = true;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", active=" + active +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return id == machine.id && Objects.equals(createdBy, machine.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy);
    }
}
