package com.hashedin.artcollective.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
	
	@Query("SELECT artist FROM Artist artist WHERE "
			+ "artist.collectionId = LOWER(:collectionId)")
	public Artist findArtistByCollectionID(@Param("collectionId")Long collectionId);

	@Query("SELECT artist FROM Artist artist WHERE "
			+ "artist.username = LOWER(:username)")
	public Artist findByUsername(@Param("username") String username);
}
