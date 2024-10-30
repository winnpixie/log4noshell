package io.github.winnpixie.log4noshell;

import java.lang.instrument.Instrumentation;

public class Log4NoShellAgent {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("Log4NoShell, a patch for CVE-2021-44228 (Log4Shell).");

        inst.addTransformer(new JndiLookupTransformer());
    }
}
