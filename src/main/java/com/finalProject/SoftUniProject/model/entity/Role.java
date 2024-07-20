package com.finalProject.SoftUniProject.model.entity;

import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleENUM name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull UserRoleENUM getName() {
        return name;
    }

    public void setName(@NotNull UserRoleENUM name) {
        this.name = name;
    }
}
