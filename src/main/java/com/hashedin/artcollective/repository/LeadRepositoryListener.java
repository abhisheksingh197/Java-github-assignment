package com.hashedin.artcollective.repository;


import javax.persistence.PostPersist;

import com.hashedin.artcollective.entity.Lead;
import com.hashedin.artcollective.utils.EmailHelper;

public class LeadRepositoryListener {
	
	@PostPersist
	public void postPersist(Lead lead) {
		EmailHelper.sendEmailOnNewContact(lead);
	}
	
	
}