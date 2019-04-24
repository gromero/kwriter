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

  protected static final int CP_CONST_COUNT = 65400;
  protected static final int MAX_METHOD_SIZE = 65400;
  protected static final String TEST_METHOD_SIGNATURE = "()V";

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
    cw.setCacheMTypes(false);
    int i, j;

//  System.out.println("BEGIN");
        // TODO: check real CP size and also limit number of iterations in this cycle

       MethodVisitor mainMV = cw.visitMethod(
               Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
               "main", "()V",
               null, null);

        int constCount = 0;
        int methodNum = 0;

        while (constCount < CP_CONST_COUNT) {
            final String methodName = "test" + String.format("%d", methodNum);

            MethodVisitor mw = cw.visitMethod(
                    Opcodes.ACC_PUBLIC,
                    methodName, TEST_METHOD_SIGNATURE,
                    null, new String[0]);

            System.out.println("Creating method " +  methodName + " ..."); 

            // generateTestMethodProlog(mw);

            // TODO: check real CP size and also limit number of iterations in this cycle
            while (constCount < CP_CONST_COUNT && cw.getBytecodeLength(mw) < MAX_METHOD_SIZE) {
                //generateCPEntryData(cw, mw);
                mw.visitLdcInsn(Type.getMethodType("(FIZ)V"));
//           mw.visitInsn(Opcodes.POP);
		System.out.print(constCount + "\r");
                ++constCount;
            }

            System.out.println("\n");
//          generateTestMethodEpilog(mw);

            mw.visitMaxs(-1, -1);
            mw.visitEnd();

//            mainMV.visitInsn(Opcodes.DUP);
//            mainMV.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "", methodName, TEST_METHOD_SIGNATURE);
            ++methodNum;
        }

//
/*
    for (i = 0; i < 4 ; ++i) {

      String methodName = "test" + String.format("%d", i);
      MethodVisitor testMV = cw.visitMethod(
             Opcodes.ACC_PUBLIC,
             methodName, "()V", null, new String[0]);

      for (j = 0; j < 512; ++j) {
        testMV.visitLdcInsn(Type.getMethodType("(FIZ)V"));
        testMV.visitInsn(Opcodes.POP);
      }

      testMV.visitInsn(Opcodes.RETURN);

      testMV.visitMaxs(-1, -1);
      testMV.visitEnd();
    }
*/
//  System.out.println("END");

    cw.visitEnd();
 
    write("/tmp/gus/", "x.class", cw.toByteArray());
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
