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

    cw.visit(51, 0, "dummy", null, null, null);

    cw.setCacheMTypes(false);

    MethodVisitor mainMV = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "main", "()V", null, null);

    Type t;
 
    MethodVisitor test00MV = cw.visitMethod( Opcodes.ACC_PUBLIC, "test00", "()V", null, null);
 
    for (int i = 0; i < 32000; ++i) {
      System.out.print(i + "\r");
      t = Type.getMethodType("()V");
//    t = Type.getType("I"); // OK!
      test00MV.visitLdcInsn(t);
    }

    System.out.println("");
  }
}
