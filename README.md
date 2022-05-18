# Log4NoShell
A runtime patcher that disables Apache Log4J's JNDI Lookup to aid against [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228) ("Log4Shell").

If you can, upgrade to the latest version of Log4J, as the vulnerability is fixed as of Log4J version *2.17.1*.
Otherwise, download [log4noshell-0.3-SNAPSHOT-shaded.jar](https://tivuhh.github.io/log4noshell/log4noshell-0.3-SNAPSHOT-shaded.jar) and continue reading.

## Usage
To use Java Agents, you specify them with the `-javaagent` argument. \
Example: `java -jar -javaagent:path/to/log4noshell-0.3-SNAPSHOT-shaded.jar YourApplication.jar`

### **Minecraft**
For clients, Log4NoShell is not necessary if you are running MC **lower than 1.7.2 OR higher than 1.11.2**. \
For servers admins: Spigot, Paper, Fabric, and many other server softwares (ex: Purpur) have updated their Log4J versions.

***Please*** read [Mojang's response](https://www.minecraft.net/en-us/article/important-message--security-vulnerability-java-edition) to the matter.

If you still believe you need this patcher, then follow these steps:
- Client:
    1. Go to the *Installations* tab in the launcher
    2. Click on the **three dots** on the **right** side of the version you'd like to use
    3. Click *Edit*
    4. Scroll down and click on *More Options*
    5. Add `-javaagent:path/to/log4noshell-0.3-SNAPSHOT-shaded.jar` to the *JVM Arguments* text-field
        - Example: `-javaagent:path/to/log4noshell-0.3-SNAPSHOT-shaded.jar -Xms2G -Xms2G...`
    6. Click *Save*
- Server:
    1. Add `-javaagent:path/to/log4noshell-0.3-SNAPSHOT-shaded.jar` somewhere before the `-jar` in your launch/start/run command
        - Example: `java -javaagent:path/to/log4noshell-0.3-SNAPSHOT-shaded.jar -jar server.jar`