# log4j-patcher

Java Agent that disables Apache Log4J's JNDI Lookup.

## How to Use

Download the [log4j-patcher](https://alerithe.github.io/log4j-patcher/log4j-patcher-0.1.jar) JAR

To use Java Agents, you specify them with the `-javaagent` JVM argument.

Example: `java -jar -javaagent:path/to/MyAgent.jar YourApplication.jar`

### ***Minecraft***

(Log4J-Patcher is NOT needed for versions >= 1.12)

- Client Steps:
    - Go to the *Installations* tab in the launcher
    - Click on the **three dots** on the **right** side of the version you'd like to use
    - Click *Edit*
    - Scroll down in that menu and click on *More Options*
    - Add `-javaagent:log4j-patcher-0.1.jar` to the *JVM Arguments* text-field
    - Click *Save*
- Server Steps:
    - If you can, add `-javaagent:log4j-patcher-0.1.jar` right before the `-jar` in your launch/start/run command (
      ex: `java -javaagent:log4j-patcher-0.1.jar -jar SpigotServer.jar --nogui`)