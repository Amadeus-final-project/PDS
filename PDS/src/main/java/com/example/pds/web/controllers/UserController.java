package com.example.pds.web.controllers;

import com.example.pds.model.packages.PackageGetMyPackagesDTO;
import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.model.user.*;
import com.example.pds.model.user.userDTO.*;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> logIn(@RequestBody LoginDTO user, HttpServletRequest request) {
        UserSimpleResponseDTO dto = userService.login(user);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.IS_USER, true);
        session.setAttribute(Constants.LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping("/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public String logOut(HttpSession session) {
        session.invalidate();
        return "Have a nice day";
    }

    @PutMapping("/forgottenPassword")
    @ResponseStatus(code = HttpStatus.OK)
    public String forgottenPassword(@RequestParam String email) {
        userService.forgottenPassword(email);
        return "New password token is sent to email";
    }

    @PutMapping("/changePassword")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> changePassword(@RequestBody UserChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        UserSimpleResponseDTO dto = userService.changePassword(changePasswordDTO, id, isUser);
        return ResponseEntity.status(200).body(dto);

    }

    @PutMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserComplexResponseDTO> editProfile(@RequestBody UserProfileChangeDTO user, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        UserComplexResponseDTO dto = userService.editProfile(id, user, isUser);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageGetMyPackagesDTO> getAllPackages(HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        List<PackageGetMyPackagesDTO> dtoList = userService.getAllPackages(id, isUser);
        return dtoList;
    }

    @GetMapping("/getPackageById/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageGetMyPackagesDTO getPackageById(@PathVariable int id, HttpServletRequest request) {
        Object userId = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        PackageGetMyPackagesDTO dto = userService.getPackageBydId(id, userId, isUser);
        return dto;
    }

    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        List<TransactionResponseDTO> transactions = userService.getAllTransactions(id, isUser);
        return transactions;
    }

}
