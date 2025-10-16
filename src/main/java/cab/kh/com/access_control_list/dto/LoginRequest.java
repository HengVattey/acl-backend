package cab.kh.com.access_control_list.dto;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank private String password;

}
