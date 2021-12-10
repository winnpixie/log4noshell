package io.github.alerithe.log4jpatcher;

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
            CtClass ctJndiLookupClass = cPool.getCtClass(CLASS_NAME);
            CtMethod ctLookupMethod = ctJndiLookupClass.getDeclaredMethod("lookup");
            ctLookupMethod.setBody("{return null;}");

            return ctJndiLookupClass.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
