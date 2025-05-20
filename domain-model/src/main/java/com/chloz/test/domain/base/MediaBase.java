package com.chloz.test.domain.base;

import com.chloz.test.domain.AbstractAuditingEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class MediaBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "name", length = 100)
	protected String name;

	@Column(name = "path", length = 255)
	protected String path;

	@Column(name = "content_type", length = 100)
	protected String contentType;

	@Column(name = "key_", length = 255)
	protected String key;

}