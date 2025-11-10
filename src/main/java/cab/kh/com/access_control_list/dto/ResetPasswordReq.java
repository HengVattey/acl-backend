package cab.kh.com.access_control_list.dto;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordReq {

    @NotBlank
    private String newPassword;
}
