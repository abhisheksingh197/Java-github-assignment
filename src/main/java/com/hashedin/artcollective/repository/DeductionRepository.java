package com.hashedin.artcollective.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.Deduction;

public interface DeductionRepository extends CrudRepository<Deduction, Long> {

	@Query("SELECT SUM(totalDeduction) FROM Deduction deductions WHERE "
			+ "deductions.artistId = :artistId")
	public Double getSumOfDeductionsByArtist(@Param("artistId")Long artistId);

	@Query("SELECT deductions FROM Deduction deductions WHERE "
			+ "deductions.artistId = :artistId order by deductions.createdAt desc")
	public List<Deduction> getDeductionsByArtist(@Param("artistId")Long artistId);

}
