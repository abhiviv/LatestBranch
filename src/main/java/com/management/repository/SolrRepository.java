package com.management.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.management.entity.ImageSearch;

public interface SolrRepository extends SolrCrudRepository<ImageSearch, Long> {

	@Query(fields = { "category", "id","iname" })
	List<ImageSearch> findByCategoryStartingWith(String name);
	
	@Query(value = "idesc:?0")
	@Facet(fields = { "category", "id","iname" }, limit = 5, prefix="?1")
	FacetPage<ImageSearch> findByImageDescriptionFacetOnName(String orderDesc, String prefix, Pageable page);
	
}
