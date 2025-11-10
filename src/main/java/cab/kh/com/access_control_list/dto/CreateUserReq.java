package cab.kh.com.access_control_list.dto;

import cab.kh.com.access_control_list.model.Permission;
import cab.kh.com.access_control_list.model.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class CreateUserReq {
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private String phoneNumber;
    private Long roleId;


//    private List<String> roleNames;       // existing roles
//    private List<String> permissionNames;


//    private String username;
//
//    private String password;
//
//    private String email;
//    private Long roleId;



}