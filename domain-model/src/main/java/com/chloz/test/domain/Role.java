package com.chloz.test.domain;

import com.chloz.test.domain.base.RoleBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Role extends RoleBase {

	private static final long serialVersionUID = 1L;

}