package com.example.pds.controllers;

import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.model.transaction.TransactionService;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(HttpServletRequest request, Pageable page) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<TransactionResponseDTO> transactions = transactionService.getTransactions(isAdmin, isAgent, isLogged, page);
        return transactions;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionResponseDTO getTransaction(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        TransactionResponseDTO transaction = transactionService.getTransactionById(id, isAdmin, isAgent, isLogged);
        return transaction;
    }

    @GetMapping("/createTransaction")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionResponseDTO createTransaction (TransactionResponseDTO transactionResponseDTO, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        TransactionResponseDTO transaction = transactionService.createTransaction(isAdmin, isLogged, transactionResponseDTO);
        return transaction;
    }

    @GetMapping("/deleteTransaction")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionResponseDTO deleteTransaction (int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        TransactionResponseDTO transaction = transactionService.deleteTransaction(isAdmin, isLogged, id);
        return transaction;
    }
}
