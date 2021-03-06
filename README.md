# KVServer

Key Value server that accepts the following commands:

1. PUT key, value
2. GET key

This server has been tested running. You can test this server using 
the netcat utility.

Please start program as follows :

1. Compile (mvn clean install)
2. Start the program (java -jar target/KVServer-1.1-SNAPSHOT.jar --help for help, java -jar target/KVServer-1.1-SNAPSHOT.jar -n 3)

Use netcat utility to type in commands: (or can use the Client API that is provided)

netcat localhost 7000
PUT USA WASHINGTON_DC
SUCCESS PUT (USA, WASHINGTON_DC)

EOR
PUT ENGLAND LONDON
SUCCESS PUT (ENGLAND, LONDON)

EOR
PUT JAPAN TOKYO
SUCCESS PUT (JAPAN, TOKYO)

EOR


When you are done typing in the commands, put in a sentinel to terminate:
netcat localhost 7000
PUT USA WASHINGTON_DC
SUCCESS PUT (USA, WASHINGTON_DC)

EOR
PUT ENGLAND LONDON
SUCCESS PUT (ENGLAND, LONDON)

EOR
PUT JAPAN TOKYO
SUCCESS PUT (JAPAN, TOKYO)

EOR
EOC ------------> Sentinel


This server uses an LRU to evict the oldest used entries. When you put in a 
bunc of entries, the lease used entry is evicted. When a GET is done on 
an evicted entry, an error i returned.

netcat localhost 7000                                                                  PUT USA WASHINGTON_DC
SUCCESS PUT (USA, WASHINGTON_DC)

EOR
PUT ENGLAND LONDON
SUCCESS PUT (ENGLAND, LONDON)

EOR
GET USA
SUCCESS GET (USA) = WASHINGTON_DC

EOR
PUT INDIA NEW_DELHI
SUCCESS PUT (INDIA, NEW_DELHI)

EOR
GET USA
FAILED. Key (USA) not found

EOR

