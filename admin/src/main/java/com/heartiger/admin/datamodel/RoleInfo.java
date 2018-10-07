package com.heartiger.admin.datamodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class RoleInfo implements Serializable {

    public RoleInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String roleName;

    private Boolean isDeleted = false;

    private Boolean isActive = true;


    public Integer getUserId() {
        return roleId;
    }

    public void setUserId(Integer userId) {
        this.roleId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}