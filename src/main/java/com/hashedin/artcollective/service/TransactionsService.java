package com.hashedin.artcollective.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.Transaction;
import com.hashedin.artcollective.repository.TransactionRepository;

@Service
public class TransactionsService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	public void addNewTransaction(DateTime createdAt, Long artistId, String remarks, Double amount) {
		Transaction transaction = new Transaction(createdAt, artistId, remarks, amount);
		transactionRepository.save(transaction);
	}
	
	
	
}
