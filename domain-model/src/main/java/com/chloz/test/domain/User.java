package com.chloz.test.domain;

import com.chloz.test.domain.base.UserBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.SQLDelete;
import jakarta.persistence.*;

@Entity
@Table(name = "test_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE test_user SET deleted = true WHERE id = ?")
public class User extends UserBase {

	private static final long serialVersionUID = 1L;

}