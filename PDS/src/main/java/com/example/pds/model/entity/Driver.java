package com.example.pds.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name = "last_checked_in", referencedColumnName = "id")
    private Office lastCheckedIn;
    @OneToOne
    @JoinColumn(name = "employee_info_id", referencedColumnName = "id")
    private EmployeeInfo employeeInfo;
    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
}