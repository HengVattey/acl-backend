package cab.kh.com.access_control_list.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssignRoleReq { @NotNull
    private Long userId;
    @NotNull private Long roleId;

}
