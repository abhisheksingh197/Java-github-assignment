package com.hashedin.artcollective.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.hashedin.artcollective.entity.ArtWork;


public interface ArtWorkRepository extends PagingAndSortingRepository<ArtWork, Long>, 
JpaSpecificationExecutor<ArtWork> {

	@Query("SELECT art FROM ArtWork art WHERE "
		+ "LOWER(art.artist.firstName) = LOWER(:firstName)")
	public List<ArtWork> findByArtist(@Param("firstName")String firstName);
	
	@Query("SELECT art FROM ArtWork art INNER JOIN art.subjects subject "
			+ "INNER JOIN art.styles style "
			+ "INNER JOIN art.collections collection "
			+ "INNER JOIN art.priceBuckets priceBucket "
			+ " WHERE (((:subjectList) is NULL OR (subject.title) in (:subjectList)) "
			+ "AND ((:styleList) is NULL OR (style.title) in (:styleList))"
			+ "AND ((:collectionList) is NULL OR (collection.title) in (:collectionList))"
			+ "AND ((:priceBucketRangeList) is NULL OR (priceBucket.title) in (:priceBucketRangeList))"
			+ "AND (:medium is NULL OR art.medium = :medium)"
			+ "AND (:orientation is NULL OR art.orientation = :orientation)"
			+ ")")
	public List<ArtWork> findByCriteria(@Param("subjectList") List<String> subjectList,
			@Param("styleList") List<String> styleList,
			@Param("collectionList") List<String> collectionList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList, 
			@Param("medium") String medium, 
			@Param("orientation") String orientation, 
			Pageable pageable);
}
