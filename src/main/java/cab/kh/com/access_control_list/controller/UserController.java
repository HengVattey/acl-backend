package cab.kh.com.access_control_list.controller;
import cab.kh.com.access_control_list.dto.AssignRoleReq;
import cab.kh.com.access_control_list.dto.CreateUserReq;
import cab.kh.com.access_control_list.dto.ResetPasswordReq;
import cab.kh.com.access_control_list.dto.UserInfoResponse;
import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.UserRepo;
import cab.kh.com.access_control_list.service.SmsService;
import cab.kh.com.access_control_list.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;
    private final PasswordEncoder bCryptPasswordEncoder;
    private  final SmsService smsService;

    //To create new user
    @PostMapping
    public User create(@Valid @RequestBody CreateUserReq req) {
//        this.smsService.sendSms("cab_sms@mekongnet","a6f82e0bb188214b5ce84c1dd713fe53",
//                "CAB", "username= " +req.getUsername()+ ", password = " +req.getPassword(),req.getPhoneNumber(),"0",
//                "From Cab");
       User user=userService.createUser(req.getUsername(), req.getPassword(),req.getEmail(),req.getPhoneNumber());
        System.out.println("User is created Id="+user.getId()+"user="+req.getUsername());

        //req.setRoleId(req.getRoleId());
      //  System.out.println("Role Id"+req.getRoleId());

        //System.out.println(this.userService.assignRole(user.getId(), req.getRoleId())) ;
        return  userService.assignRole(user.getId(), req.getRoleId());
    }

    //To allow super can reset password for user
    @PutMapping("/{id}/reset-password")
    public String resetPassword(@PathVariable Long id, @RequestBody ResetPasswordReq req) {
        User u = userRepo.findById(id).orElseThrow();

        u.setPassword(bCryptPasswordEncoder.encode(req.getNewPassword()));
        userRepo.save(u);
        return "Password reset successfully";
    }
    //To assign role to user
    @PostMapping("/assign-role")
    public User assignRole(@Valid @RequestBody AssignRoleReq req) {
        return userService.assignRole(req.getUserId(), req.getRoleId());
    }



    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        var user = userRepo.findByUsername(authentication.getName()).orElseThrow();
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new UserInfoResponse(user.getUsername(), roles));
    }



    //To grant permissions to role
    @GetMapping("/{id}/permissions")
    public Map<String, Object> permissions(@PathVariable Long id) {
        System.out.println("Get permissions for user " + id);
        var u = userRepo.findById(id).orElseThrow();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("username", u.getUsername());
        res.put("roles", u.getRoles().stream().map(Role::getName).toArray());
        res.put("permissions", userService.effectivePermissions(id));
        return res;
    }

    @GetMapping
    public List<User> all() {
        System.out.println("List all users");
        return userRepo.findAll();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody CreateUserReq req) {
        User u = userRepo.findById(id).orElseThrow();
        u.setUsername(req.getUsername());
        u.setPassword(req.getPassword());
        u.setEmail(req.getEmail());
        u.setEnabled(req.isEnabled());
        return userRepo.save(u);
    }


}
