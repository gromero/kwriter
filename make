#!/bin/bash -x
# javac --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED kwriter.java
# java kwriter
javac -cp ./java.base --patch-module java.base=./java.base --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED ./java.base/jdk/internal/org/objectweb/asm/ClassWriterExt.java
# javac -cp ./java.base --patch-module java.base=./java.base --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED ./java.base/java/lang/invoke/NewInvokeSpecialCallSite.java
javac -cp ./java.base --patch-module java.base=./java.base --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED kwriter.java
java --patch-module java.base=./java.base --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED kwriter
