package cab.kh.com.access_control_list.controller;

import cab.kh.com.access_control_list.dto.AssignRoleReq;
import cab.kh.com.access_control_list.dto.CreateUserReq;
import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.UserRepo;
import cab.kh.com.access_control_list.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @PostMapping
    public User create(@Valid @RequestBody CreateUserReq req) {
        return userService.createUser(req.getUsername(), req.getPassword(), req.getEmail());
    }

    @PostMapping("/assign-role")
    public User assignRole(@Valid @RequestBody AssignRoleReq req) {
        return userService.assignRole(req.getUserId(), req.getRoleId());
    }

    @GetMapping("/{id}/permissions")
    public Map<String, Object> permissions(@PathVariable Long id) {
        var u = userRepo.findById(id).orElseThrow();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("username", u.getUsername());
        res.put("roles", u.getRoles().stream().map(Role::getName).toArray());
        res.put("permissions", userService.effectivePermissions(id));
        return res;
    }

    @GetMapping
    public List<User> all() {
        return userRepo.findAll();
    }
}
