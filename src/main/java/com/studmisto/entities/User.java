package com.studmisto.entities;

import com.studmisto.entities.enums.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    @NotNull
    private String firstName;
    @Column(name = "second_name")
    @NotNull
    private String secondName;
    @Email
    @Column(name = "email")
    @NotNull
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    @NotNull
    private Gender gender;
    @Column(name = "position")
    @NotNull
    private String position;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private Status status;
    @Column(name = "group_name")
    private String groupName;
    @Enumerated(EnumType.STRING)
    @Column(name = "institute")
    private Institute institute;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "balance")
    private double balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "tariff")
    private Tariff tariff;
    @Column(name = "userpic")
    private String userpic;
    @Column(length = 64, name = "password")
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(getRole());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getFirstName() + " " + getSecondName();
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
