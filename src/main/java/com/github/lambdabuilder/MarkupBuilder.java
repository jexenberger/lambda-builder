package com.github.lambdabuilder;

import java.util.function.Supplier;

import static com.github.lambdabuilder.Node.node;
import static com.github.lambdabuilder.Node.val;


public class MarkupBuilder extends Builder<String> {

    boolean rootElement = false;

    public MarkupBuilder() {
        super();
    }

    public MarkupBuilder(String defaultRootElement) {
        super(defaultRootElement);
    }

    protected static String spacer(int len){
       StringBuilder b = new StringBuilder();
        for (int i = 0; i < len; i++) {
             b.append('\t');
        }
        return b.toString();
    }

    public MarkupBuilder el(String name) {
        this.append(node(name));
        return this;
    }

    public MarkupBuilder content(String name) {
        this.append(val(name));
        return this;
    }

    public MarkupBuilder el(String name, Object value) {
        this.append(node(name, value));
        return this;
    }

    public MarkupBuilder elx(String name, Supplier value) {
        this.append(node(name, value));
        return this;
    }

    public MarkupBuilder el(String name, Runnable runnable) {
        this.append(node(name), runnable);
        return this;
    }

    public MarkupBuilder el(String name, Object value, Runnable runnable) {
        this.append(node(name, value), runnable);
        return this;
    }

    public MarkupBuilder at(String attribute, Object value) {
        this.currentNode.attr(attribute, value);
        return this;
    }
}
