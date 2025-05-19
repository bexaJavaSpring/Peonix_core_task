package bekhruz.com.peonix_core_task.entity.auth;

import bekhruz.com.peonix_core_task.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "auth_users")
public class User extends Auditable {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name" /*nullable = false*/)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "auth_users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    public String getFullName() {
        StringBuilder str = new StringBuilder();
        str.append(this.firstName).append(" ");
        if (this.lastName != null)
            str.append(this.lastName).append(" ");
        if (this.middleName != null)
            str.append(this.middleName).append(" ");
        return str.toString();
    }
}
