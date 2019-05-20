package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Content;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Content} entity.
 */
public interface ContentSearchRepository extends ElasticsearchRepository<Content, Long> {
}
