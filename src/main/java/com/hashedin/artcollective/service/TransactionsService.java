package com.hashedin.artcollective.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.Transaction;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.TransactionRepository;


@Service
public class TransactionsService {
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd-MM-yyyy");
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ArtistRepository artistRepository;
	
	public void addNewTransaction(DateTime createdAt, Long artistId, String remarks, Double amount) {
		Transaction transaction = new Transaction(createdAt, artistId, remarks, amount);
		transactionRepository.save(transaction);
	}
	
	/**
	 * Parse the InputSTream as a CSV file
	 * Save each line of the CSV as a Transaction
	 * Columns : 
	 * Date, Artist Handle, Remarks, Amount
	 * 
	 * @param csv
	 * @throws IOException 
	 */
	public List<String> saveTransactionsInBulk(Reader csvReader) throws IOException {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		List<String> errors = new ArrayList<>();
		parseTransactions(csvReader, transactions, errors);
		List<Transaction> toAdd = new ArrayList<>();
		List<Transaction> pastTransactions = transactionRepository.getTransactionsSince(
				new DateTime().minusMonths(2));
		
		Map<String, Transaction> pastTransactionsById = getTransactionsByUniqueId(pastTransactions);
		for (Transaction t : transactions) {
			if (wasTransactionAddedPreviously(pastTransactionsById, t)) {
				errors.add("Transaction " + t.getCreatedAt().toString() + t.getArtistId() 
						+ t.getAmount() + " is a duplicate and will be ignored");
			}
			else {
				for (String uniqueId : getTransactionUniqueKeys(t)) {
					pastTransactionsById.put(uniqueId, t);
				}
				toAdd.add(t);
			}
		}
		
		transactionRepository.save(toAdd);
		
		return errors;
	}
	
	private Map<String, Transaction> getTransactionsByUniqueId(List<Transaction> allTransactions) {
		Map<String, Transaction> pastTransactionIDs = new HashMap<>();
		for (Transaction t : allTransactions) {
			for (String uniqueId : getTransactionUniqueKeys(t)) {
				pastTransactionIDs.put(uniqueId, t);
			}
		}
		
		return pastTransactionIDs;
	}

	private boolean wasTransactionAddedPreviously(Map<String, Transaction> pastTransactions, 
			Transaction newTransaction) {
		for (String uniqueId : getTransactionUniqueKeys(newTransaction)) {
			if (pastTransactions.containsKey(uniqueId)) {
				return true;
			}
		}
		return false;
	}
	
	private List<String> getTransactionUniqueKeys(Transaction t) {
		String byArtistAndRemarks = t.getArtistId() + t.getRemarks();
		String byArtistAndAmountOnADay = t.getArtistId() + String.valueOf(t.getAmount()) 
				+ t.getCreatedAt().getDayOfYear();
		
		return Arrays.asList(byArtistAndAmountOnADay, byArtistAndRemarks);
	}

	private Map<String, Artist> getAllArtistsByUniqueId() {
		Map<String, Artist> artistByUniqueId = new HashMap<>();
		
		for (Artist artist : artistRepository.findAll()) {
			List<String> artistIds = getArtistIds(artist);
			for (String artistId : artistIds) {
				artistByUniqueId.put(artistId, artist);
			}
		}
		return artistByUniqueId;
	}
	
	private List<String> getArtistIds(Artist artist) {
		String name = artist.getFirstName() + artist.getLastName();
		name = name.toLowerCase().replaceAll("[^A-Za-z0-9]", "");
		
		String handle = artist.getHandle().toLowerCase().replaceAll("[^A-Za-z0-9]", "");
		String userName = artist.getUsername();
		
		return Arrays.asList(name, userName, handle);
	}

	private void parseTransactions(Reader csvReader, List<Transaction> transactions, 
			List<String> errors) throws IOException {
		
		Map<String, Artist> artistByUniqueId = getAllArtistsByUniqueId();
		CSVParser parser = null;
		String dateAsString = "";
		String artistHandle = "";
		String amountAsString = "";
		String remarks = "";
		try {
			parser = new CSVParser(csvReader, CSVFormat.EXCEL.withHeader());
			
			for (CSVRecord record : parser) {
				try {
					dateAsString = record.get("Date");
					artistHandle = record.get("Artist").toLowerCase()
							.replaceAll("[^A-Za-z0-9]", "");
					amountAsString = record.get("Amount");
					remarks = record.get("Remarks");
					
					
					Artist artist = artistByUniqueId.get(artistHandle);
					if (artist == null) {
						errors.add("Can't process row " + dateAsString + artistHandle 
								+ amountAsString + remarks + "Invalid Artist");
					}
					else {
						DateTime createdAt = DATE_FORMAT.parseDateTime(dateAsString);
						Long artistId = artist.getId();
						Double amount = Double.valueOf(amountAsString);
						Transaction transaction = new Transaction(createdAt, artistId, 
								remarks, amount);
						transactions.add(transaction);
					}
				}
				
				catch (NumberFormatException e) {
					errors.add("Can't process row has invalid Amount" + dateAsString + artistHandle 
							+ amountAsString + remarks);
					continue;
				}
				
				catch (IllegalArgumentException illegalArgument) {
					errors.add("Can't process row has invalid Date" + dateAsString + artistHandle 
							+ amountAsString + remarks);
					continue;
				}
			}
		}
		finally {
			if (parser != null) {
				parser.close();
			}
		}
	}
	
}