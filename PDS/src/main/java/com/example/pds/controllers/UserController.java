package com.example.pds.controllers;

import com.example.pds.model.packages.packageDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.model.user.*;
import com.example.pds.model.user.userDTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserSimpleResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        UserSimpleResponseDTO dto = userService.register(registerDTO);
        return ResponseEntity.status(201).body(dto);
    }
    @PutMapping("/forgottenPassword")
    @ResponseStatus(code = HttpStatus.OK)
    public String forgottenPassword(@RequestBody String email) {
        userService.forgottenPassword(email);
        return "New password token is sent to email";
    }

    @PutMapping("/changePassword")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> changePassword(@RequestBody UserChangePasswordDTO changePasswordDTO,Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        UserSimpleResponseDTO dto = userService.changePassword(changePasswordDTO, id);
        return ResponseEntity.status(200).body(dto);

    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserComplexResponseDTO> editProfile(@RequestBody UserProfileChangeDTO user, Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        UserComplexResponseDTO dto = userService.editProfile(id, user);
        return ResponseEntity.status(200).body(dto);
    }


    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(Authentication authentication, Pageable page) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        List<TransactionResponseDTO> transactions = userService.getAllTransactions(id, page);
        return transactions;
    }

}
