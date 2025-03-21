package io.github.winnpixie.log4noshell;

import java.lang.instrument.Instrumentation;

public class Log4NoShellAgent {
    public static final String VERSION = "0.7";

    public static void premain(String args, Instrumentation inst) {
        System.out.printf("Log4NoShell %s, a patch for CVE-2021-44228 (Log4Shell).%n", VERSION);

        inst.addTransformer(new JndiLookupTransformer());
    }
}
