package com.example.pds.model.employees.agent;

import com.example.pds.config.CheckAuthentications;
import com.example.pds.config.CheckViolations;
import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.employeeInfo.EmployeeProfileChangeDTO;
import com.example.pds.model.employees.employeeInfo.EmployeeRepository;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class AgentService {

    @Autowired
    Validator validator;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EmployeeRepository employeeRepository;


    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {

        CheckViolations.check(validator, login);

        Agent agent = agentRepository.findByEmail(login.getEmail());

        if (agent == null) {
            throw new NotFoundException("Agent not found");
        }

        return modelMapper.map(agent, EmployeeSimpleResponseDTO.class);
    }


    public EmployeeSimpleResponseDTO editProfile(Object id, EmployeeProfileChangeDTO employeeProfileChangeDTO, Object isAgent) {

        CheckAuthentications.checkIfLogged(id);
        CheckAuthentications.checkIfAgent(isAgent);

        CheckViolations.check(validator, employeeProfileChangeDTO);

        Set<ConstraintViolation<EmployeeProfileChangeDTO>> violations = validator.validate(employeeProfileChangeDTO);


        Agent agent = agentRepository.getById((int) id);

        if (!agent.getEmployeeInfo().getFirstName().equals(employeeProfileChangeDTO.getFirstName())) {
            agent.getEmployeeInfo().setFirstName(employeeProfileChangeDTO.getFirstName());
        }
        if (!agent.getEmployeeInfo().getLastName().equals(employeeProfileChangeDTO.getLastName())) {
            agent.getEmployeeInfo().setLastName(employeeProfileChangeDTO.getLastName());
        }

        if (agent.getEmployeeInfo().getPhoneNumber() == null) {
            agent.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());
        }
        if (!agent.getEmployeeInfo().getPhoneNumber().equals(employeeProfileChangeDTO.getPhoneNumber())) {
            agent.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());
        }
        agent.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());

        agentRepository.save(agent);
        return modelMapper.map(agent.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
    }


}


