package cab.kh.com.access_control_list.model;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity @Table(name="users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String username;
    @Column(unique = true, nullable=false)
    private String password;
    @Column(unique=true) // Can null
    private String email;
    private boolean enabled = true;
    @Column(unique = true,nullable = true)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();


}