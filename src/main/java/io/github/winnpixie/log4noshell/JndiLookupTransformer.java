package io.github.winnpixie.log4noshell;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JndiLookupTransformer implements ClassFileTransformer {
    private static final String LOOKUP_CLASS_NAME = "org/apache/logging/log4j/core/lookup/JndiLookup";
    private static final String LOOKUP_METHOD_NAME = "lookup";
    private static final String LOOKUP_METHOD_DESC = "(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!LOOKUP_CLASS_NAME.equals(className)) return null;

        try {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, ClassReader.SKIP_FRAMES);

            for (MethodNode methodNode : classNode.methods) {
                if (!methodNode.name.equals(LOOKUP_METHOD_NAME)) continue;
                if (!methodNode.desc.equals(LOOKUP_METHOD_DESC)) continue;

                methodNode.instructions.clear();
                methodNode.instructions.add(new InsnNode(Opcodes.ACONST_NULL));
                methodNode.instructions.add(new InsnNode(Opcodes.ARETURN));
            }

            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);

            System.out.println("Attempting to patch JndiLookup#lookup");
            return classWriter.toByteArray();
        } catch (ClassTooLargeException | MethodTooLargeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
