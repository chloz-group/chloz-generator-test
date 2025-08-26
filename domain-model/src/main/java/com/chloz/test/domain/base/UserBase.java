package com.chloz.test.domain.base;

import com.querydsl.core.annotations.QueryInit;
import com.chloz.test.domain.AbstractAuditingEntity;
import com.chloz.test.domain.Media;
import com.chloz.test.domain.Role;
import com.chloz.test.domain.UserGroup;
import com.chloz.test.domain.VerificationCode;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class UserBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "login", length = 100, unique = true)
	protected String login;

	@Column(name = "password", length = 255)
	protected String password;

	@Column(name = "email", length = 254, unique = true)
	protected String email;

	@Column(name = "phone", length = 50, unique = true)
	protected String phone;

	@Column(name = "phone_checked")
	protected Boolean phoneChecked;

	@Column(name = "account_locked")
	protected Boolean accountLocked;

	@Column(name = "email_checked")
	protected Boolean emailChecked;

	@Column(name = "activated")
	protected Boolean activated;

	@Column(name = "attempts", precision = 10)
	protected Integer attempts;

	@Column(name = "first_name", length = 100)
	protected String firstName;

	@Column(name = "name", length = 100)
	protected String name;

	@Column(name = "lang", length = 10)
	protected String lang;

	@ManyToMany()
	@JoinTable(name = "test_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@QueryInit("*.*")
	protected List<Role> roles;

	@ManyToOne
	@JoinColumn(name = "picture_id")
	@QueryInit("*.*")
	protected Media picture;

	@ManyToMany(mappedBy = "users")
	@QueryInit("*.*")
	protected List<UserGroup> groups;

	@OneToMany(mappedBy = "usedFor", cascade = CascadeType.ALL, orphanRemoval = true)
	@QueryInit("*.*")
	protected List<VerificationCode> verificationCodeList;

}