package io.github.winnpixie.log4noshell;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class JndiLookupTransformer implements ClassFileTransformer {
    private static final String CLASS_NAME = "org/apache/logging/log4j/core/lookup/JndiLookup";
    private static final String METHOD_NAME = "lookup";
    private static final String METHOD_DESC = "(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (!className.equals(CLASS_NAME)) return null;

        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (!name.equals(METHOD_NAME)) return null;
                if (!descriptor.equals(METHOD_DESC)) return null;

                return new MethodVisitor(Opcodes.ASM9, cw.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitCode() {
                        super.visitInsn(Opcodes.ACONST_NULL);
                        super.visitInsn(Opcodes.ARETURN);
                    }
                };
            }
        };

        cr.accept(cv, ClassReader.SKIP_FRAMES);
        return cw.toByteArray();
    }
}
