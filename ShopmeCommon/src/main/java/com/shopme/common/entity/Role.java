package com.shopme.common.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 40,unique = true)
    private String name;

    @Column(nullable = false,length = 150)
    private String description;

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Role(String name) {
        this.name = name;
    }
    public Role() {
    }

    public Integer getId(){
            return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
}
