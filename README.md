# Endcrystal-Shield
Properly implements shields being able to block endcrystal blasts, as they should; like every other explosion type.

Mojang CONFIRMED bug report: https://bugs.mojang.com/browse/MC-188247

How to compile:

1. Install maven https://maven.apache.org/download.cgi
2. Make any code adjustments you want
3. Open the main folder in command prompt and type "mvn package"
4. Retrieve compiled plugin jar from target/

Todo List:

1. Find more effective way to simulate a shield hit (to lower it) instead of creating a zombie and then removing it after
2. Implement respawn anchor and bed explosion shield blocking
