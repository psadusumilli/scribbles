HTTP persistent connection, also called HTTP keep-alive, or HTTP connection reuse, is the idea of using a single TCP connection to send and receive multiple HTTP requests/responses, as opposed to opening a new connection for every single request/response pair. 
The newer HTTP/2 protocol uses the same idea and takes it further to allow multiple concurrent requests/responses to be multiplexed over a single connection.

Under HTTP 1.0, there is no official specification for how keepalive operates. It was, in essence, added to an existing protocol. If the client supports keep-alive, it adds an additional header to the request:
Connection: keep-alive

In HTTP 1.1, all connections are considered persistent unless declared otherwise.[1] The HTTP persistent connections do not use separate keepalive messages, they just allow multiple requests to use a single connection. However, the default connection timeout of Apache httpd 1.3 and 2.0 is as little as 15 seconds[2][3] and just 5 seconds for Apache httpd 2.2 and above.[4][5] The advantage of a short timeout is the ability to deliver multiple components of a web page quickly while not consuming resources to run multiple server processes or threads for too long.[6]

Advantages[edit]
Lower CPU and memory usage (because fewer connections are open simultaneously).
Enables HTTP pipelining of requests and responses.
Reduced network congestion (fewer TCP connections).
Reduced latency in subsequent requests (no handshaking).
Errors can be reported without the penalty of closing the TCP connection.
These advantages are even more important for secure HTTPS connections, because establishing a secure connection needs much more CPU time and network round-trips.


http://httpd.apache.org/docs/current/mod/core.html#keepalive
