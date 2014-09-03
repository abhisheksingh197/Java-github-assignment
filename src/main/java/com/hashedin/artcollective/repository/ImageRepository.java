package com.hashedin.artcollective.repository;


import org.springframework.data.repository.CrudRepository;

import com.hashedin.artcollective.entity.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

}
