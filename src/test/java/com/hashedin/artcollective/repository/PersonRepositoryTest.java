package com.hashedin.artcollective.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.Person;
import com.hashedin.artcollective.repository.PersonRepository;
 
public class PersonRepositoryTest extends BaseUnitTest {

	@Autowired
	private PersonRepository repository;
	
	@Test
	public void testFindByLastName() {
		List<Person> artists = repository.findByLastName("Jain");
		assertEquals(artists.size(), 2);
		assertEquals(artists.get(0).getFirstName(), "Shilpa");
		assertEquals(artists.get(1).getFirstName(), "Anurag");
		
	}
	
	@Test
	public void createAndSearchArtist() {
		repository.save(new Person("Sripathi", "Krishnan"));
		List<Person> artists = repository.findByLastName("Krishnan");
		assertEquals(artists.size(), 1);
		assertEquals(artists.get(0).getFirstName(), "Sripathi");
		assertEquals(artists.get(0).getLastName(), "Krishnan");
		
	}

}
