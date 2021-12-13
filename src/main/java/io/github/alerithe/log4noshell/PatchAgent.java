package io.github.alerithe.log4noshell;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

public class PatchAgent {
    public static final Logger LOGGER = Logger.getLogger(PatchAgent.class.getName());

    public static void main(String[] args) {
        LOGGER.warning("Log4NoShell is NOT a standalone JAR, it runs as a Java Agent.");
        LOGGER.info("Example: 'java -jar -javaagent:log4noshell.jar YourApplication.jar'");
    }

    public static void premain(String args, Instrumentation inst) {
        LOGGER.info("Hello from Log4NoShell, a quick-fix for CVE-2021-44228!");

        inst.addTransformer(new JndiLookupTransformer());
    }
}
