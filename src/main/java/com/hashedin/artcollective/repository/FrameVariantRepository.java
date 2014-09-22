package com.hashedin.artcollective.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.FrameVariant;

public interface FrameVariantRepository extends PagingAndSortingRepository<FrameVariant, Long>, 
JpaSpecificationExecutor<FrameVariant> {

	@Query("SELECT frameVariant FROM FrameVariant frameVariant "
			+ " WHERE ((frameVariant.frameLength = :frameLength)"
			+ "AND (frameVariant.frameBreadth = :frameBreadth)"
			+ "AND (frameVariant.mountThickness = :mountThickness)"
			+ "AND (frameVariant.frameThickness = :frameThickness)"
			+ ")")
	public List<FrameVariant> findVariants(@Param("frameLength")Double frameLength, 
			@Param("frameBreadth")Double frameBreadth,
			@Param("mountThickness")Double mountThickness, 
			@Param("frameThickness")Double frameThickness);
}