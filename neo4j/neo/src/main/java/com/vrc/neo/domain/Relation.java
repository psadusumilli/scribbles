package com.vrc.neo.domain;

import org.neo4j.graphdb.RelationshipType;

public enum Relation implements RelationshipType {
    parent, friend, sibling, child, spouse
}
