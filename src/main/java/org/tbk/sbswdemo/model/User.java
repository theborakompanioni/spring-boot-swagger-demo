package org.tbk.sbswdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity()
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    @Convert(converter = CryptoConverter.class)
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = Collections.emptyList();

    protected User() {
    }

    public User(String name, String password) {
        this(name, password, Collections.emptyList());
    }

    public User(String name, String password, List<String> authorities) {
        this.name = requireNonNull(name);
        this.password = requireNonNull(password);
        this.authorities = requireNonNull(authorities);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s']", id, name);
    }
}
