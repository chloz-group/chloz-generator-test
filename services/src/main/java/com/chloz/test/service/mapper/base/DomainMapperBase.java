package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.DomainGraph;
import com.chloz.test.dataaccess.GraphBuilder;
import com.chloz.test.domain.AbstractAuditingEntity;
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
	protected DomainMapperBase() {
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(), DomainMapperBase.class);
		this.modelType = (Class<T>) types[0];
		this.dtoType = (Class<DTO>) types[1];
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Generate the dto from the model
	 *
	 * @param model
	 * @param graph
	 * @return The Dto from the model
	 */
	public DTO mapToDto(T model, String graph) {
		DomainGraph domainGraph = graphBuilder.toGraph(modelType, graph);
		return map(domainGraph, model, dtoType);
	}

	/**
	 * Return the entity from the dto ID if an ID has been provided in the DTO. If
	 * not, transform the DTO to a model object.
	 *
	 * @param dto
	 * @return
	 */
	public abstract T entityFromIdOrModelFromDto(DTO dto);

	/**
	 * Update entity fields and dependencies using only the values from dto that are
	 * not null
	 * 
	 * @param entity
	 * @param dto
	 */
	public abstract void partialUpdate(T entity, DTO dto);

	/**
	 * Transform the dto to a model. When transforming, the model dependencies are
	 * fetched from the database if theirs IDs are provided in the DTO.
	 *
	 * @param dto
	 *            The DTO object
	 * @return The model from the DTO object
	 */
	public T modelFromDto(DTO dto) {
		return modelMapper.map(dto, modelType);
	}

	private <A> A map(DomainGraph domainGraph, Object model, Class<A> targetType) {
		A destination = null;
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
			List<?> list = (List) PropertyUtils.getProperty(model, targetField.getName());
			if (list != null && !list.isEmpty()) {
				List destinationList = new ArrayList<>();
				Class<?> desType = (Class) ((ParameterizedType) targetField.getGenericType())
						.getActualTypeArguments()[0];
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
			Set<?> set = (Set) PropertyUtils.getProperty(model, targetField.getName());
			if (set != null && !set.isEmpty()) {
				Set destinationList = new HashSet<>();
				Class<?> desType = (Class) ((ParameterizedType) targetField.getGenericType())
						.getActualTypeArguments()[0];
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
	private static class ClassDescriptor {

		private static Map<Class<?>, DomainMapperBase.ClassDescriptor> descriptors = new HashMap<>();

		private final List<Field> fields;

		private final Map<String, Field> fieldsMap;
		private ClassDescriptor(Class<?> clazz) {
			this.fields = FieldUtils.getAllFieldsList(clazz);
			fieldsMap = new HashMap<>();
			this.fields.forEach(field -> fieldsMap.put(field.getName(), field));
		}

		public boolean hasProperty(String prop) {
			return this.fieldsMap.containsKey(prop);
		}

		public static final synchronized DomainMapperBase.ClassDescriptor getInstance(Class<?> clazz) {
			return descriptors.computeIfAbsent(clazz, ClassDescriptor::new);
		}

	}
	protected void setCommonField(AbstractAuditingEntity target, Optional<? extends AbstractAuditingEntity> src) {
		src.ifPresent(ent -> {
			target.setCreatedBy(ent.getCreatedBy());
			target.setCreatedDate(ent.getCreatedDate());
			target.setLastModifiedBy(ent.getLastModifiedBy());
			target.setLastModifiedDate(ent.getLastModifiedDate());
		});
	}

}