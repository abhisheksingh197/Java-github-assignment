package com.hashedin.artcollective.repository;

import java.util.List;
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

	@Query(value = "select * from art_work art"
			+ " where exists "
			+ "(   select 'x' from art_work_styles" 
			+ "    where art_work_id = art.id and (styles_id in (:styleList) or -1 in (:styleList)))"
			+ " and exists "
			+ "(   select 'x' from art_work_subjects" 
			+ "    where art_work_id = art.id and (subjects_id in (:subjectList) or -1 in (:subjectList)))"
			+ " and exists "
			+ "(   select 'x' from art_work_price_buckets" 
			+ "    where art_work_id = art.id and (price_buckets_id in (:priceBucketRangeList) or -1 in "
			+ "(:priceBucketRangeList)))"
			+ "and (art.medium in (:medium) or '-1' in (:medium))"
			+ "and (art.orientation in (:orientation) or '-1' in (:orientation))"
			+ "and (art.id in (:idList) or -1 in (:idList))" ,
			nativeQuery = true)
	public List<ArtWork> findByCriteria(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation,
			@Param("idList") List<Long> idList);

}
