package com.chloz.test.domain.base;

import com.chloz.test.domain.AbstractAuditingEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "code", length = 100, nullable = false)
	private String code;

	@NotNull
	@Size(min = 5, max = 500)
	@Column(name = "content", nullable = false)
	@Lob
	private String content;

	/**
	 * Title of the template
	 */
	@Size(min = 1, max = 255)
	@Column(name = "title", length = 255)
	private String title;

	/**
	 * Short content to use for SMS for example
	 */
	@Size(min = 1, max = 255)
	@Column(name = "short_content", length = 255)
	private String shortContent;

}