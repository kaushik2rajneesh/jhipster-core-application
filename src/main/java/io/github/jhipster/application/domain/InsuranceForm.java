package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InsuranceForm.
 */
@Entity
@Table(name = "insurance_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "insuranceform")
public class InsuranceForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "policies_category", nullable = false)
    private Integer policiesCategory;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "pincode", nullable = false)
    private Integer pincode;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @NotNull
    @Column(name = "email_id", nullable = false)
    private String emailId;

    @NotNull
    @Column(name = "application_gen_key", nullable = false)
    private Integer applicationGenKey;

    @OneToOne
    @JoinColumn(unique = true)
    private PolicyInformation policyInfoGenKey;

    @OneToMany(mappedBy = "insuranceForm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoliciesCategory() {
        return policiesCategory;
    }

    public InsuranceForm policiesCategory(Integer policiesCategory) {
        this.policiesCategory = policiesCategory;
        return this;
    }

    public void setPoliciesCategory(Integer policiesCategory) {
        this.policiesCategory = policiesCategory;
    }

    public String getFirstName() {
        return firstName;
    }

    public InsuranceForm firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public InsuranceForm middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public InsuranceForm lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public InsuranceForm address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public InsuranceForm city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPincode() {
        return pincode;
    }

    public InsuranceForm pincode(Integer pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public InsuranceForm phoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public InsuranceForm emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getApplicationGenKey() {
        return applicationGenKey;
    }

    public InsuranceForm applicationGenKey(Integer applicationGenKey) {
        this.applicationGenKey = applicationGenKey;
        return this;
    }

    public void setApplicationGenKey(Integer applicationGenKey) {
        this.applicationGenKey = applicationGenKey;
    }

    public PolicyInformation getPolicyInfoGenKey() {
        return policyInfoGenKey;
    }

    public InsuranceForm policyInfoGenKey(PolicyInformation policyInformation) {
        this.policyInfoGenKey = policyInformation;
        return this;
    }

    public void setPolicyInfoGenKey(PolicyInformation policyInformation) {
        this.policyInfoGenKey = policyInformation;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public InsuranceForm documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public InsuranceForm addDocument(Document document) {
        this.documents.add(document);
        document.setInsuranceForm(this);
        return this;
    }

    public InsuranceForm removeDocument(Document document) {
        this.documents.remove(document);
        document.setInsuranceForm(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InsuranceForm)) {
            return false;
        }
        return id != null && id.equals(((InsuranceForm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InsuranceForm{" +
            "id=" + getId() +
            ", policiesCategory=" + getPoliciesCategory() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", pincode=" + getPincode() +
            ", phoneNumber=" + getPhoneNumber() +
            ", emailId='" + getEmailId() + "'" +
            ", applicationGenKey=" + getApplicationGenKey() +
            "}";
    }
}
