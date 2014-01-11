package com.github.lambdabuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.lambdabuilder.Node.node;


public class Builder<T> implements Supplier<T> {


    protected Node root;
    protected Node currentNode;

    public Builder() {
        this.root = getDefaultRootElement("root");
        this.currentNode = root;
    }

    public Builder(String defaultRootElement) {
        this.root = getDefaultRootElement(defaultRootElement);
        this.currentNode = root;
    }

    public Builder(Node root) {
        this.root = root;
        this.currentNode = root;
    }

    public static <T> Builder root(String name, T value) {
        return new Builder<T>(node(name, value));
    }

    public static <T> Builder root(String name, T value, Runnable consumer) {
        Builder<T> tBuilder = new Builder<T>(node(name, value));
        consumer.run();
        return tBuilder;
    }

    public static <T> Builder root(String name) {
        return new Builder<T>(node(name, null));
    }

    public static <T> Builder root(String name, Runnable consumer) {
        return root(name, null, consumer);
    }

    protected Node getDefaultRootElement(String rootName) {
        return new Node(rootName, null);
    }

    public Builder<T> append(Node node) {
        this.currentNode.child(node);
        return this;
    }

    public Builder<T> append(Node node, Runnable consumer) {
        this.currentNode.child(node);
        Node oldNode = this.currentNode;
        this.currentNode = node;
        consumer.run();
        this.currentNode = oldNode;
        return this;
    }

    public void eachNode(Consumer<Node> consumer) {
        this.root.visitNode(consumer);
    }

    public void eachChild(Node node, Consumer<Node> consumer) {
        node.getChildren().forEach((child) -> {
            consumer.accept(child);
            eachChild(child, consumer);
        });
    }

    public void visitBeforeAndAfter(BiConsumer<Boolean, Node> consumer) {
        this.root.visitNodeBeforeAndAfter(consumer);

    }


    @Override
    public T get() {
        return null;
    }
}
