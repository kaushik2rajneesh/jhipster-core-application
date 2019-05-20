package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.PolicyInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PolicyInformation} entity.
 */
public interface PolicyInformationSearchRepository extends ElasticsearchRepository<PolicyInformation, Long> {
}
