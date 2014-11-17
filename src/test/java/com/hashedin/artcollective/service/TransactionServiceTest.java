package com.hashedin.artcollective.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.repository.TransactionRepository;
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class TransactionServiceTest extends BaseUnitTest{
	
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Autowired
	private TransactionsService transactionService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	@Before
	public void setup() {
		
		synchronizeSetup.artworksSynchronizeSetup();
		synchronizeSetup.ordersSynchronizeSetup();
	}
	
	@Test
	public void testForCSVBulkImportOfTransactions() throws IOException {
		Reader csvReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("transactions.csv"));
		List<String> errors = transactionService.saveTransactionsInBulk(csvReader);
		csvReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("transactions.csv"));
		errors.addAll(transactionService.saveTransactionsInBulk(csvReader));
		assertEquals(errors.size(), 9);
		assertEquals(transactionRepository.count(), 4);
	}

}
