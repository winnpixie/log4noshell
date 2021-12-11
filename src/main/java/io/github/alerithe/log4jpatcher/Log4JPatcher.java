package io.github.alerithe.log4jpatcher;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

public class Log4JPatcher {
    public static final Logger LOGGER = Logger.getLogger(Log4JPatcher.class.getName());

    public static void main(String[] args) {
        LOGGER.warning("Log4J-Patcher is NOT a standalone JAR, it runs as a Java Agent.");
        LOGGER.info("Example: 'java -jar -javaagent:log4j-patcher.jar YourApplication.jar'");
    }

    public static void premain(String args, Instrumentation inst) {
        LOGGER.info("Hello from Log4J-Patcher!");
        inst.addTransformer(new JndiLookupTransformer());
    }
}
