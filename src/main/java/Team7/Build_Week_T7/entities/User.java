package Team7.Build_Week_T7.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({
        "password", "authorities",
        "enabled", "accountNonExpired",
        "credentialsNonExpired", "accountNonLocked"
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nome;
    private String cognome;
    private String avatar;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")  // evitiamo loop
    private List<UserRole> roles;

    public User(String username, String email, String password, String nome, String cognome, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = avatar;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(userRole ->
                        new SimpleGrantedAuthority(userRole.getRole().getName()))
                .collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
