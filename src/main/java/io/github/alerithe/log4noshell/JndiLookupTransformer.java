package io.github.alerithe.log4noshell;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JndiLookupTransformer implements ClassFileTransformer {
    public static final String CLASS_NAME = "org.apache.logging.log4j.core.lookup.JndiLookup";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null) return null;

        className = className.replace('/', '.');
        if (!className.equals(CLASS_NAME)) return null;

        try {
            ClassPool cPool = ClassPool.getDefault();
            cPool.appendClassPath(new ByteArrayClassPath(className, classfileBuffer));

            CtClass ctJndiLookupClass = cPool.getCtClass(CLASS_NAME);
            CtMethod ctLookupMethod = ctJndiLookupClass.getMethod("lookup", "(Lorg/apache/logging/log4j/core/LogEvent;Ljava/lang/String;)Ljava/lang/String;");
            ctLookupMethod.setBody("{return null;}");

            PatchAgent.LOGGER.info("Applying patches to JndiLookup#lookup!");
            return ctJndiLookupClass.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            PatchAgent.LOGGER.info("Could not apply patches to JndiLookup#lookup!");
            e.printStackTrace();
        }

        return null;
    }
}
