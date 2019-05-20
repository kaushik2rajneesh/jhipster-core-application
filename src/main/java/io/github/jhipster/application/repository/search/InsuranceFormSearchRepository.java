package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.InsuranceForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InsuranceForm} entity.
 */
public interface InsuranceFormSearchRepository extends ElasticsearchRepository<InsuranceForm, Long> {
}
