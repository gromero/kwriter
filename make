#!/bin/bash -x
javac --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED kwriter.java
