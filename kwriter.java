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

    ClassWriterExt cw = new ClassWriterExt(ClassWriter.COMPUTE_MAXS);

    cw.visit(51,                 /* Class version  */
             0,                  /* Access */
             "dummy",            /* Internal class name */
             null,               /* Class signature */
             null,
             null);

    cw.setCacheMTypes(false);

    MethodVisitor mainMV = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                                          "main",
                                          "()V",
                                          null,
                                          null);

    int constCount = 0;
    int methodNum = 0;
    int K = 64500;

    while (constCount < K) {
      String methodName = "test" + methodNum;
 
      MethodVisitor mw = cw.visitMethod(
              Opcodes.ACC_PUBLIC,
              methodName, "()V", 
              null, new String[0]);
 
      System.out.println("Creating method " +  methodName + " ..."); 
 
 
      while (constCount < K && cw.getBytecodeLength(mw) < K) {
  	  System.out.print(constCount + "\r");
          mw.visitLdcInsn(Type.getMethodType("(FIZ)V"));
          ++constCount;
      }
 
      System.out.println("");
 
      mw.visitMaxs(-1, -1);
      mw.visitEnd();
 
      ++methodNum;
    }

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
}
