package io.github.winnpixie.log4noshell;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JndiLookupTransformer implements ClassFileTransformer {
    private static final String TARGET_CLASS_NAME = "org.apache.logging.log4j.core.lookup.JndiLookup";
    private static final String TARGET_METHOD_NAME = "lookup";
    private static final String TARGET_METHOD_DESC = "(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null) return null;

        className = className.replace('/', '.');
        if (!className.equals(TARGET_CLASS_NAME)) return null;

        try {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, ClassReader.SKIP_FRAMES);

            for (MethodNode methodNode : classNode.methods) {
                if (!methodNode.name.equals(TARGET_METHOD_NAME)) continue;
                if (!methodNode.desc.equals(TARGET_METHOD_DESC)) continue;

                methodNode.instructions.clear();
                methodNode.instructions.add(new InsnNode(Opcodes.ACONST_NULL));
                methodNode.instructions.add(new InsnNode(Opcodes.ARETURN));
            }

            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);

            Log4NoShellAgent.LOGGER.info("Patching class");
            return classWriter.toByteArray();
        } catch (IllegalStateException | ClassTooLargeException | MethodTooLargeException e) {
            Log4NoShellAgent.LOGGER.severe("ERROR PATCHING METHOD OR CLASS");

            e.printStackTrace();
        }

        return null;
    }
}
