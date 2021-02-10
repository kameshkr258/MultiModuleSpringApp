package com.makeen.assignment.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {


    private Long id;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
