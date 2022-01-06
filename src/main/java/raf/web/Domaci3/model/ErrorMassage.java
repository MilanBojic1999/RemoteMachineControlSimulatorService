package raf.web.Domaci3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ErrorMassage {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String massage;

    public ErrorMassage(String massage) {
        this.massage = massage;
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
}
