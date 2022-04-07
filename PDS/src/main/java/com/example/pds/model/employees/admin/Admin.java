package com.example.pds.model.employees.admin;

import com.example.pds.model.user.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@Setter
@Getter
@NoArgsConstructor

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile profile;

}
