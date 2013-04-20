package com.vrc.neo.base;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeoDb {

    private GraphDatabaseService databaseService;
    
    private final String dataFolder = "/home/vijayrc/projs/MISC/neodata";

    private List<Node> nodesToDelete = new ArrayList<Node>();
    private List<Relationship> relationsToDelete = new ArrayList<Relationship>();
    private List<Index> indexesToDelete = new ArrayList<Index>();

    public NeoDb start(){
        databaseService = new GraphDatabaseFactory().newEmbeddedDatabase(dataFolder);
        Log.print("started db");
        return this;
    }

    public Node node(){
        Node node = databaseService.createNode();
        markToDelete(node);
        return node;
    }

    public Index<Node> index(String name){
        Index<Node> index = databaseService.index().forNodes(name);
        markToDelete(index);
        return index;
    }

    public void hookToStop(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                databaseService.shutdown();
                Log.print("shutdown db");
            }
        });
    }

    public Transaction tx() {
        return databaseService.beginTx();
    }

    public NeoDb markToDelete(Node... nodes){
        nodesToDelete.addAll(Arrays.asList(nodes));
        return this;
    }

    public NeoDb markToDelete(Relationship... relationships){
        relationsToDelete.addAll(Arrays.asList(relationships));
        return this;
    }

    public NeoDb markToDelete(Index... indexes){
        indexesToDelete.addAll(Arrays.asList(indexes));
        return this;
    }

    public void clearAll(){
        Transaction tx = tx();
        try {
            for (Index index : indexesToDelete)
                index.delete();
            for (Relationship relationship : relationsToDelete)
                relationship.delete();
            for (Node node : nodesToDelete)
                node.delete();
            tx.success();
        } catch (Exception e) {
            Log.print(e);
            tx.failure();
        }finally{
            tx.finish();
        }
    }
}
