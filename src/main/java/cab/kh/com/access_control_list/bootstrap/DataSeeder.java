package cab.kh.com.access_control_list.bootstrap;

import cab.kh.com.access_control_list.model.Permission;
import cab.kh.com.access_control_list.model.Role;
import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.PermissionRepo;
import cab.kh.com.access_control_list.repository.RoleRepo;
import cab.kh.com.access_control_list.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PermissionRepo permRepo;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder pe;

    @Override
    public void run(String... args) {
        try {


            List<String> perms = List.of("READ", "WRITE", "SHARE", "DELETE", "MANAGE");
            for (String p : perms) {
                permRepo.findByName(p).orElseGet(() ->
                        permRepo.save(Permission.builder()
                                .name(p)
                                .description(p + " permission")
                                .build())
                );
            }


            List<Permission> allPerms = permRepo.findAll();

            Role superRole = roleRepo.findByName("SUPER_ADMIN").orElse(null);

            if (superRole == null) {
                superRole = Role.builder()
                        .name("SUPER_ADMIN")
                        .description("Super admin role")
                        .permissions(new HashSet<>(allPerms))
                        .build();

                roleRepo.save(superRole);
                System.out.println(" SUPER_ADMIN role created.");
            } else {
                if (superRole.getPermissions() == null) {
                    superRole.setPermissions(new HashSet<>(allPerms));
                }


                final Role existingRole = superRole;


                allPerms.stream()
                        .filter(p -> !existingRole.getPermissions().contains(p))
                        .forEach(p -> existingRole.getPermissions().add(p));

                roleRepo.save(existingRole);
            }


            if (userRepo.findByUsername("superadmin").isEmpty()) {
                User u = User.builder()
                        .username("superadmin")
                        .password(pe.encode("Admin@123"))
                        .email("superadmin@example.com")
                        .enabled(true)
                        .roles(new HashSet<>())
                        .build();

                u.getRoles().add(superRole);
                userRepo.save(u);
                System.out.println(" Superadmin user created (username: superadmin, password: Admin@123)");
            }

            System.out.println(" DataSeeder completed successfully.");
        } catch (Exception e) {
            System.err.println(" DataSeeder failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
