package cab.kh.com.access_control_list.controller;

import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.repository.RoleRepo;
import cab.kh.com.access_control_list.service.RbacService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RbacService rbac;
    private final RoleRepo roleRepo;
    @PostMapping
    public Role create(@RequestBody Map<String, String> body) {
        return rbac.createRole(body.get("name"), body.getOrDefault("description", ""));
    }
    @GetMapping
    public List<Role> all() {
        return roleRepo.findAll();
    }
}
