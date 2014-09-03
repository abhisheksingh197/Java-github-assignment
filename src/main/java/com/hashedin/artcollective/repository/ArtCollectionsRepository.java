package com.hashedin.artcollective.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.hashedin.artcollective.entity.ArtCollection;;

public interface ArtCollectionsRepository extends PagingAndSortingRepository<ArtCollection, Long> {

}
