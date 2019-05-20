package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PolicyInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PolicyInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyInformationRepository extends JpaRepository<PolicyInformation, Long> {

}
