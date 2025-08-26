package com.chloz.test.dataaccess.filter;

import java.util.List;

public interface IAdvancedFilter<T> {

	public UserFilter getCreatedBy();

	public UserFilter getLastModifiedBy();

	public List<T> getAnd();

	public List<T> getOr();

}