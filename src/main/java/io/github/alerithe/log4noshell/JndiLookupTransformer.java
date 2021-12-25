package io.github.alerithe.log4noshell;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassTooLargeException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodTooLargeException;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class JndiLookupTransformer implements ClassFileTransformer {
    private static final String TARGET_CLASS_NAME = "org.apache.logging.log4j.core.lookup.JndiLookup";
    private static final String TARGET_METHOD_NAME = "lookup";
    private static final String TARGET_METHOD_DESC = "(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null) {
            return null;
        }

        className = className.replace('/', '.');
        if (!className.equals(TARGET_CLASS_NAME)) {
            return null;
        }

        try {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, ClassReader.SKIP_FRAMES);

            for (MethodNode methodNode : classNode.methods) {
                if (!methodNode.name.equals(TARGET_METHOD_NAME)
                        || !methodNode.desc.equals(TARGET_METHOD_DESC)) {
                    continue;
                }

                methodNode.instructions.clear();

                methodNode.instructions.add(new InsnNode(Opcodes.ACONST_NULL));
                methodNode.instructions.add(new InsnNode(Opcodes.ARETURN));
            }

            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);

            PatchAgent.LOGGER.info("Applying patches to JndiLookup#lookup!");
            return classWriter.toByteArray();
        } catch (IllegalStateException | ClassTooLargeException | MethodTooLargeException e) {
            PatchAgent.LOGGER.info("Could not apply patches to JndiLookup#lookup!");
            e.printStackTrace();
        }

        return null;
    }
}
