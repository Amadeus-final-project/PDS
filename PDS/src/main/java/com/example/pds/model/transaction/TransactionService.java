package com.example.pds.model.transaction;

import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<TransactionResponseDTO> getTransactions(Pageable page) {
        List<TransactionResponseDTO> responseTransactions = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAll(page).getContent();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        for (Transaction transaction1 : transactions) {
            responseTransactions.add(modelMapper.map(transaction1, TransactionResponseDTO.class));
        }
        return responseTransactions;
    }

    public TransactionResponseDTO getTransactionById(int id) {

        if (transactionRepository.getTransactionById(id) == null) {
            throw new NotFoundException("Package does not exist");
        }
        Transaction transaction = transactionRepository.getTransactionById(id);
        return modelMapper.map(transaction, TransactionResponseDTO.class);
    }

    public List<TransactionResponseDTO> getTransactionByUsername(String username) {
        List<Transaction> transactions = transactionRepository.findAllByPayerUsername(username);
        List<TransactionResponseDTO> transactionsDto = new LinkedList<>();
        for (Transaction transaction : transactions) {
            transactionsDto.add(modelMapper.map(transaction,TransactionResponseDTO.class));
        }
        return transactionsDto;
    }
}

