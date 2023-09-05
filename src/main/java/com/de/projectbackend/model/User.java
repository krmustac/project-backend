package com.de.projectbackend.model;

import com.de.projectbackend.enums.Roles;
import com.de.projectbackend.passwordvalidation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    @NotBlank(message = "username should not be blank or empty")
    @Email(message = "username should be an email")
    private String username;
    @ValidPassword
    private String password;
    private Integer roleId;
    private String token;

    public String getRole(Integer roleId){
        return roleId == 1 ? Roles.ROLE_ADMIN.name() : Roles.ROLE_USER.name();
    }

}
