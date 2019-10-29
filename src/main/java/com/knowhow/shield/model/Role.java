package com.knowhow.shield.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ElementCollection(targetClass = Privilege.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    private Set<Privilege> privileges;
}
