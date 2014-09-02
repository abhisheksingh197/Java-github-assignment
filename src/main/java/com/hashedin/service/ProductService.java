package com.hashedin.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

	public List<PersonData> getProductsSinceLastModified(Date lastModified);
}
