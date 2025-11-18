package cab.kh.com.access_control_list.dto;

import lombok.Data;

@Data
public class CreateUserReq {
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private String phoneNumber;
    private Long roleId;

}