package com.chloz.test.service.filter.common;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.util.List;

/**
 * Base class for the various attribute filters. It can be added to a criteria
 * class as a member, to support the following query parameters:
 * 
 * <pre>
 *      fieldName.eq='something'
 *      fieldName.neq='somethingElse'
 *      fieldName.in='something','other'
 *      fieldName.notIn='something','other'
 *      fieldName.isNull=true
 *      fieldName.isNotNull=false
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Filter<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Equals
	 */
	private T eq;

	/**
	 * Not equals
	 */
	private T neq;

	/**
	 * In
	 */
	private List<T> in;

	/**
	 * Not In
	 */
	private List<T> notIn;

	/**
	 * Is null
	 */
	private Boolean isNull;

	/**
	 * Is not null
	 */
	private Boolean isNotNull;

}