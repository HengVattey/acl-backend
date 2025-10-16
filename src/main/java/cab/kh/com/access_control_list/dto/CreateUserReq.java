package cab.kh.com.access_control_list.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CreateUserReq {
    private String username;
    private String password;
    private String email;
    private boolean enabled;
//    private List<String> roleNames;       // existing roles
//    private List<String> permissionNames;
}