# log4noshell
Java Agent that disables Apache Log4J's JNDI Lookup, made in response to [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228) ("Log4Shell").

If you can, use the latest available version of Log4J, as this vulnerability is fixed as of Log4J 2.15.0.

Otherwise, download the [log4noshell-0.1.jar](https://alerithe.github.io/log4noshell/log4noshell-0.1.jar) JAR and follow the steps below.

## How to Use
To use Java Agents, you specify them with the `-javaagent` JVM argument.

Example: `java -jar -javaagent:path/to/MyAgent.jar YourApplication.jar`

### ***Minecraft***
For the client, Log4NoShell is not necessary if you are running MC **1.12 or higher**.

For servers owners, Spigot, Paper, Fabric, and many other projects (ex: Purpur) have updated to fix this issue.

- Client Steps (1.7 - 1.11.2):
    1. Go to the *Installations* tab in the launcher
    2. Click on the **three dots** on the **right** side of the version you'd like to use
    3. Click *Edit*
    4. Scroll down in that menu and click on *More Options*
    5. Add `-javaagent:log4noshell-0.1.jar` to the *JVM Arguments* text-field
        - (ex: `-javaagent:log4noshell-0.1.jar -Xms2G -Xms2G...`)
    6. Click *Save*
- Server Steps (1.7+):
    1. Add `-javaagent:log4noshell-0.1.jar` right before the `-jar` in your launch/start/run command
        - (ex: `java -javaagent:log4noshell-0.1.jar -jar SpigotServer.jar --nogui`)