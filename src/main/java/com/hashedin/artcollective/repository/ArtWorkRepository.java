package com.hashedin.artcollective.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.hashedin.artcollective.entity.ArtWork;


public interface ArtWorkRepository extends PagingAndSortingRepository<ArtWork, Long>, 
JpaSpecificationExecutor<ArtWork> {

	String CRITERIA_WHERE_CLAUSE = " where ( "
			+ "art.deleted = false "
			+ "AND "
			+ "exists (   select 'x' from art_work_styles" 
			+ "    where art_work_id = art.id and (styles_id in (:styleList) or -1 in (:styleList))) "
			+ "OR (not exists "
			+ "(select 'x' from art_work_styles where art_work_id = art.id) and -1 in (:styleList))"
			+ ") "
			+ " and ("
			+ "exists "
			+ "(   select 'x' from art_work_subjects" 
			+ "    where art_work_id = art.id and (subjects_id in (:subjectList) or -1 in (:subjectList))) "
			+ "or (not exists "
			+ "(select 'x' from art_work_subjects where art_work_id = art.id) and -1 in (:subjectList))"
			+ ")"
			+ " and exists "
			+ "(   select 'x' from art_work_price_buckets" 
			+ "    where art_work_id = art.id and (price_buckets_id in (:priceBucketRangeList) or -1 in "
			+ "(:priceBucketRangeList)))"
			+ " and exists "
			+ "(   select 'x' from art_work_size_buckets" 
			+ "    where art_work_id = art.id and (size_buckets_id in (:sizeBucketRangeList) or -1 in "
			+ "(:sizeBucketRangeList)))"
			+ "and (art.medium = :medium or :medium is null )"
			+ "and (art.orientation = :orientation or :orientation is null )"
			+ "and (art.id in (:idList) or -1 in (:idList)) ";
	
	String PREFERENCE_WHERE_CLAUSE = " where ( "
			+ "art.deleted = false "
			+ "AND "
			+ "exists (   select 'x' from art_work_styles" 
			+ "    where art_work_id = art.id and (styles_id in (:styleList) or -1 in (:styleList))) "
			+ "OR (not exists "
			+ "(select 'x' from art_work_styles where art_work_id = art.id) and -1 in (:styleList))"
			+ ") "
			+ " OR ("
			+ "exists "
			+ "(   select 'x' from art_work_subjects" 
			+ "    where art_work_id = art.id and (subjects_id in (:subjectList) or -1 in (:subjectList))) "
			+ "or (not exists "
			+ "(select 'x' from art_work_subjects where art_work_id = art.id) and -1 in (:subjectList))"
			+ ")"
			+ "OR (art.medium = :medium or :medium is null )"
			+ "OR (art.orientation = :orientation or :orientation is null )";
	
	@Query("SELECT art FROM ArtWork art WHERE "
		+ "LOWER(art.artist.firstName) = LOWER(:firstName)")
	public List<ArtWork> findByArtist(@Param("firstName")String firstName);
	
	//Writing a native SQL query for performance reasons. An exists sub query is a lot faster than join.
	@Query(value = "select * from art_work art"
			+ CRITERIA_WHERE_CLAUSE
			+ "order by art.created_at desc "
			+ "limit :pageLimit offset :pageOffset " ,
			nativeQuery = true)
	
	//CHECKSTYLE:OFF
	public List<ArtWork> findByCriteria(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation,
			@Param("sizeBucketRangeList") List<String> sizeBucketRangeList,
			@Param("idList") List<Long> idList,
			@Param("pageLimit") Integer pageLimit,
			@Param("pageOffset") Integer pageOffset);
	//CHECKSTYLE:ON
	
	//Replicating the query above to get a count of the search result
	@Query(value = "select count(*) from art_work art"
			+ CRITERIA_WHERE_CLAUSE,
			nativeQuery = true)
	
	public int findCountByCriteria(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("priceBucketRangeList") List<String> priceBucketRangeList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation,
			@Param("sizeBucketRangeList") List<String> sizeBucketRangeList,
			@Param("idList") List<Long> idList);
	
	@Query("SELECT art FROM ArtWork art WHERE "
			+ "(art.deleted = false) "
			+ "AND "
			+ "(art.artist.id = :artistId)")
	public List<ArtWork> getArtworksByArtist(@Param("artistId")Long artistId);

	@Query(value = "select * from art_work art"
			+ PREFERENCE_WHERE_CLAUSE
			+ "order by art.created_at desc "
			+ "limit :pageLimit offset :pageOffset " ,
			nativeQuery = true)
	public List<ArtWork> findByPreference(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation,
			@Param("pageLimit") Integer pageLimit,
			@Param("pageOffset") Integer pageOffset);
	
	@Query(value = "select count(*) from art_work art"
			+ PREFERENCE_WHERE_CLAUSE,
			nativeQuery = true)
	public int findCountByPreference(@Param("styleList") List<String> styleList,
			@Param("subjectList") List<String> subjectList,
			@Param("medium") String medium, 
			@Param("orientation") String orientation);
	
}
