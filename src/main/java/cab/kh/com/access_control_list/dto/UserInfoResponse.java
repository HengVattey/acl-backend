package cab.kh.com.access_control_list.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private String username;
    private Set<String> roles;
}
