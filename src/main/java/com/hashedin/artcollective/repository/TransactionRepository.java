package com.hashedin.artcollective.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	
	@Query("SELECT SUM(amount) FROM Transaction transactions WHERE "
			+ "transactions.artistId = :artistId")
	public Double getSumOfTransactionsByArtist(@Param("artistId")Long artistId);
	
	@Query("SELECT transactions FROM Transaction transactions WHERE "
			+ "transactions.artistId = :artistId order by transactions.createdAt desc")
	public List<Transaction> getTransactionsByArtist(@Param("artistId")Long artistId);
	
	@Query("SELECT t from Transaction t WHERE t.createdAt > :since")
	public List<Transaction> getTransactionsSince(@Param("since") DateTime since);



}
