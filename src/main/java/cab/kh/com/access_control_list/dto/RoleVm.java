package cab.kh.com.access_control_list.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVm {
    private Long roleId;
    private String roleName;
}
