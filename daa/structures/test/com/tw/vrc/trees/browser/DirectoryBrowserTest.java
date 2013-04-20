package com.tw.vrc.trees.browser;

import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class DirectoryBrowserTest {

    @Test
    public void shouldWork() {
        DirectoryBrowser browser = new DirectoryBrowser("D:\\Vijay\\Projs\\NEW\\dummy\\101");
        assertNotNull(browser);
        assertTrue(browser.exists(new File("D:\\Vijay\\Projs\\NEW\\dummy\\101\\A\\A1\\A12\\A121.txt")));
        assertFalse(browser.exists(new File("D:\\Vijay\\Projs\\NEW\\dummy\\101\\A\\A1\\A12\\A1212.txt")));
    }
}
