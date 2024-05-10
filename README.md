## OREutils Fabric
simple QoL mod for the **ORE minecraft server** now for **Fabric**
commands include:
* `/bl` - shows blocked username list.
* `/bl add [username]` - adds a username to the block list.
* `/bl remove [username]` - removes a username from the block list.
<!---
* `/bpos1 [x] [z]` - sets the first corner of a plot
* `/bpos2 [x] [z]` - sets the second corner of a plot
--->
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

in addition it also says "wb" automatically anytime someone not from your blocklist joins
this can be turned off with `/autowb false`