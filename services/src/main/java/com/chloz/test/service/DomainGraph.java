package com.chloz.test.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;

/**
 * Represent a graph of attributes of an Object
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainGraph {

	private List<String> attributes;

	private Map<String, DomainGraph> subGraphs;

}