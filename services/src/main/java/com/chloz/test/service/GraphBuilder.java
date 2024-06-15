package com.chloz.test.service;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Component
public class GraphBuilder {

    private static final List<String> ALL_SIMPLE_ATTRIBUTES = List.of("*", "_");
    private static final List<String> ALL_SIMPLE_AND_CHILD_ATTRIBUTES = List.of("**", "__");

    @Autowired
    private EntityManager entityManager;
    private Map<String, EntityGraph> entityGraphCache = new HashMap<>(1000);
    private Map<String, DomainGraph> domainGraphCache = new HashMap<>(1000);

    /**
     * Create the entityGraph according to the graph text provided
     *
     * @param clazz
     * @param graph
     * @param <T>   the domain class
     * @return
     */
    public <T> EntityGraph<T> createEntityGraph(Class<T> clazz, String graph) {
        String cacheKey = this.getCacheKey(clazz, graph);
        if (entityGraphCache.containsKey(cacheKey)) {
            return entityGraphCache.get(cacheKey);
        }
        DomainGraph g = this.toGraph(clazz, graph);
        EntityGraph<T> entityGraph = null;
        if (g != null) {
            entityGraph = this.entityManager.createEntityGraph(clazz);
            List<String> attributes = g.getAttributes();
            if (attributes != null) {
                for (String at : attributes) {
                    entityGraph.addAttributeNodes(at);
                }
            }
            Map<String, DomainGraph> subGraphs = g.getSubGraphs();
            if (subGraphs != null) {
                Set<Map.Entry<String, DomainGraph>> sets = subGraphs.entrySet();
                for (Map.Entry<String, DomainGraph> entry : sets) {
                    Subgraph<?> sg = entityGraph.addSubgraph(entry.getKey());
                    addSubGraph(sg, entry.getValue());
                }
            }
        }
        entityGraphCache.put(cacheKey, entityGraph);
        return entityGraph;
    }

    private <T> void addSubGraph(Subgraph<T> subGraph, DomainGraph graph) {
        List<String> attributes = graph.getAttributes();
        if (attributes != null) {
            for (String at : attributes) {
                subGraph.addAttributeNodes(at);
            }
        }
        Map<String, DomainGraph> subGraphs = graph.getSubGraphs();
        if (subGraphs != null) {
            Set<Map.Entry<String, DomainGraph>> sets = subGraphs.entrySet();
            for (Map.Entry<String, DomainGraph> entry : sets) {
                Subgraph<?> sg = subGraph.addSubgraph(entry.getKey());
                addSubGraph(sg, entry.getValue());
            }
        }
    }

