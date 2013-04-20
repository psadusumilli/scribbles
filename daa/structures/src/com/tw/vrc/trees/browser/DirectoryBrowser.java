package com.tw.vrc.trees.browser;

import com.tw.vrc.trees.nodes.BigNode;

import java.io.File;

public class DirectoryBrowser {
    private BigNode<File> root;

    public DirectoryBrowser(String path) {
        root = new BigNode(new File(path));
        recurse(root);
    }

    private void recurse(BigNode<File> node) {
        for (File file : node.value().listFiles()) {
            BigNode<File> fileNode = new BigNode<File>(file);
            node.addChild(fileNode);
            if (file.isDirectory()) recurse(fileNode);
        }
    }

    public boolean exists(File file) {
        if (root == null) return false;
        BigNode<File> toFind = new BigNode<File>(file);
        return root.exists(toFind);
    }


}
