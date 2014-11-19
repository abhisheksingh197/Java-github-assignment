package com.hashedin.artcollective.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hashedin.artcollective.entity.Lead;
@RepositoryRestResource(collectionResourceRel = "leads", path = "leads")
public interface LeadRepository extends PagingAndSortingRepository<Lead, Long> {
	
	@Query("SELECT leads FROM Lead leads ORDER BY leads.createdAt desc")
	public List<Lead> getLeadsOrderByCreatedAt(Pageable page);

}
