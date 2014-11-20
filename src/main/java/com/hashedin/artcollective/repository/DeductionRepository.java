package com.hashedin.artcollective.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.hashedin.artcollective.entity.Deduction;

public interface DeductionRepository extends PagingAndSortingRepository<Deduction, Long> {

	@Query("SELECT SUM(totalDeduction) FROM Deduction deductions WHERE "
			+ "deductions.artistId = :artistId")
	public Double getSumOfDeductionsByArtist(@Param("artistId")Long artistId);

	@Query("SELECT deductions FROM Deduction deductions WHERE "
			+ "deductions.artistId = :artistId order by deductions.createdAt desc")
	public List<Deduction> getDeductionsByArtist(@Param("artistId")Long artistId, Pageable page);

	@Query("SELECT deductions FROM Deduction deductions WHERE "
			+ "deductions.createdAt > :since")
	public List<Deduction> getDeductionsSince(@Param("since")DateTime since);

}
