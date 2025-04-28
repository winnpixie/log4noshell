package io.github.winnpixie.log4noshell;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JndiLookupTransformer implements ClassFileTransformer {
    private static final String CLASS_NAME = "org/apache/logging/log4j/core/lookup/JndiLookup";
    private static final String METHOD_NAME = "lookup";
    private static final String METHOD_DESC = "(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals(CLASS_NAME)) return null;

        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, 0);

        classReader.accept(new ClassVisitor(Opcodes.ASM9, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!name.equals(METHOD_NAME)) return mv;
                if (!descriptor.equals(METHOD_DESC)) return mv;

                mv.visitCode();
                mv.visitFrame(Opcodes.F_NEW, 3, new Object[3], 1, new Object[1]);
                mv.visitInsn(Opcodes.ACONST_NULL);
                mv.visitInsn(Opcodes.ARETURN);
                mv.visitMaxs(1, 3);
                mv.visitEnd();
                return null;
            }
        }, 0);

        return classWriter.toByteArray();
    }
}
