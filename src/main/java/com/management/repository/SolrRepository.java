package com.management.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.management.entity.ImageSearch;

public interface SolrRepository extends SolrCrudRepository<ImageSearch, Long> {

}
