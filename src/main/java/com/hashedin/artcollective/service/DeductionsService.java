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

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Deduction;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.DeductionRepository;

@Service
public class DeductionsService {
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd-MM-yyyy");
	
	@Autowired
	private DeductionRepository deductionsRepository;
	
	@Autowired
	private ArtWorkRepository artworkRepository;
	
	public Deduction addNewDeduction(DateTime createdAt, String type, Long artworkId, 
			Long quantity, Double unitPrice, Double totalDeductions) {
		ArtWork artwork = artworkRepository.findOne(artworkId);
		if (artwork != null) {
			Long artistId = artwork.getArtist().getId();
			Deduction deduction = new Deduction(createdAt, type, artistId, artworkId, 
					quantity, unitPrice, totalDeductions);
			return deductionsRepository.save(deduction);
		}
		return null;
	}
	
	public List<String> saveDeductionsInBulk(Reader csvReader) throws IOException {
		
		List<Deduction> deductions = new ArrayList<Deduction>();
		List<Deduction> pastDeductions =  deductionsRepository
				.getDeductionsSince(new DateTime().minusMonths(2));
		List<String> errors = new ArrayList<>();
		List<Deduction> toAddDeductions = new ArrayList<>();
		parseDeductions(csvReader, deductions, errors);
		Map<String, Deduction> pastDeductionsById = getDeductionsWithUniqueId(pastDeductions);
		for (Deduction deduction : deductions) {
			if (wasDeductionAddedPreviously(pastDeductionsById, deduction)) {
				errors.add("Duplicate " + deduction + " is a duplicate and will be ignored");
			}
			else {
				for (String uniqueId : getUniqueKeysForDeduction(deduction)) {
					pastDeductionsById.put(uniqueId, deduction);
				}
				toAddDeductions.add(deduction);
			}
				
 		}
		
		deductionsRepository.save(toAddDeductions);
		
		return errors;
	}

	private Map<String, Deduction> getDeductionsWithUniqueId(
			List<Deduction> pastDeductions) {
		Map<String, Deduction> pastDeductionsById = new HashMap<>();
		for (Deduction deduction :pastDeductions) {
			for (String uniqueId : getUniqueKeysForDeduction(deduction)) {
				pastDeductionsById.put(uniqueId, deduction);
			}
		}
		return pastDeductionsById;
	}

	private List<String> getUniqueKeysForDeduction(Deduction deduction) {
		ArtWork artwork = artworkRepository.findOne(deduction.getArtworkId()); 
		if (artwork != null) {
			String keyByArtworkHandleTitleAndType = artwork.getHandle() + deduction.getType();
			String keyByDateAndTotalDeductionOnADay = String.valueOf(deduction.getTotalDeduction()) 
					+ deduction.getCreatedAt().dayOfYear();
			return Arrays.asList(keyByArtworkHandleTitleAndType, keyByDateAndTotalDeductionOnADay);
		}
		
		return null;
		
	}

	private boolean wasDeductionAddedPreviously(Map<String, Deduction> pastDeductionsById, Deduction deduction) {
		for (String uniqueKey : getUniqueKeysForDeduction(deduction)) {
			if (pastDeductionsById.containsKey(uniqueKey)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Parsing Deductions from CSV file and storing them if they the same deduction was not made
	 * before 2 Months
	 * @param csvReader
	 * @param deductions
	 * @param errors
	 * @throws IOException
	 */
	private void parseDeductions(Reader csvReader, List<Deduction> deductions,
			List<String> errors) throws IOException {
		
		Map<String, ArtWork> artworksByUniqueId = getAllArtworksByUniqueId();
		CSVParser parser = null;
		String dateAsString = "";
		String artworkHandle = "";
		String type = "";
		String quantityAsString = "";
		String unitPriceAsString = "";
		String totalDeductionsAsString = "";
		
		try {
			parser = new CSVParser(csvReader, CSVFormat.EXCEL.withHeader());
			for (CSVRecord record : parser) {
				dateAsString = record.get("Date");
				artworkHandle = record.get("Artwork").toLowerCase()
						.replaceAll("[^A-Za-z0-9]", "");
				type = record.get("Type");
				quantityAsString = record.get("Quantity");
				unitPriceAsString = record.get("Unit-Price");
				totalDeductionsAsString = record.get("Total-Deduction");
				
				ArtWork artwork = artworksByUniqueId.get(artworkHandle);
				if (artwork == null) {
					errors.add("Can't process row " + dateAsString + artworkHandle + type 
						+ quantityAsString + unitPriceAsString + totalDeductionsAsString 
						+ " invalid artwork");
				}
				else {
					DateTime createdAt = DATE_FORMAT.parseDateTime(dateAsString);
					Long quantity = Long.valueOf(quantityAsString);
					Double unitPrice = Double.valueOf(unitPriceAsString);
					Double totalDeductions = Double.valueOf(totalDeductionsAsString);
					Long artistId = artwork.getArtist().getId();
					Deduction deduction = new Deduction(createdAt, type, artistId, 
							artwork.getId(), quantity, unitPrice, totalDeductions);
					deductions.add(deduction);
				}
			}
		}
		finally {
			if (parser != null) {
				parser.close();
			}
		}
	}

	private Map<String, ArtWork> getAllArtworksByUniqueId() {
		Map<String, ArtWork> artworksByUniqueId = new HashMap<>();
		List<ArtWork> artowrks = (List<ArtWork>) artworkRepository.findAll();
		for (ArtWork artwork : artowrks) {
			List<String> artworkIds = getArtworkIds(artwork);
			for (String uniqueId : artworkIds) {
				artworksByUniqueId.put(uniqueId, artwork);
			}
		}
		
		return artworksByUniqueId;
	}

	private List<String> getArtworkIds(ArtWork artwork) {
		String handle = artwork.getHandle().toLowerCase().replaceAll("[^A-Za-z0-9]", "");
		String title = artwork.getTitle().toLowerCase().replaceAll("[^A-Za-z0-9]", "");
		return Arrays.asList(handle, title);
	}

}
