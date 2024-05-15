package io.github.winnpixie.log4noshell;

import java.lang.instrument.Instrumentation;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log4NoShellAgent {
    public static final Logger LOGGER = LogManager.getLogManager().getLogger(Log4NoShellAgent.class.getName());

    public static void premain(String args, Instrumentation inst) {
        LOGGER.info("Log4NoShell, a Java agent to mitigate CVE-2021-44228 (\"Log4Shell\")");

        inst.addTransformer(new JndiLookupTransformer());
    }
}
