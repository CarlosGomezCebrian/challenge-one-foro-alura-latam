package edu.foro.api.domain.user;


import edu.foro.api.domain.course.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "users")
@Entity(name = "User")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password_hash;
    private String first_name;
    private String last_name;
    private String email;
    private String image_url;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Date create_date;
    private Boolean activated;


    public User(DataRegistrationUser datosRegistroUsuario) {
        this.login = datosRegistroUsuario.login();
        this.password_hash = datosRegistroUsuario.password_hash();
        this.first_name = datosRegistroUsuario.first_name();
        this.last_name = datosRegistroUsuario.last_name();
        this.email = datosRegistroUsuario.email();
        this.image_url = datosRegistroUsuario.image_url();
        this.category = datosRegistroUsuario.category();
        this.create_date = new Date(System.currentTimeMillis());
        this.activated = true;
    }


    public void inactiveUser() {
        this.activated = false;
    }



    public void UpdateUser(String login, String passwordEncrypted, String firstName, String lastName,
                           String email, String imageUrl, Category category) {
        this.login = login;
        this.password_hash = passwordEncrypted;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.image_url = imageUrl;
        this.category = category;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password_hash;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
