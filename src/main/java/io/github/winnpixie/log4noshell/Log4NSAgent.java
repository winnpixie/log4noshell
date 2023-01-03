package io.github.winnpixie.log4noshell;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

public class Log4NSAgent {
    public static final Logger LOGGER = Logger.getLogger(Log4NSAgent.class.getName());

    public static void premain(String args, Instrumentation inst) {
        LOGGER.info("Log4NoShell, an agent to aid against CVE-2021-44228 (\"Log4Shell\")");

        inst.addTransformer(new JndiLookupTransformer());
    }
}
