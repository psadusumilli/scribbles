package com.tw.vrc.utilities;

public class Print {

    public void with(Printable node) {
        with(node, 0);
    }

    private void with(Printable node, int pad) {
        toConsole(pad, node);
        ++pad;
        for (Printable child : node.printables())
            if (child != null) with(child, pad);
    }

    private void toConsole(Integer pad, Object o) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pad; i++) builder.append("      ");
        builder.append("|----");
        builder.append("[" + pad + "]");
        System.out.println(builder.toString() + o);
    }
}
