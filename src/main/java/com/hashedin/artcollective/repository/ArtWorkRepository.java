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
	
	//Writing a native SQL query for performance reasons. An exists sub query is a lot faster than join.
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
			+ "and (art.medium = :medium or :medium is null )"
			+ "and (art.orientation = :orientation or :orientation is null )"
			+ "and (art.id in (:idList) or -1 in (:idList)) order by art.created_at asc "
			+ "limit :pageLimit offset :pageOffset " ,
			nativeQuery = true)
	
	//CHECKSTYLE:OFF
	public List<ArtWork> findByCriteria(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation,
			@Param("idList") List<Long> idList,
			@Param("pageLimit") Integer pageLimit,
			@Param("pageOffset") Integer pageOffset);
	//CHECKSTYLE:ON
	
	//Replicating the query above to get a count of the search result
	@Query(value = "select count(*) from art_work art"
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
			+ "and (art.medium = :medium or :medium is null )"
			+ "and (art.orientation = :orientation or :orientation is null )"
			+ "and (art.id in (:idList) or -1 in (:idList))",
			nativeQuery = true)
	
	public int findCountByCriteria(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation,
			@Param("idList") List<Long> idList);
	
}
