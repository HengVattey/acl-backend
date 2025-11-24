package cab.kh.com.access_control_list.service;



import cab.kh.com.access_control_list.dto.UpdateUserReq;
import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.RoleRepo;
import cab.kh.com.access_control_list.repository.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor // Use this one instead of inject service
@Transactional
public class UserService {


    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder pe;

//
//    private UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder pe) {
//        this.userRepo = userRepo;
//        this.roleRepo = roleRepo;
//        this.pe = pe;
//    }

    public User createUser(String username, String rawPwd, String email, String phoneNumber) {
        if (userRepo.findByUsername(username).isPresent()) throw new IllegalArgumentException("username exists");
       User u = User.builder().username(username).password(pe.encode(rawPwd)).email(email).enabled(true).phoneNumber(phoneNumber).build();
//      User u=  User.builder().username(username).password((rawPwd)).email(email).enabled(true).build();
        return userRepo.save(u);
    }
//    public User assignRole(Long userId, Long roleId){
//        User u = userRepo.findById(userId).orElseThrow();
//        Role r = roleRepo.findById(roleId).orElseThrow();
//        u.getRoles().add(r);
//        return u;
//    }

    @Transactional
    public User assignRole(Long userId, Long roleId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id = " + userId));

        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id = " + roleId));

        // ensure roles set exists
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        user.getRoles().add(role);

        // SAVE TO DB! (missing before)
        return userRepo.save(user);
    }




//    public  superAdminAssignRole(Long userId, Long roleId){
//        Role r
//    }

    public Map<String,Boolean> effectivePermissions(Long userId){
        User u = userRepo.findById(userId).orElseThrow();
        Set<String> names = new HashSet<>();
        u.getRoles().forEach(r -> r.getPermissions().forEach(p -> names.add(p.getName())));
        Map<String,Boolean> map = new LinkedHashMap<>();
        for (String key : List.of("READ","WRITE","SHARE","DELETE","MANAGE")) map.put(key, names.contains(key));
        return map;
    }

    public User updateUser(Long userId, UpdateUserReq req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        if (req.getUsername() != null && !req.getUsername().isBlank() && !req.getUsername().equals(user.getUsername())) {
            if (userRepo.findByUsername(req.getUsername()).isPresent()) {
                throw new IllegalArgumentException("Username '" + req.getUsername() + "' is already taken.");
            }
            user.setUsername(req.getUsername());
        }

        // SECURELY update password only if a new one is provided
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(pe.encode(req.getPassword()));
        }

        // Update phone number if provided
        if (req.getPhoneNumber() != null) {
            user.setPhoneNumber(req.getPhoneNumber());
        }

        return userRepo.save(user);
    }



}


