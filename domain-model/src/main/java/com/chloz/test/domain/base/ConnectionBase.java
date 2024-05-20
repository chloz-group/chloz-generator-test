package com.chloz.test.domain.base;

import com.chloz.test.domain.User;
import com.chloz.test.domain.enums.AccountType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class ConnectionBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Size(min = 7, max = 75)
	@Column(name = "ip_address", length = 75)
	private String ipAddress;

	@Size(min = 17, max = 17)
	@Column(name = "mac_address", length = 17)
	private String macAddress;

	@NotNull
	@Column(name = "succeed", nullable = false)
	private Boolean succeed;

	@NotNull
	@Column(name = "connecion_date", nullable = false)
	private Instant connecionDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "connection_mean", nullable = false, length = 10)
	private AccountType connectionMean;

	@ManyToOne(optional = false)
	@NotNull
	private User user;

}