package com.chloz.test.web.mapper.base;

import com.chloz.test.domain.AbstractAuditingEntity;
import com.chloz.test.service.DomainGraph;
import com.chloz.test.service.GraphBuilder;
import lombok.Getter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class DomainMapperBase<T, DTO> {

	private final Class<T> modelType;

	private final Class<DTO> dtoType;

	private final ModelMapper modelMapper;

	@Autowired
	private GraphBuilder graphBuilder;
	public DomainMapperBase() {
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(), DomainMapperBase.class);
		this.modelType = (Class<T>) types[0];
		this.dtoType = (Class<DTO>) types[1];
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public DTO mapToDto(T model, String graph) {
		DomainGraph domainGraph = graphBuilder.toGraph(modelType, graph);
		DTO res = map(domainGraph, model, dtoType);
		return res;
	}

	public abstract T entityFromIdOrElseFromDto(DTO dto);

	public T entityFromDto(DTO dto) {
		return modelMapper.map(dto, modelType);
	}

	private <A> A map(DomainGraph domainGraph, Object model, Class<A> targetType) {
		A destination = null;
		// Deprecated code after use of Hibernate @SQLRestriction
		/*
		 * if (model instanceof AbstractAuditingEntity && ((AbstractAuditingEntity)
		 * model).isDeleted()) { return null; }
		 */
		try {
			destination = targetType.getDeclaredConstructor().newInstance();
			ClassDescriptor descriptor = ClassDescriptor.getInstance(targetType);
			A finalDestination = destination;
			domainGraph.getAttributes().stream().filter(descriptor::hasProperty)
					.forEach(att -> writeProperty(model, att, finalDestination));
			Map<String, DomainGraph> subGraphs = domainGraph.getSubGraphs();
			if (subGraphs != null) {
				subGraphs.keySet().stream().filter(descriptor::hasProperty).forEach(prop -> {
					Field targetField = descriptor.fieldsMap.get(prop);
					if (List.class.isAssignableFrom(targetField.getType())) {
						writeList(model, targetField, finalDestination, subGraphs.get(prop));
					} else if (Set.class.isAssignableFrom(targetField.getType())) {
						writeSet(model, targetField, finalDestination, subGraphs.get(prop));
					} else {
						writeObject(model, targetField, finalDestination, subGraphs.get(prop));
					}
				});
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return destination;
	}

	private <A> void writeObject(Object model, Field targetField, A target, DomainGraph subGraph) {
		try {
			Object source = PropertyUtils.getProperty(model, targetField.getName());
			if (source != null) {
				Object value = map(subGraph, source, targetField.getType());
				if (value != null)
					PropertyUtils.setProperty(target, targetField.getName(), value);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

	private <A> void writeList(Object model, Field targetField, A target, DomainGraph graph) {
		try {
			List list = (List) PropertyUtils.getProperty(model, targetField.getName());
			if (list != null && !list.isEmpty()) {
				List destinationList = new ArrayList();
				Class desType = (Class) ((ParameterizedType) targetField.getGenericType()).getActualTypeArguments()[0];
				list.forEach(src -> {
					Object dest = map(graph, src, desType);
					if (dest != null)
						destinationList.add(dest);
				});
				PropertyUtils.setProperty(target, targetField.getName(), destinationList);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

	private <A> void writeSet(Object model, Field targetField, A target, DomainGraph graph) {
		try {
			Set set = (Set) PropertyUtils.getProperty(model, targetField.getName());
			if (set != null && !set.isEmpty()) {
				Set destinationList = new HashSet<>();
				Class desType = (Class) ((ParameterizedType) targetField.getGenericType()).getActualTypeArguments()[0];
				set.forEach(src -> {
					Object dest = map(graph, src, desType);
					if (dest != null)
						destinationList.add(dest);
				});
				PropertyUtils.setProperty(target, targetField.getName(), destinationList);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

	private void writeProperty(Object source, String property, Object target) {
		try {
			Object value = PropertyUtils.getProperty(source, property);
			if (value != null)
				PropertyUtils.setProperty(target, property, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}
	@Getter
	private static class ClassDescriptor {

		private static Map<Class, DomainMapperBase.ClassDescriptor> descriptors = new HashMap<>();

		private final List<Field> fields;

		private final Map<String, Field> fieldsMap;
		private ClassDescriptor(Class clazz) {
			this.fields = FieldUtils.getAllFieldsList(clazz);
			fieldsMap = new HashMap<>();
			this.fields.forEach(field -> fieldsMap.put(field.getName(), field));
		}

		public boolean hasProperty(String prop) {
			return this.fieldsMap.containsKey(prop);
		}

		public static final synchronized DomainMapperBase.ClassDescriptor getInstance(Class clazz) {
			return descriptors.computeIfAbsent(clazz, c -> new DomainMapperBase.ClassDescriptor(c));
		}

	}

}