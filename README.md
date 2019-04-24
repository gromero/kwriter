= POWER8

```
$ ./make 
+ java --patch-module java.base=./java.base --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED kwriter
Creating method test0 ...
21582
Creating method test1 ...
43082
Creating method test2 ...
64499
Writing 386925 byte(s) to ./bogus.class ...

real	2m50.130s
user	2m50.184s
sys	0m0.712s
```

= Intel (Skylake)
```
$ ./make
+ java --patch-module java.base=./java.base --add-modules java.base --add-exports java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED kwriter
Creating method test0 ...
21582
Creating method test1 ...
43082
Creating method test2 ...
64499
Writing 386925 byte(s) to ./bogus.class ...

real	1m25.778s
user	1m26.039s
sys	0m0.228s

```
