package cab.kh.com.access_control_list.service;



import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.RoleRepo;
import cab.kh.com.access_control_list.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class UserService {
    private final UserRepo userRepo; private final RoleRepo roleRepo; private final PasswordEncoder pe;

    public User createUser(String username, String rawPwd, String email){
        if (userRepo.findByUsername(username).isPresent()) throw new IllegalArgumentException("username exists");
        User u = User.builder().username(username).password(pe.encode(rawPwd)).email(email).enabled(true).build();
        return userRepo.save(u);
    }
    public User assignRole(Long userId, Long roleId){
        User u = userRepo.findById(userId).orElseThrow();
        Role r = roleRepo.findById(roleId).orElseThrow();
        u.getRoles().add(r); return u;
    }
    public Map<String,Boolean> effectivePermissions(Long userId){
        User u = userRepo.findById(userId).orElseThrow();
        Set<String> names = new HashSet<>();
        u.getRoles().forEach(r -> r.getPermissions().forEach(p -> names.add(p.getName())));
        Map<String,Boolean> map = new LinkedHashMap<>();
        for (String key : List.of("READ","WRITE","SHARE","DELETE","MANAGE")) map.put(key, names.contains(key));
        return map;
    }
}


