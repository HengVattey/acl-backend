package cab.kh.com.access_control_list.dto;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class GrantPermissionReq { @NotNull
    private Long roleId;
    @NotNull
    private Long permissionId;
}
