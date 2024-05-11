## OREutils Fabric
simple QoL mod for the **ORE minecraft server** now for **Fabric**
commands include:
* `/bl` - shows blocked username list.
* `/bl add [username]` - adds a username to the block list.
* `/bl remove [username]` - removes a username from the block list.

plot commands:
* `/plotcoords` - prints the current list of plot coordinates.
* `/plotcoords add [x1] [z1] [x2] [z2]` - adds a plot coordinate to the list.
* `/plotcoords remove "[x1,z1,x2,z2]"` - removes a plot coordinate from the list, the coordinate must  
be formatted just like the example, with quotes, square brackets, 4 numbers with commans in between with no spaces.

boolean config commands (running them without an arguement will print their value):
* `/kos [true/false] \ /kickonsight [true/false]` - sets kick on sight to true or false.
  * kick on sight will attempt to kick blocked players from any declared plot boundary set.
* `/hbp [true/false] \ /hideblockedplayers [true/false]` - sets hide blocked players to true or false.
* `/hbm [true/false] \ /hideblockedmessages [true/false]` - sets hide blocked messages to true or false.
* `/ntc [true/false] \ /nametagcensor [true/false]` - sets name tag censor to true or false.
  * name tag censor will censor the name tag of blocked players making it say "-CENSORED-" instead. 

it also adds autocompletion and shortening to server switching commands:
* `/build, /b`
* `/school, /s`
* `/competition, /comp`
* `/seasonal`

in addition the mod has an auto wb function that will say "wb" automatically when someone joins, with a random
delay (between 0 to 2 seconds).
* `/autowb [true/false]` - turns the auto wb feature on/off