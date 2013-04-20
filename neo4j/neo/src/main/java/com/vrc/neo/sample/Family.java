package com.vrc.neo.sample;

import com.vrc.neo.base.Log;
import com.vrc.neo.base.NeoDb;
import com.vrc.neo.domain.Relation;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;

public class Family extends Sample {

    public Family(NeoDb neoDb) {
        super(neoDb);
    }

    @Override
    public void run() {
        Transaction tx = neoDb.tx();
        try {
            Node child = neoDb.node();
            child.setProperty("name", "Child");
            Node mother = neoDb.node();
            mother.setProperty("name", "Mother");
            Node father = neoDb.node();
            father.setProperty("person", "Father");
            Node brother = neoDb.node();
            brother.setProperty("person", "Brother");

            Relationship rel1 = mother.createRelationshipTo(child, Relation.parent);
            rel1.setProperty("years", 2);
            Relationship rel2 = father.createRelationshipTo(child, Relation.parent);
            rel2.setProperty("years", 2);
            child.createRelationshipTo(mother, Relation.child);
            child.createRelationshipTo(father, Relation.child);
            Relationship rel3 = father.createRelationshipTo(brother, Relation.sibling);
            rel3.setProperty("years",30);
            Relationship rel4 = father.createRelationshipTo(mother, Relation.spouse);
            rel4.setProperty("years",3);
            neoDb.markToDelete(rel1,rel2,rel3,rel4);


            Index<Node> index = neoDb.index("name");
            index.add(child,"name","child");
            index.add(father,"name","father");
            index.add(mother,"name","mother");
            Log.print(index.get("name","child").getSingle().getProperty("name").toString());


            final TraversalDescription travel1 = Traversal.description().breadthFirst().relationships(Relation.parent);
            Traverser traverser = travel1.traverse(child);
            for(Path path:traverser )
                Log.print(path.length()+","+path.endNode().getProperty("name"));

            tx.success();
        }catch (Exception e){
            tx.failure();
        }finally {
            tx.finish();
            neoDb.clearAll();
        }
    }
}
