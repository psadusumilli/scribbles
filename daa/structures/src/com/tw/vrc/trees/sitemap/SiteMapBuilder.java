package com.tw.vrc.trees.sitemap;

import com.tw.vrc.sort.InsertionSort;

import java.util.List;

public class SiteMapBuilder {

    public SiteMapNode on(List<String> urls) {
        new InsertionSort().on(urls);
        SiteMapNode root = new SiteMapNode("/", "/");
        for (String url : urls) {
            root.add(url);
        }
        return root;
    }
}
