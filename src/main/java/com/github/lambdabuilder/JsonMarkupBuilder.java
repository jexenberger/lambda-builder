package com.github.lambdabuilder;

import java.util.concurrent.atomic.AtomicInteger;


public class JsonMarkupBuilder extends MarkupBuilder {


    public JsonMarkupBuilder(boolean isRoot, String defaultRootElementName) {
        super(defaultRootElementName);
        this.rootElement = isRoot;
    }

    @Override
    public String get() {

        final StringBuilder builder = (rootElement) ? new StringBuilder("{\n") : new StringBuilder();
        final AtomicInteger level = new AtomicInteger(-1);
        this.visitBeforeAndAfter((isBefore, node) -> {
            Object nodeValue = node.getValue();
            if (isBefore) {
                if (nodeValue != null) {
                    if (nodeValue instanceof String || !nodeValue.getClass().getPackage().getName().startsWith("java"))  {
                        nodeValue = quote(nodeValue.toString());
                    }
                } else {
                    nodeValue = (node.getChildren().isEmpty()) ? "null" : "";
                }
                builder.append(spacer(level.incrementAndGet())).append(quote(node.getName())).append(':');
                if (nodeValue != null) {
                    builder.append(nodeValue);
                }
                if (node.getChildren().size() == 1) {
                    builder.append("{\n");
                }
                if (node.getChildren().size() > 1) {
                    builder.append("[\n");
                }
            } else {
                int currentLevel = level.getAndDecrement();
                if (!node.isAnonymous()) {
                    String spacer = "";
                    if (builder.charAt(builder.length() - 1) == '\n') {
                        spacer = spacer(currentLevel);
                    }
                    builder.append(spacer).append(",\n");
                }
            }
        });
        if (rootElement) {
            builder.append("\n}");
        }
        return builder.toString();
    }

    private String quote(Object stringToQuote) {
        return '"' + stringToQuote.toString() + '"';
    }
}
