package cab.kh.com.access_control_list.controller;

import cab.kh.com.access_control_list.dto.GrantPermissionReq;
import cab.kh.com.access_control_list.model.Permission;
import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.repository.PermissionRepo;
import cab.kh.com.access_control_list.service.RbacService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final RbacService rbac;
    private final PermissionRepo permRepo;

    @PostMapping
    public Permission create(@RequestBody Map<String, String> body) {
        return rbac.createPermission(body.get("name"), body.getOrDefault("description", ""));
    }

    @PostMapping("/grant")
    public Role grant(@RequestBody GrantPermissionReq req) {
        return rbac.grantPermission(req.getRoleId(), req.getPermissionId());
    }

    @GetMapping
    public List<Permission> all() {
        return permRepo.findAll();
    }
}
