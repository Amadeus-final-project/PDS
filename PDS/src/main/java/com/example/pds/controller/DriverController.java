package com.example.pds.controller;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.driver.Driver;
import com.example.pds.model.employees.driver.DriverService;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.user.userDTO.LoginDTO;
import com.example.pds.model.user.userDTO.UserSimpleResponseDTO;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("driver/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> logIn(@RequestBody EmployeeLoginDTO driver, HttpServletRequest request) {
        EmployeeSimpleResponseDTO dto = driverService.login(driver);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        session.setAttribute(Constants.IS_DRIVER,true);
        return ResponseEntity.status(200).body(dto);
    }
    @PostMapping("driver/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public String logOut(HttpSession session) {
        session.invalidate();
        return "Have a nice day";
    }

    @PutMapping("driver/vehicle/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void getCar(@PathVariable int id, HttpServletRequest request){
        Object driverId = request.getSession().getAttribute(Constants.USER_ID);
        Object isDriver = request.getSession().getAttribute(Constants.IS_DRIVER);
        driverService.getVehicle(driverId, id, isDriver);
    }
    @PutMapping("driver/vehicle/releaseCar")
    @ResponseStatus(code=HttpStatus.OK)
    public String releaseCar(HttpServletRequest request){
        Object driverId = request.getSession().getAttribute(Constants.USER_ID);
        Object isDriver = request.getSession().getAttribute(Constants.IS_DRIVER);
        driverService.releaseVehicle(driverId,isDriver);
        return "Done";
    }

    @GetMapping("driver/getAllDrivers")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DriverSimpleResponseDTO> getAllDrivers(HttpServletRequest request){
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<DriverSimpleResponseDTO> drivers=driverService.getAllDrivers(isUser, isLogged);
        return drivers;

    }

    @GetMapping("driver/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DriverSimpleResponseDTO getDriver(@PathVariable int id, HttpServletRequest request){
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        DriverSimpleResponseDTO driver = driverService.getDriverById(id,isUser, isLogged);
        return driver;
    }

    @PutMapping("driver/requestPaidLeave")
    @ResponseStatus(code = HttpStatus.OK)
    public String requestPaidLeave(@RequestBody Date start, Date end, String description, HttpServletRequest request){
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        //TODO
      //  driverService.requestPaidLeave(start, end, description, isLogged, isUser);
        return null;
    }



}
