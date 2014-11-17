package com.hashedin.artcollective.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hashedin.artcollective.entity.Lead;

@RepositoryRestResource(collectionResourceRel = "leads", path = "leads")
public interface LeadRepository extends PagingAndSortingRepository<Lead, Long> {

}
