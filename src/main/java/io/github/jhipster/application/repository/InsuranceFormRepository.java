package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.InsuranceForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InsuranceForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuranceFormRepository extends JpaRepository<InsuranceForm, Long> {

}
