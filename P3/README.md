# P3 - System Integration

## Group #IkkeForLangt

<br>
<br>

# Homework Task

UDP protocol provides faster transmission of messages, and therefore is a preferable for transmiting large, binary and video-streeming data.

Modify the program above, so it can be applied for sending and receiving image data from a binary file.

# How to

- open project and go to [/src/udp](https://github.com/grem848/Soft2020Fall-SI/tree/master/P3/src/udp)
- run [UDPServer.java](https://github.com/grem848/Soft2020Fall-SI/blob/master/P3/src/udp/UDPServer.java) to run the server and await imgs
- set up UDPClient.java args
  - Right click file and click -> Edit 'UDPClient.main()'
  - find Program Arguments and add the path to your img
- connect as client by running [UDPClient.java](https://github.com/grem848/Soft2020Fall-SI/blob/master/P3/src/udp/UDPClient.java)
- enjoy sending imgs from client to server (imgs goto the imgs folder in the project)
