package com.chloz.test.domain;

import com.chloz.test.domain.base.ConnectionBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_connection")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Connection extends ConnectionBase {

	private static final long serialVersionUID = 1L;

}