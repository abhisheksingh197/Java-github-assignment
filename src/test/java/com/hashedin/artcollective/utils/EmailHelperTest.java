package com.hashedin.artcollective.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.Lead;

public class EmailHelperTest extends BaseUnitTest {

	@Autowired
	private EmailHelper helper;
	
	@Test
	public void sendEmailTest() {
		Lead lead = new Lead();
		lead.setSource("Original Art");
		lead.setName("Anshuman Singh");
		lead.setMessage("Can I buy original art for Amit Bhar's painting? How much does it cost?");
		
		helper.sendEmailOnNewContact(lead);
		
	}

}
