package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PolicyInformation.
 */
@Entity
@Table(name = "policy_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "policyinformation")
public class PolicyInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "policy_new_or_existing", nullable = false)
    private Boolean policyNewOrExisting;

    @Column(name = "policy_no_existing")
    private String policyNoExisting;

    @Column(name = "insurance_company_existing")
    private Integer insuranceCompanyExisting;

    @Column(name = "date_of_expire_existing")
    private Instant dateOfExpireExisting;

    @Column(name = "idv")
    private String idv;

    @Column(name = "ncb")
    private String ncb;

    @Column(name = "value_of_vichle")
    private Long valueOfVichle;

    @Column(name = "purchase_date")
    private Instant purchaseDate;

    @Column(name = "vichle_manuf_comp")
    private String vichleManufComp;

    @Column(name = "policy_tpa")
    private String policyTPA;

    @NotNull
    @Column(name = "policy_info_gen_key", nullable = false)
    private Integer policyInfoGenKey;

    @OneToOne(mappedBy = "policyInfoGenKey")
    @JsonIgnore
    private InsuranceForm insuranceForm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPolicyNewOrExisting() {
        return policyNewOrExisting;
    }

    public PolicyInformation policyNewOrExisting(Boolean policyNewOrExisting) {
        this.policyNewOrExisting = policyNewOrExisting;
        return this;
    }

    public void setPolicyNewOrExisting(Boolean policyNewOrExisting) {
        this.policyNewOrExisting = policyNewOrExisting;
    }

    public String getPolicyNoExisting() {
        return policyNoExisting;
    }

    public PolicyInformation policyNoExisting(String policyNoExisting) {
        this.policyNoExisting = policyNoExisting;
        return this;
    }

    public void setPolicyNoExisting(String policyNoExisting) {
        this.policyNoExisting = policyNoExisting;
    }

    public Integer getInsuranceCompanyExisting() {
        return insuranceCompanyExisting;
    }

    public PolicyInformation insuranceCompanyExisting(Integer insuranceCompanyExisting) {
        this.insuranceCompanyExisting = insuranceCompanyExisting;
        return this;
    }

    public void setInsuranceCompanyExisting(Integer insuranceCompanyExisting) {
        this.insuranceCompanyExisting = insuranceCompanyExisting;
    }

    public Instant getDateOfExpireExisting() {
        return dateOfExpireExisting;
    }

    public PolicyInformation dateOfExpireExisting(Instant dateOfExpireExisting) {
        this.dateOfExpireExisting = dateOfExpireExisting;
        return this;
    }

    public void setDateOfExpireExisting(Instant dateOfExpireExisting) {
        this.dateOfExpireExisting = dateOfExpireExisting;
    }

    public String getIdv() {
        return idv;
    }

    public PolicyInformation idv(String idv) {
        this.idv = idv;
        return this;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public String getNcb() {
        return ncb;
    }

    public PolicyInformation ncb(String ncb) {
        this.ncb = ncb;
        return this;
    }

    public void setNcb(String ncb) {
        this.ncb = ncb;
    }

    public Long getValueOfVichle() {
        return valueOfVichle;
    }

    public PolicyInformation valueOfVichle(Long valueOfVichle) {
        this.valueOfVichle = valueOfVichle;
        return this;
    }

    public void setValueOfVichle(Long valueOfVichle) {
        this.valueOfVichle = valueOfVichle;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public PolicyInformation purchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getVichleManufComp() {
        return vichleManufComp;
    }

    public PolicyInformation vichleManufComp(String vichleManufComp) {
        this.vichleManufComp = vichleManufComp;
        return this;
    }

    public void setVichleManufComp(String vichleManufComp) {
        this.vichleManufComp = vichleManufComp;
    }

    public String getPolicyTPA() {
        return policyTPA;
    }

    public PolicyInformation policyTPA(String policyTPA) {
        this.policyTPA = policyTPA;
        return this;
    }

    public void setPolicyTPA(String policyTPA) {
        this.policyTPA = policyTPA;
    }

    public Integer getPolicyInfoGenKey() {
        return policyInfoGenKey;
    }

    public PolicyInformation policyInfoGenKey(Integer policyInfoGenKey) {
        this.policyInfoGenKey = policyInfoGenKey;
        return this;
    }

    public void setPolicyInfoGenKey(Integer policyInfoGenKey) {
        this.policyInfoGenKey = policyInfoGenKey;
    }

    public InsuranceForm getInsuranceForm() {
        return insuranceForm;
    }

    public PolicyInformation insuranceForm(InsuranceForm insuranceForm) {
        this.insuranceForm = insuranceForm;
        return this;
    }

    public void setInsuranceForm(InsuranceForm insuranceForm) {
        this.insuranceForm = insuranceForm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyInformation)) {
            return false;
        }
        return id != null && id.equals(((PolicyInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PolicyInformation{" +
            "id=" + getId() +
            ", policyNewOrExisting='" + isPolicyNewOrExisting() + "'" +
            ", policyNoExisting='" + getPolicyNoExisting() + "'" +
            ", insuranceCompanyExisting=" + getInsuranceCompanyExisting() +
            ", dateOfExpireExisting='" + getDateOfExpireExisting() + "'" +
            ", idv='" + getIdv() + "'" +
            ", ncb='" + getNcb() + "'" +
            ", valueOfVichle=" + getValueOfVichle() +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", vichleManufComp='" + getVichleManufComp() + "'" +
            ", policyTPA='" + getPolicyTPA() + "'" +
            ", policyInfoGenKey=" + getPolicyInfoGenKey() +
            "}";
    }
}
