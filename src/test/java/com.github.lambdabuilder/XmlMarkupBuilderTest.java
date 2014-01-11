package com.github.lambdabuilder;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;


public class XmlMarkupBuilderTest {


    @Test
    public void testGetSampleProjectPom() throws Exception {

        final AtomicReference<Double> version = new AtomicReference<>(4.8);


        MarkupBuilder pom = new XmlMarkupBuilder(true, "pom")
                .at("xmlns", "http://maven.apache.org/POM/4.0.0")
                .at("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
                .at("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");
        pom.el("modelVersion", "4.0.0");
        pom.el("groupId", "com.github");
        pom.el("artifactId", "lambda-builder");
        pom.el("version", "1.0-SNAPSHOT");
        pom.el("dependencies", () -> {
            pom.el("dependency", () -> {
                pom.el("groupId", "junit");
                pom.el("artifactId", "junit");
                pom.elx("version", version::get);
            });
            pom.el("dependency", () -> {
                pom.el("groupId", "commons-beanutils");
                pom.el("artifactId", "commons-beanutils");
                pom.elx("version", version::get);
            });
        });
        pom.el("build", () -> {
            pom.el("plugins", () -> {
                pom.el("plugin", () -> {
                    pom.el("groupId", "org.apache.maven.plugins");
                    pom.el("artifactId", "maven-compiler-plugin");
                    pom.el("configuration", () -> {
                        pom.el("source", 1.8);
                        pom.el("target", 1.8);
                        pom.el("fork", true);
                        pom.el("compilerArgument", "-proc:none");
                    });
                });
            });
        });

        System.out.println(pom.get());
    }



    private void buildPom(MarkupBuilder pom, AtomicReference<Double> version) {
        pom.el("modelVersion", "4.0.0");
        pom.el("groupId", "com.github");
        pom.el("artifactId", "lambda-builder");
        pom.el("version", "1.0-SNAPSHOT");
        pom.el("dependencies", () -> {
            pom.el("dependency", () -> {
                pom.el("groupId", "junit");
                pom.el("artifactId", "junit");
                pom.elx("version", version::get);
            });
            pom.el("dependency", () -> {
                pom.el("groupId", "commons-beanutils");
                pom.el("artifactId", "commons-beanutils");
                pom.elx("version", version::get);
            });
        });
        pom.el("build", () -> {
            pom.el("plugins", () -> {
                pom.el("plugin", () -> {
                    pom.el("groupId", "org.apache.maven.plugins");
                    pom.el("artifactId", "maven-compiler-plugin");
                    pom.el("configuration", () -> {
                        pom.el("source", 1.8);
                        pom.el("target", 1.8);
                        pom.el("fork", true);
                        pom.el("compilerArgument", "-proc:none");
                    });
                });
            });
        });
    }
}
