package com.hashedin.artcollective.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.hashedin.artcollective.entity.FrameVariant;

public interface FrameVariantRepository extends PagingAndSortingRepository<FrameVariant, Long>, 
JpaSpecificationExecutor<FrameVariant> {

	@Query("SELECT frameVariant FROM FrameVariant frameVariant "
			+ "WHERE frameVariant.deleted = false "
			+ "AND"
			+ "(frameVariant.mountThickness = :mountThickness) "
			+ "AND (frameVariant.frameThickness = :frameThickness)"
			+ ")")
	public List<FrameVariant> findVariants(@Param("mountThickness")Double mountThickness, 
			@Param("frameThickness")Double frameThickness);
	
	/*
	 * Updating an entity, hence using Transactional and Modifying Annotations
	 */
	@Transactional
	@Modifying
	@Query("Update FrameVariant frameVariant set frameVariant.deleted = true "
			+ "WHERE frameVariant.productId = :productId")
	public void softDeleteVariantsByProductId(@Param("productId")Long productId);
}

