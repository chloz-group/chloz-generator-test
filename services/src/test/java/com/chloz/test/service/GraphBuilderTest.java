package com.chloz.test.service;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.GraphBuilder;
import com.chloz.test.domain.User;
import jakarta.persistence.AttributeNode;
import jakarta.persistence.EntityGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = com.chloz.test.service.ChlozTestServiceTestsApplication.class)
@SpringBootTest
class GraphBuilderTest {

	private final GraphBuilder builder;
	@Autowired
	public GraphBuilderTest(GraphBuilder builder) {
		this.builder = builder;
	}

	@BeforeEach
	void setUp() {
		// This will be implemented later
	}

	@AfterEach
	void tearDown() {
		// This will be implemented later
	}

	@Test
	void createNullEntityGraph() {
		EntityGraph<User> graph = builder.createEntityGraph(User.class, null);
		assertEquals(1, graph.getAttributeNodes().size());
		assertEquals("id", graph.getAttributeNodes().get(0).getAttributeName());
	}

	@Test
	void createEntityGraphWithUnkownProperty() {
		assertThrows(BusinessException.class,
				() -> builder.createEntityGraph(User.class, "aSampleProperty,anotherOne"));
		assertThrows(BusinessException.class,
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

	@Test
	@DisplayName("Create an entityGraph that provide all child entities identifier")
	void createEntityGraphWithAllSubGraphSimpleFields() {
		// ** is used for getting entity simple fields and also dependent entities IDs
		// Collections entities are not included
		final EntityGraph<User> graph = builder.createEntityGraph(User.class, "**");
		// all simple fields of the entities are available
		assertTrue(graph.getAttributeNodes().size() >= 10);
		assertTrue(List
				.of("id", "login", "password", "createdDate", "disabled", "lastModifiedDate", "deleted", "picture",
						"createdBy", "lastModifiedBy")
				.stream()
				.allMatch(s -> graph.getAttributeNodes().stream().anyMatch(at -> at.getAttributeName().equals(s))));
		// SubGraph exists for all child properties : (picture, createdBy,
		// lastModifiedBy)
		List<AttributeNode<?>> subGraphs = this.getSubEntityGraph(graph.getAttributeNodes());
		assertEquals(3, subGraphs.size());
		assertTrue(subGraphs.stream()
				.allMatch(an -> List.of("picture", "createdBy", "lastModifiedBy").contains(an.getAttributeName())));
		// for each subGraph only id field exist and no other subgraph exist
		for (AttributeNode<?> node : subGraphs) {
			List<AttributeNode<?>> attributes = new ArrayList<>();
			node.getSubgraphs().values().stream().forEach(sg -> attributes.addAll(sg.getAttributeNodes()));
			assertEquals(1, attributes.size());
			assertEquals("id", attributes.get(0).getAttributeName());
		}
	}

	private List<AttributeNode<?>> getSubEntityGraph(List<AttributeNode<?>> attributeNodes) {
		return attributeNodes.stream().filter(
				attributeNode -> attributeNode.getSubgraphs() != null && !attributeNode.getSubgraphs().isEmpty())
				.toList();
	}

}