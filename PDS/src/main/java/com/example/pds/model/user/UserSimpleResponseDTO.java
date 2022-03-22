package com.example.pds.model.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSimpleResponseDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
