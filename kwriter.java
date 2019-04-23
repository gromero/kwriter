import jdk.internal.org.objectweb.asm.ByteVector;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.ClassWriterExt;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class kwriter {

  public static void main(String[] args) {
    System.out.println("main()[]");
    System.out.println(getDummyInterfaceClassName());

    Class<?> myclass;

    try {
      myclass = Class.forName("kwriter");
    } catch (Exception Ex) {
      System.out.println(Ex);
      myclass = null;
    }

    String packageName = myclass.getPackage().getName();
    String simpleName  = myclass.getSimpleName();

    System.out.println("Class package name: " + packageName);
    System.out.println("Class simple name : " + simpleName);

    String className = packageName + simpleName;

    ClassWriterExt cw = new ClassWriterExt(ClassWriter.COMPUTE_MAXS);
    cw.visit(51,                 /* Class version  */
//           Opcodes.ACC_PUBLIC, /* Access */
             0, /* Access */
//           className,          /* Internal class name */
             "dummy",            /* Internal class name */
             null,               /* Class signature */
             null,
             null);

    int i;

    cw.setCacheMTypes(false);
    for (i = 0; i < 65400; ++i) {

      String methodName = "test" + String.format("%2d", i);
      MethodVisitor testMV = cw.visitMethod(
             Opcodes.ACC_PUBLIC,
             methodName, "()V", null, new String[0]);

      testMV.visitMaxs(-1,-1);
      testMV.visitEnd();
    }

   cw.visitEnd();

   write("/tmp/gus/", "dummy.class", cw.toByteArray());
/*


    mainmv.visitInsn(Opcodes.DUP);
    mainmv.visitInsn(Opcodes.POP);
    mainmv.visitInsn(Opcodes.DUP);
    mainmv.visitInsn(Opcodes.POP);
    mainmv.visitInsn(Opcodes.DUP);
    mainmv.visitInsn(Opcodes.POP);
*/
/*
    int CP_CONST_COUNT = 65400;
    int MAX_METHOD_SIZE = 65400;
   int constCount = 0;

    System.out.println("BEGIN");
    while (constCount < CP_CONST_COUNT/2) {
      mainmv.visitLdcInsn(Type.getMethodType("(FIZ)V"));
//      mainmv.visitInsn(Opcodes.POP);
      ++constCount;
    }
    System.out.println("END");

    mainmv.visitInsn(Opcodes.RETURN);
    mainmv.visitMaxs(-1, -1);
    mainmv.visitEnd();

    cw.visitEnd();

    write("/tmp/gus/", "dummy.class", cw.toByteArray());
    
//
//   String h = "Hello";
//   write("/tmp/gus/","mock01.txt", h.getBytes());
/*
    public static class Klass {
      private String pkgName;
      private String className;
      private byte[] bytes;
      private String mainMethodName;
      private String mainMethodSignature;
*/

  }

  static void write(String path, String filename, byte[] b) {
     File dir  = new File(path);
     dir.mkdirs();

     File file = new File(dir, filename);
     FileOutputStream os;
 
     try {
       os = new FileOutputStream(file);
     } catch (Exception E) {
       System.out.println(E);
       os = null;
     }
  
     try {
       System.out.println("Writing " + b.length +  " byte(s) to " + path + filename + " ...");
       os.write(b);
     } catch (Exception E) {
       System.out.println(E);
     }  
   }

 protected interface DummyInterface {
     public void targetInstance();
 }
  
 protected static String getDummyInterfaceClassName() {
     return DummyInterface.class.getName().replace('.', '/');
 } 

 public static void mockup() {
 }
}
