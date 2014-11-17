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
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class DeductionServiceTest extends BaseUnitTest{
	
	@Autowired
	private DeductionsService deductionsService;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Before
	public void setup() {
		synchronizeSetup.artworksSynchronizeSetup();
		synchronizeSetup.ordersSynchronizeSetup();
	}
	
	@Test
	public void testForCSVBulkImportOfDeductions() throws IOException {
		Reader csvReader = new InputStreamReader(this.getClass().getClassLoader()
				.getResourceAsStream("deductions.csv"));
		List<String> errors = deductionsService.saveDeductionsInBulk(csvReader);
		assertEquals(errors.size(), 3);
		csvReader = new InputStreamReader(this.getClass().getClassLoader()
				.getResourceAsStream("deductions_duplicate.csv"));
		errors = deductionsService.saveDeductionsInBulk(csvReader);
		assertEquals(errors.size(), 0);
	}

}
