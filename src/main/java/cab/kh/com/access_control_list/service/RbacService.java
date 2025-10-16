package cab.kh.com.access_control_list.service;

import cab.kh.com.access_control_list.model.Permission;
import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.repository.PermissionRepo;
import cab.kh.com.access_control_list.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RbacService {
    private final RoleRepo roleRepo; private final PermissionRepo permRepo;

    public Role createRole(String name, String desc){
        return roleRepo.save(Role.builder().name(name).description(desc).build());
    }
    public Permission createPermission(String name, String desc) {
        return permRepo.save(
                Permission.builder()
                        .name(name)
                        .description(desc)
                        .build()
        );
    }

    public Role grantPermission(Long roleId, Long permId){
        Role r = roleRepo.findById(roleId).orElseThrow(); Permission p = permRepo.findById(permId).orElseThrow();
        r.getPermissions().add(p); return r;
    }
}