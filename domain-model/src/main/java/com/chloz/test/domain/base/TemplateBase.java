package com.chloz.test.domain.base;

import com.querydsl.core.annotations.QueryInit;
import com.chloz.test.domain.AbstractAuditingEntity;
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
public class TemplateBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "code", length = 100, unique = true)
	protected String code;

	@Column(name = "content")
	@Lob
	protected String content;

	@Column(name = "title", length = 255)
	protected String title;

	@Column(name = "short_content", length = 255)
	protected String shortContent;

}