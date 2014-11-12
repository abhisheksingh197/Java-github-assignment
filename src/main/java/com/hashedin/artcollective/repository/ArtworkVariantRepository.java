package com.hashedin.artcollective.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.ArtworkVariant;

public interface ArtworkVariantRepository extends CrudRepository<ArtworkVariant, Long> {
	
	@Query("SELECT variant.earning FROM ArtworkVariant variant WHERE "
			+ "variant.id = :variantId")
		public Double getEarningForVariant(@Param("variantId")Long variantId);
}
