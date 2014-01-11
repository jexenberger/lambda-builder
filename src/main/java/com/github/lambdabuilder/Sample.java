package com.github.lambdabuilder;

import static com.github.lambdabuilder.Node.node;


public class Sample {


    public static void main(String[] args) {
        final XmlMarkupBuilder b = new XmlMarkupBuilder(false, "html");

        b.el("head", () -> {
            b.el("title", "This is a test");
        });

        b.el("body", () -> {
            b.el("p", () -> {
                b.content("hello");
                b.el("i", () -> {
                    b.content("world");
                });
            });
        });


        System.out.println(b.get());

    }
}
