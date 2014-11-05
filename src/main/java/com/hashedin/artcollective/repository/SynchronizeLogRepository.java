package com.hashedin.artcollective.repository;


import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hashedin.artcollective.entity.SynchronizeLog;

public interface SynchronizeLogRepository extends CrudRepository<SynchronizeLog, Long> {
	
	@Query("SELECT MAX(log.synchronizeCompletedAt) FROM SynchronizeLog log WHERE "
			+ "LOWER(log.synchronizeType) = LOWER(:type)")
		public DateTime getLastSynchronizeDate(@Param("type")String type);

}
