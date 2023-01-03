# Log4NoShell
A Java Agent that disables Apache Log4J's JNDI Lookup to aid against [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228) ("Log4Shell").

If possible, update your program to use the latest Log4J version, as the vulnerability is fixed as of version *2.17.1*.
Otherwise, download [log4noshell-0.4-SNAPSHOT-shaded.jar](https://winnpixie.github.io/log4noshell/log4noshell-0.4-SNAPSHOT-shaded.jar) and continue reading.

## Usage
To use Java Agents, you must specify them with the `-javaagent` argument. \
`java -jar -javaagent:path/to/log4noshell-0.4-SNAPSHOT-shaded.jar Program.jar`

### **Minecraft**
Please read [Mojang's response](https://www.minecraft.net/en-us/article/important-message--security-vulnerability-java-edition)
to the matter to determine if you need this patcher.

- Client:
    1. Go to the *Installations* tab in the launcher
    2. Click on the **three dots** on the **right** side of the version you'd like to use
    3. Click *Edit*
    4. Scroll down and click on *More Options*
    5. Add `-javaagent:path/to/log4noshell-0.4-SNAPSHOT-shaded.jar` to the *JVM Arguments* text-field
        - `-javaagent:path/to/log4noshell-0.4-SNAPSHOT-shaded.jar -Xms2G -Xms2G...`
    6. Click *Save*
- Server:
    1. Add `-javaagent:path/to/log4noshell-0.4-SNAPSHOT-shaded.jar` somewhere before the `-jar` in your launch/start/run command
       - `java -javaagent:path/to/log4noshell-0.4-SNAPSHOT-shaded.jar -jar minecraft_server.jar`