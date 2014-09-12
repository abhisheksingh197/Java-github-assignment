package com.hashedin.artcollective.repository;


import org.springframework.data.repository.CrudRepository;

import com.hashedin.artcollective.entity.PriceBucket;

public interface PriceBucketRepository extends CrudRepository<PriceBucket, Long> {

}
