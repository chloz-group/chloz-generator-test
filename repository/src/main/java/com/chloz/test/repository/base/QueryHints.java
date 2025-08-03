package com.chloz.test.repository.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

final class QueryHints {

	private Map<String, Object> hints;
	private QueryHints() {
		hints = new HashMap<>();
	}

	public static QueryHints of() {
		QueryHints val = new QueryHints();
		return val;
	}

	public static QueryHints of(String name, Object value) {
		QueryHints val = new QueryHints();
		val.hints.put(name, value);
		return val;
	}

	public QueryHints setHint(String name, Object value) {
		this.hints.put(name, value);
		return this;
	}

	public void forEach(BiConsumer<String, Object> action) {
		Set<Map.Entry<String, Object>> entries = hints.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			action.accept(entry.getKey(), entry.getValue());
		}
	}

}