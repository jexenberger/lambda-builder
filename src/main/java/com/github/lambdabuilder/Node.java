package com.github.lambdabuilder;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class Node {

    public static final String _DEFAULT_NS = "_default_ns";

    private Map attributes;
    private Object value;
    private String name;
    private String namespace = _DEFAULT_NS;
    private Collection<Node> children;
    private boolean anonymous = false;
    private Node parent;

    public Node(String namespace, String name, Object value, Map<String, ?> attributes) {
        this.namespace = namespace;
        this.name = name;
        this.value = value;
        this.attributes = attributes;
    }

    public Node(String name, Object value, Map<String, ?> attributes) {
        this(_DEFAULT_NS, name, value, attributes);
    }


    public Node(String name, Object value) {
        this(_DEFAULT_NS, name, value, null);
    }

    public Node(Object value) {
        this(_DEFAULT_NS, UUID.randomUUID().toString(), value, null);
        anonymous = true;
    }

    public Node(String  name) {
        this(_DEFAULT_NS, name, null, null);
    }

    public Node(String namespace, String name, Object value) {
        this(namespace, name, value, null);
    }

    public static Node node(String name, Object value) {
        return new Node(name, value);
    }

    public static Node node(String name) {
        return new Node(name, null);
    }
    public static  Node val(Object value) {
        Node node = new Node(UUID.randomUUID().toString(), value);
        node.anonymous = true;
        return node;
    }

    public Collection<Node> getChildren() {
        if (children == null) {
            this.children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(Collection<Node> children) {
        this.children = children;
    }

    public static Node node(String namespace, String name, Object value) {
        return new Node(namespace, name, value);
    }


    public  Node attr(String name, Object val) {
        getAttributes().put(name, val);
        return this;
    }

    public Node child(Node child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        this.children.add(child);
        child.parent = this;
        return this;
    }

    public Object getValue() {
        if (value instanceof Supplier) {
            return  ((Supplier) value).get();
        }
        return  value;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Map getAttributes() {
        if (attributes == null) {
            attributes = new LinkedHashMap<>(3, 1.0f);
        }
        return attributes;
    }

    public Node visitNode(Consumer<Node> consumer) {
        consumer.accept(this);
        getChildren().forEach((child) -> {
            child.visitNode(consumer);
        });
        return this;
    }

    public Node visitNodeBeforeAndAfter(BiConsumer<Boolean, Node> consumer) {
        consumer.accept(true, this);
        getChildren().forEach((child) -> {
            child.visitNodeBeforeAndAfter(consumer);
        });
        consumer.accept(false, this);
        return this;
    }

    public Node visitBefore(Consumer<Node> consumer) {
        consumer.accept(this);
        return this;
    }

    public Node visitAfter(Consumer<Node> consumer) {
        consumer.accept(this);
        return this;
    }

    public Node getParent() {
        return parent;
    }
}
