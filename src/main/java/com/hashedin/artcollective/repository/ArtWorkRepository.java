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
			+ " WHERE (((subject.title) in (:subjectList)) "
			+ "AND ((style.title) in (:styleList))"
			+ "AND ((collection.title) in (:collectionList))"
			+ "AND ((priceBucket.title) in (:priceBucketRangeList))"
			+ "AND (str(art.medium) LIKE:medium)"
			+ "AND (str(art.orientation) LIKE:orientation)"
			+ ")")
	public List<ArtWork> findByCriteria(@Param("subjectList") List<String> subjectList,
			@Param("styleList") List<String> styleList,
			@Param("collectionList") List<String> collectionList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList, 
			@Param("medium") String medium, 
			@Param("orientation") String orientation, 
			Pageable pageable);
}
