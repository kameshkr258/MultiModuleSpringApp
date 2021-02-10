package com.makeen.assignment.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "last_used_passwords")
@Builder
@Access(value=AccessType.FIELD)
public class LastUsedPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "last_used_password")
    private String lastUsedPassword;

    @Column(name = "create_dt")
    private Date createDt;

    public LastUsedPassword() {
    }

    public LastUsedPassword(Long id,Long userId, String lastUsedPassword, Date createDt) {
        this.id = id;
        this.lastUsedPassword = lastUsedPassword;
        this.createDt = createDt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastUsedPassword() {
        return lastUsedPassword;
    }

    public void setLastUsedPassword(String lastUsedPassword) {
        this.lastUsedPassword = lastUsedPassword;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @ManyToOne(targetEntity = User.class)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}