package com.comunidad.comunidad_backend.dto;

public class CambioPass {
    private String oldPassword;
    private String newPassword;

    public CambioPass(){}

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    
    
}