    public DomainGraph toGraph(Class clazz, String graph) {
        String cacheKey = getCacheKey(clazz, graph);
        if (domainGraphCache.containsKey(cacheKey)) {
            return domainGraphCache.get(cacheKey);
        }
        final List<String> attributes = new ArrayList<>();
        final Map<String, DomainGraph> subGraphs = new HashMap<>();
        ClassDescriptor descriptor = ClassDescriptor.getInstance(clazz);
        if (StringUtils.isBlank(graph)) {
            descriptor.getIdFields().stream().forEach(field -> attributes.add(field.getName()));
        } else {
            // parse the string to get the attribute list
            List<String> list = new ArrayList<>(Arrays.asList(graph.split(",")));
            List<Field> simpleFields = descriptor.getSimpleFields();
            List<Field> entitiesFields = descriptor.getEntitiesFields();
            if (list.stream().anyMatch(s -> ALL_SIMPLE_AND_CHILD_ATTRIBUTES.contains(s))) {
                //we add all simple attributes
                list.add(ALL_SIMPLE_ATTRIBUTES.get(0));
                //we add all the child attributes that are not collections
                for (Field field : entitiesFields) {
                    if (!Collection.class.isAssignableFrom(field.getType())) {
                        list.add(field.getName());
                    }
                }
            }
            if (list.stream().anyMatch(s -> ALL_SIMPLE_ATTRIBUTES.contains(s))) {
                simpleFields.stream().map(Field::getName).forEach(attributes::add);
            } else {
                // simple fields
                list.stream().filter(s -> !s.contains("."))// extract attributes without dot(.)
                        .filter(s -> entitiesFields.stream().noneMatch(field -> field.getName().equals(s))) // filter
                        // attributes
                        // that are
                        // entities
                        .filter(s -> {
                            if (simpleFields.stream().noneMatch(field -> field.getName().equals(s))) {
                                throw new IllegalArgumentException("Unknown field " + s + " provided in the graph");
                            }
                            return true;
                        }).forEach(attributes::add);
            }
            // Get the subGraph
            list.stream()
                    .filter(s -> s.contains(".")
                            || entitiesFields.stream().anyMatch(field -> field.getName().equals(s)))
                    .map(s -> s.indexOf(".") < 0 ? s : s.substring(0, s.indexOf("."))).distinct().filter(s -> {
                        if (entitiesFields.stream().noneMatch(field -> field.getName().equals(s))) {
                            throw new IllegalArgumentException("Unknown subGraph " + s + " requested");
                        }
                        return true;
                    }).forEach(fieldName -> {
                        List<String> subList = list.stream().filter(s1 -> s1.startsWith(fieldName + "."))
                                .map(s -> s.substring(fieldName.length() + 1)).toList();
                        Field f = entitiesFields.stream().filter(field -> field.getName().equals(fieldName)).findFirst()
                                .get();
                        Class type = Collection.class.isAssignableFrom(f.getType())
                                ? (Class) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]
                                : f.getType();
                        DomainGraph g = toGraph(type, StringUtils.join(subList, ","));
                        if (g != null) {
                            subGraphs.put(fieldName, g);
                        }
                    });
        }
        DomainGraph res = null;
        if (!attributes.isEmpty() || !subGraphs.isEmpty()) {
            res = new DomainGraph(attributes, subGraphs);
        }
        domainGraphCache.put(cacheKey, res);
        return res;
    }

    private String getCacheKey(Class clazz, String graph) {
        String cacheKey = clazz.getName();
        if (graph != null && !graph.trim().isEmpty()) {
            List<String> list = new ArrayList<>(Arrays.asList(graph.split(",")));
            list.sort(String::compareTo);
            cacheKey = cacheKey + "@" + StringUtils.join(list);
        }
        return cacheKey;
    }

    @Getter
    private static class ClassDescriptor {

        private static Map<Class, ClassDescriptor> descriptors = new HashMap<>();

        private final List<Field> idFields;

        private final List<Field> entitiesFields;

        private final List<Field> simpleFields;

        private ClassDescriptor(Class clazz) {
            this.idFields = new ArrayList<>();
            this.entitiesFields = new ArrayList<>();
            this.simpleFields = new ArrayList<>();
            List<Field> fields = FieldUtils.getAllFieldsList(clazz);
            fields.stream().filter(field -> isClassField(field)).forEach(field -> {
                if (isEntity(field)) {
                    entitiesFields.add(field);
                } else if (isId(field)) {
                    idFields.add(field);
                    simpleFields.add(field);
                } else {
                    simpleFields.add(field);
                }
            });
        }

        private boolean isClassField(Field field) {
            int modifiers = field.getModifiers();
            return !(Modifier.isStatic(modifiers) || Modifier.isVolatile(modifiers) || Modifier.isTransient(modifiers));
        }

        private boolean isId(Field field) {
            return field.getAnnotation(Id.class) != null;
        }

        private boolean isEntity(Field field) {
            Class type = Collection.class.isAssignableFrom(field.getType())
                    ? (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]
                    : field.getType();
            return type.getAnnotation(Entity.class) != null;
        }

        public static final synchronized ClassDescriptor getInstance(Class clazz) {
            return descriptors.computeIfAbsent(clazz, c -> new ClassDescriptor(c));
        }

    }

}