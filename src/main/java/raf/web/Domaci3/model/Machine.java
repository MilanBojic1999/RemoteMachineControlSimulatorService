package raf.web.Domaci3.model;

import javax.persistence.*;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "userId")
    private User createdBy;

    private boolean active;


    public Machine() {
    }

    public Machine(long id, StatusEnum status, User createdBy, boolean active) {
        this.id = id;
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
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

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", active=" + active +
                '}';
    }
}
