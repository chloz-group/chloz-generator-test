package com.chloz.test.service;

import com.chloz.test.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.persistence.AttributeNode;
import jakarta.persistence.EntityGraph;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ServiceConfigurationTest.class)
class GraphBuilderTest {

	@Autowired
	private GraphBuilder builder;
	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void createNullEntityGraph() {
		EntityGraph<User> graph = builder.createEntityGraph(User.class, null);
		assertEquals(1, graph.getAttributeNodes().size());
		assertEquals("id", graph.getAttributeNodes().get(0).getAttributeName());
	}

	@Test
	void createEntityGraphWithUnkownProperty() {
		assertThrows(IllegalArgumentException.class,
				() -> builder.createEntityGraph(User.class, "aSampleProperty,anotherOne"));
		assertThrows(IllegalArgumentException.class,
				() -> builder.createEntityGraph(User.class, "unknownChild.id,unknownChild.name"));
	}

	@Test
	void createEmptyEntityGraph() {
		EntityGraph<User> graph = builder.createEntityGraph(User.class, "");
		assertEquals(1, graph.getAttributeNodes().size());
		assertEquals("id", graph.getAttributeNodes().get(0).getAttributeName());
	}

	@Test
	void createSimpleFieldsEntityGraph() {
		final EntityGraph<User> graph = builder.createEntityGraph(User.class, "login,password,createdDate,disabled");
		assertEquals(4, graph.getAttributeNodes().size());
		assertTrue(List.of("login", "password", "createdDate", "disabled").stream()
				.allMatch(s -> graph.getAttributeNodes().stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// no subgraph exists
		assertTrue(graph.getAttributeNodes().stream().allMatch(
				attributeNode -> attributeNode.getSubgraphs() == null || attributeNode.getSubgraphs().isEmpty()));
	}

	@Test
	void createAllSimpleFieldsEntityGraph() {
		final EntityGraph<User> graph = builder.createEntityGraph(User.class, "_");
		assertTrue(graph.getAttributeNodes().size() >= 7);
		assertTrue(List.of("id", "login", "password", "createdDate", "disabled", "lastModifiedDate", "deleted").stream()
				.allMatch(s -> graph.getAttributeNodes().stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// no subgraph exists
		assertTrue(graph.getAttributeNodes().stream().allMatch(
				attributeNode -> attributeNode.getSubgraphs() == null || attributeNode.getSubgraphs().isEmpty()));
		EntityGraph<User> graph2 = builder.createEntityGraph(User.class, "*");
		assertTrue(graph2.getAttributeNodes().size() >= 7);
		assertTrue(List.of("id", "login", "password", "createdDate", "disabled", "lastModifiedDate", "deleted").stream()
				.allMatch(s -> graph2.getAttributeNodes().stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// no subgraph exists
		assertTrue(graph2.getAttributeNodes().stream().allMatch(
				attributeNode -> attributeNode.getSubgraphs() == null || attributeNode.getSubgraphs().isEmpty()));
	}

	@Test
	void createEntityGraphWithSubGraph() {
		final EntityGraph<User> graph = builder.createEntityGraph(User.class,
				"login,createdBy.id,createdBy.login,groups,roles");
		assertEquals(4, graph.getAttributeNodes().size());
		assertTrue(List.of("login", "createdBy", "groups", "roles").stream()
				.allMatch(s -> graph.getAttributeNodes().stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// subgraph
		assertEquals(3, graph.getAttributeNodes().stream().filter(
				attributeNode -> attributeNode.getSubgraphs() != null && !attributeNode.getSubgraphs().isEmpty())
				.count());
		// createdBy graph
		AttributeNode<?> node = graph.getAttributeNodes().stream()
				.filter(attributeNode -> attributeNode.getAttributeName().equals("createdBy")).findFirst().get();
		final List<AttributeNode<?>> attributes = node.getSubgraphs().values().iterator().next().getAttributeNodes();
		assertEquals(2, attributes.size());
		assertTrue(List.of("id", "login").stream()
				.allMatch(s -> attributes.stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// groups graph
		node = graph.getAttributeNodes().stream()
				.filter(attributeNode -> attributeNode.getAttributeName().equals("groups")).findFirst().get();
		final List<AttributeNode<?>> attributes2 = node.getSubgraphs().values().iterator().next().getAttributeNodes();
		assertEquals(1, attributes2.size());
		assertTrue(List.of("id").stream()
				.allMatch(s -> attributes2.stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// roles graph
		node = graph.getAttributeNodes().stream()
				.filter(attributeNode -> attributeNode.getAttributeName().equals("roles")).findFirst().get();
		final List<AttributeNode<?>> roles = node.getSubgraphs().values().iterator().next().getAttributeNodes();
		assertEquals(1, roles.size());
		assertTrue(
				List.of("name").stream().allMatch(s -> roles.stream().anyMatch(at -> at.getAttributeName().equals(s))));
	}

}