package com.chloz.test.domain;

import com.chloz.test.domain.base.VerificationCodeBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_verification_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class VerificationCode extends VerificationCodeBase {

	private static final long serialVersionUID = 1L;

}