package com.tw.vrc.trees.sitemap;

import com.tw.vrc.trees.sitemap.SiteMapBuilder;
import com.tw.vrc.trees.sitemap.SiteMapNode;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;

public class SiteMapBuilderTest {

    private SiteMapBuilder builder;

    @Before
    public void setup(){
       builder = new SiteMapBuilder();
    }

    @Test
    public void shouldWork() {
        List<String> urls = new ArrayList<String>();
        urls.add("/1/11/112");
        urls.add("/1/11/111");
        urls.add("/1/11");
        urls.add("/1");
        urls.add("/1/12/111");
        urls.add("/1/12");
        urls.add("/1/12/112");
        urls.add("/2/22/221");
        urls.add("/2/22");
        urls.add("/2");
        urls.add("/2/21");
        urls.add("/2/21/221");
        urls.add("/2/22/222");
        urls.add("/2/21/222");

        SiteMapNode root = builder.on(urls);
        assertNotNull(root);
        root.print();
    }
}
