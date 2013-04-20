package com.tw.vrc.trees.sitemap;

import com.tw.vrc.lists.Queue;
import com.tw.vrc.utilities.Print;
import com.tw.vrc.utilities.Printable;

import java.util.*;

public class SiteMapNode implements Printable{
    private String url;
    private String value;
    private Map<String, SiteMapNode> children = new HashMap<String, SiteMapNode>();

    public SiteMapNode(String url, String value) {
        this.url = url;
        this.value = value;
    }

    public void add(String url) {
        String[] splits = url.split("/");
        Queue<String> tokens = new Queue<String>();
        for (int i = 1; i < splits.length; i++) tokens.enqueue(splits[i]);
        recurse(tokens, url);
    }

    public void recurse(Queue<String> tokens, String url) {
        if (tokens.isEmpty()) return;
        String key = tokens.dequeue();
        if (!children.containsKey(key))children.put(key, new SiteMapNode(url, key));
        children.get(key).recurse(tokens, url);
    }

    public List<Printable> printables() {
        return new ArrayList<Printable>(children.values());
    }

    public void print() {
        new Print().with(this);
    }

    @Override
    public String toString() {
        return value+"=>"+url;
    }
}
