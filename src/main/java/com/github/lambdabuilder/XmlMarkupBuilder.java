package com.github.lambdabuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.lambdabuilder.Node.node;


public class XmlMarkupBuilder extends MarkupBuilder {


    public XmlMarkupBuilder(boolean isRoot, String defaultRootElementName) {
        super(defaultRootElementName);
        this.rootElement = isRoot;
    }

    public XmlMarkupBuilder(boolean isRoot) {
        super("xml");
        this.rootElement = isRoot;
    }

    @Override
    public String get() {

        final StringBuilder builder = (rootElement) ?  new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n") : new StringBuilder();
        final AtomicInteger level = new AtomicInteger(-1);
        this.visitBeforeAndAfter((isBefore, node) -> {
            Object nodeValue = node.getValue();
            if (isBefore) {
                if (!node.isAnonymous()) {
                    builder.append(spacer(level.incrementAndGet())).append("<").append(node.getName());
                    if (!node.getAttributes().isEmpty()) {
                        node.getAttributes().forEach((key, value) -> builder.append(" ").append(key).append("=\"").append(value).append("\" "));
                    }
                    builder.append(">");
                }
                if (nodeValue != null) {
                    builder.append(nodeValue);
                }
                if (!node.getChildren().isEmpty()) {
                    builder.append("\n");
                }
            } else {
                int currentLevel = level.getAndDecrement();
                if (!node.isAnonymous()) {
                    String spacer = "";
                    if (builder.charAt(builder.length()-1) == '\n') {
                        spacer = spacer(currentLevel);
                    }
                    builder.append(spacer).append("</").append(node.getName()).append(">\n");
                }
            }
        });
        return builder.toString();
    }


}
