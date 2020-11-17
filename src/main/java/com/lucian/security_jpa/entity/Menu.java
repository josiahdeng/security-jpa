package com.lucian.security_jpa.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pattern")
    private String pattern;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_role", joinColumns = @JoinColumn(name = "menu_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
