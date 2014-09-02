package com.hashedin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hashedin.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findByLastName(String lastName); 
}
