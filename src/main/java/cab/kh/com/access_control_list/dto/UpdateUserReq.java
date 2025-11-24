package cab.kh.com.access_control_list.dto;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class UpdateUserReq {

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String phoneNumber;
}