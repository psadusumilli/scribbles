High Performance Browser Networking
________________________________________________________________________________________________________________________________________________________________________
CHAPTER 0: General:
***********************
OSI
****
Physical(wire)->Datalink()->Network(IP)->Transport(TCP)->Session(TLS)->Application(HTTP)

Bandwith
***********
It is dataflow rate=100Mb/s which is dependent more on medium (optic, copper), technology.

RTT:
****
It is roundtrip time which is a product of distance,bandwidth,intermediate stops.

IP
***
is layer below TCP/UDP, has the primary task of delivering datagrams from the source to the destinationhost based on their addresses. 
To do so, the messages are encapsulated within an IP packet which identifies the source and the destination addresses, as well as a number of other routing parameters.
If packets are dropped, its the responsibility of above protocol like TCP to compensate.

netstat command:
********************
netstat -a 	= all ports
netstat -at 	= all tcp ports
netstat -au 	= all udp ports
netstat -l	= all listening ports
netstat -lt 	= all listening tcp ports
netstat -s 	= show statistics
netstat -p 	= show process
netstat -ap | grep ssh = show port used bu processs say ssh

tcpdump:
***********
tcpdump host vijayrc.com > 1.txt => start browsing vijayrc.com, capture all tcp trafffic into a text file
tcpdump src vijayrc.com > 1.txt => capture traffic from src vijayrc.com and not from ur browser
tcpdump dst vijayrc.com > 1.txt => capture traffic from browser and not from vijayrc.com
tcpdump net 1.2.3.0/24 => capure over IP range
tcpdump udp => capture udp alone, works for tcp, icmp
tcpdump port 80 => capture for port 80 alone
tcpdump src port 1025 
tcpdump dst port 389
tcpdump src port 1025 and tcp 
tcpdump portrange 21-23
tcpdump less 32 => packets lesser than 32bytes
tcpdump greater 128 => packets greater than 128bytes

//capture and read file
tcpdump -s 1514 port 80 -w capture_file
tcpdump -r capture_file

//combo || &&
tcpdump -nnvvS and src 10.5.2.3 and dst port 3389
tcpdump -nvvXSs 1514 dst 192.168.0.2 and src net and not icmp
tcpdump 'src 10.0.2.4 and (dst port 3389 or 22)'



ICMP: Internet Control Message Protocol
*******************************************
 It is used by network devices, like routers, to send error messages indicating, for example, that a requested service is not available or that a host or router could not be reached. ICMP can also be used to relay query messages.[1] It is assigned protocol number 1.[2] ICMP[3] differs from transport protocols such as TCP and UDP in that it is not typically used to exchange data between systems, nor is it regularly employed by end-user network applications (with the exception of some diagnostic tools like ping and traceroute).
For example, every device (such as an intermediate router) forwarding an IP datagram first decrements the time to live (TTL) field in the IP header by one. If the resulting TTL is 0, the packet is discarded and an ICMP Time To Live exceeded in transit message is sent to the datagram's source address.
Although ICMP messages are contained within standard IP packets, ICMP messages are usually processed as a special case, distinguished from normal IP processing, rather than processed as a normal sub-protocol of IP. In many cases, it is necessary to inspect the contents of the ICMP message and deliver the appropriate error message to the application that generated the original IP packet, the one that sent the packet that prompted the sending of the ICMP message.

____________________________________________________________________________________________________________________________________
NAT:Network Address Translation 
********************************
Given that IP4 is 32 bits, 2^32 is the max number of unique IP addresses globally
IP6 is in progress
NAT is a temp hack which became permanent, its like a telephone operator in your office.
One front-facing globally unique IP/port for LAN (office ntwk) and a table of local IPs./ports (for each employee)

Static NAT:
***********
Mapping an unregistered IP address to a registered IP address on a one-to-one basis. 
Particularly useful when a device needs to be accessible from outside the network.
 
Dynamic NAT:
************
Maps an unregistered IP address to a registered IP address from a group of registered IP addresses.

Overloading:
************
A form of dynamic NAT that maps multiple unregistered IP addresses to a single registered IP address by using different ports. 
This is known also as PAT (Port Address Translation), single address NAT or port-level multiplexed NAT.
 
Overlapping:
************
When the IP addresses used on your internal network are registered IP addresses in use on another network, the router must maintain a lookup table of these addresses so that it can intercept them and replace them with registered unique IP addresses. 
____________________________________________________________________________________________________________________________________________
CHAPTER 2: Networking 101
******************************
1) DELAYS:
***********
    	Probagation delay:    	
        Amount of time required for a message to travel from the sender to receiver, which is a function of distance over speed with which the signal propagates. 
	Propagation time is dictated by the distance and the medium through which the signal travels, the propagation speed is usually within a small constant factor of the speed of light

    	Transmission delay:
        Amount of time required to push all the packet’s bits into the link, which is a function of the packet’s length and data rate of the link. 
	Tansmission delay is dictated by the available data rate of the transmitting link and has nothing to do with the distance between the client and the server. 
        As an example, let’s assume we want to transmit a 10 Mb file over two links: 1 Mbps and 100 Mbps. 
	It will take 10 seconds to put the entire file on the “wire” over the 1 Mbps link and only 0.1 seconds over the 100 Mbps link.

	Processing delay:
	Amount of time required to process the packet header, check for bit-level errors, and determine the packet’s destination. Happens at routers, intermediate hops.
	Queuing delay:
	Amount of time the incoming packet is waiting in the queue until it can be processed. Waiting before routers.
____________________________________________________________________________________________________________________________________________
2) LAST MILE LATENCY:
***********************
	Its the last link between ISP and home/office, which is the slowest.
	traceroute google.com
3) BANDWIDTH:
*****************
	Optical fibers have a distinct advantage when it comes to bandwidth 
	because each fiber can carry many different wavelengths (channels) of light through a process known as wavelength-division multiplexing (WDM). 
	Hence, the total bandwidth of a fiber link is the multiple of per-channel data rate and the number of multiplexed channels.

	The backbones, or the optic fiber links, that form the core data paths of the Internet are capable of moving hundreds of terabits per second. 
	However, the available capacity at the 	edges of the network is much, much less, and varies wildly based on deployed technology: 
	dial-up, DSL, cable, a host of wireless technologies, fiber-to-the-home, and even the performance of the local router. 
____________________________________________________________________________________________________________________________________________
CHAPTER 2: Building blocks of TCP/IP
***************************************
	IP: Internet Protocol is what provides the host-to-host routing and addressing.

	TCP: Transmission Control Protocol, is what provides the abstraction of a reliable network running over an unreliable channel.
	When you work with a TCP stream, you are guaranteed that all bytes sent will be identical with bytes received and that they will arrive in the same order to the client. 
	As such, TCP is optimized for accurate delivery, rather than a timely one.

	HTTP standard never specified TCP as underlying protocol, UDP could've be chosen.

	TCP HANDSHAKE:
	******************
	Every TCP connection starts with 3 step handshake:
	STEP1: SYN: Client picks a random sequence number x and sends a SYN packet, which may also include additional TCP flags and options.
	STEP2: SYN ACK: Server increments x by one, picks own random sequence number y, appends it own set of flags and options, and dispatches the response.
	STEP3: ACK:Client increments both x and y by one and completes the handshake by dispatching the last ACK packet in the handshake

	The delay(58ms London-NY) imposed by the three-way handshake makes new TCP connections expensive to create.
	It is one of the big reasons why connection reuse is a critical optimization for any application running over TCP.

	TCP Fast Open (TFO) is a mechanism, which allows data transfer within the SYN packet, could decrease HTTP transaction network latency
	Congestion Collapse can happen due to resistance between IP (DATAGRAM PROTOCOL) and TCP, leading to servers emiiting multiple copies of same packet.

	FLOW CONTROL:
	*****************
	Flow control is a mechanism to prevent the sender from overwhelming the receiverwith data it may not be able to process.
	The receiver may be busy, under heavy load, or	may only be willing to allocate a fixed amount of buffer space. 
	To address this, each side	of the TCP connection advertises its own receive window (rwnd), which communicates the size of the available buffer space to hold the incoming data.
	TCP connection lifecycle with Slow start to avoid congestion which in turn leads to packet loss.
	TCP primary ambition is to avoid packet loss thus leads to a slow startup.

	3 step handshake (SYN, SYN_ACK, ACK)
	Receive Window
	Congestion Window
	Slow start (doubling congestion window for every round trip RTT)

	Receive Window:[rwnd]
	**********************
	Original TCP allowed only 16bits for window size, but RFC 1323 changed it to 1GB
	Intermediate routers, hubs can strip this option off

	To view/setup this option in linux
	$> sysctl net.ipv4.tcp_window_scaling 
	$> sysctl -w net.ipv4.tcp_window_scaling=1

	Congestion Window:[cwnd]
	**************************
	Sender-side limit on the amount of data the sender can have in flight before receiving an acknowledgment (ACK) from the client.
	To start, the server initializes a new congestion window (cwnd) variable per TCP connection and sets its initial value to a conservative, system-specified value (initcwnd on Linux).

	The maximum amount of data in flight (not ACKed) between the client and the server is the minimum of the rwnd and cwnd variables

	Slow start:
	************
	The maximum amount of data in flight for a new TCP connection is the minimum of the rwnd and cwnd values; hence the server can send up to four network segments to
	the client, at which point it must stop and wait for an acknowledgment. Then, for every received ACK, the slow-start algorithm indicates that the server can increment its cwnd window size by one segment—for every ACKed packet, two new packets can be sent.
	This phase of the TCP connection is commonly known as the “exponential growth” algorithm, as the client and the server are trying to quickly converge on
	the available bandwidth on the network path between them.

	Time taken to reach cwnd size of N = (round trip time)RTT × log2( N/initial cwnd)

	Example:
	• Client and server receive windows: 65,535 bytes (64 KB)
	• Initial congestion window: 4 segments (RFC 2581) (now 10 segments-RFC 6928-Apr 2013)
	• Roundtrip time: 56 ms (London to New York)

	65,535 bytes/1, 460 bytes ≈ 45 segments
	Time to reach congestion window of 45 segments = 56 ms × log2(45/4 ) = 224 ms

	4RTT & 224ms to reach 64 KB of throughput between the client and server! 
	The fact that the client and server may be capable of transferring at Mbps+ data rates has no effect—that’s slow-start.

	Slow start is OK for large streaming videos but bad for short bursts like page navigation.

	(SSR)
	******
	SSR mechanism, resets the congestion window of a connection after it has been idle for a defined period of time. 
	The rationale is simple: the network conditions may have changed while the connection has been idle, and to avoid congestion, the window is reset to a “safe” default.

	To disable in server:
	• $> sysctl net.ipv4.tcp_slow_start_after_idle
	• $> sysctl -w net.ipv4.tcp_slow_start_after_idle=0


	FULL FLOW FOR A GET REQUEST FOR A  20KB FILE = 19 segments
	***********************************************************
	0 ms 	Client begins the TCP handshake with the SYN packet.
	28 ms 	Server replies with SYN-ACK and specifies its rwnd size.
	56 ms 	Client ACKs the SYN-ACK, specifies its rwnd size, and immediately send the HTTP GET request.
	84 ms 	Server receives the HTTP request.
	124 ms 	Server completes generating the 20 KB response and sends 4 TCP segments before pausing for an ACK (initial cwnd size is 4).
	152 ms 	Client receives 4 segments and ACKs each one.
	180 ms 	Server increments its cwnd for each ACK and sends 8 segments.
	208 ms 	Client receives 8 segments and ACKs each one.
	236 ms 	Server increments its cwnd for each ACK and sends remaining 3 segments.
	264 ms 	Client receives remaining segments, ACKs each one.
 
 	PRR
 	****
	Proportional Rate Reduction (PRR) is a new algorithm specified by RFC 6937, whose goal is to improve the speed of recovery when a packet is lost.
	Developed in Google, now default in linux 3.2+

	Bandwidth-delay product (BDP)
	******************************
	Product of data link’s capacity and its end-to-end delay. 
	The result is the maximum	amount of unacknowledged data that can be in flight at any point in time.
	If either the sender or receiver are frequently forced to stop and wait for ACKs for previous packets, then this would create gaps in the data flow

	Ex1:
	If rwnd=cwnd=16KB RTT=100ms=0.1s
	Max datarate= 16*1024*8/0.1s=1310720 bits/s = 1.31Mb/s

	Ex2:
	If datarate=10Mb/s, RTT=0.1s
	rwnd, cwnd=10*1024*8*0.1=122.1KB

	Head of line blocking:
	***********************
	TCP assigns sequence number to each packet sent from server.
	client sorts them, but if a packet goes missing, the client has to wait to get the full assembly.

	Packet loss:
	*************
	It is necessary as a feedback mechanism in TCP to tune the windows, delivery rate. 
	But scenarios where packet order does not matter do not require TCP
		Audio/Video missing a very small gap
		Computer games missing a previous state packet
		messages where a latest message overrides other
		
	Recommendations to tune TCP
	**********************************
	"Upgrade to latest kernel": 
		simplest bcos they r tuned perfectly.
	“Increasing TCP’s Initial Congestion Window”: 
		A larger starting congestion window allows TCP transfers more data in the first roundtrip and significantly accelerates the window growth—an especially critical optimization for bursty and short-lived connections.
	“Slow-Start Restart”:
		Disabling slow-start after idle will improve performance of long-lived TCP connections, which transfer data in bursts.
	“Window Scaling (RFC 1323)”:
		Enabling window scaling increases the maximum receive window size and allows high-latency connections to achieve better throughput.
	“TCP Fast Open”:
		Allows application data to be sent in the initial SYN packet in certain situations.
		TFO is a new optimization, which requires support both on client and server; investigate if your application can make use of it.

	:$> ss --options --extended --memory --processes --info	

_____________________________________________________________________________________________________________________________________________________________
CHAPTER 3: UDP- User Datagram Protocol:
**********************************************
Came in 1980 after TCP/IP
Chose to omit many features of TCP/IP
Usually called Unreliable Datagram Protocol
Used in DNS
Become first class browser protocol after WebRTC (Web Real Time Communication) (skype)

Datagram
************
A self-contained, independent entity of data carrying sufficient information to be routed from the source to the destination nodes without reliance on earlier exchanges
between the nodes and the transporting network.

NAT & UDP:
*************
The issue with NAT translation, at least as far as UDP is concerned, is precisely the routing table that it must maintain to deliver the data. NAT middleboxes rely on connection
state, whereas UDP has none.
Delivering outbound UDP traffic does not require any extra work, but routing a reply requires that we have an entry in the translation table, which will tell us the IP and port
of the local destination host. Thus, translators have to keep state about each UDP flow, which itself is stateless.

Mechanisms to help:

	STUN: Session Traversal Utilities for NAT [RFC 5389]
	******************************************************
	With this mechanism in place, whenever two peers want to talk to each other over UDP,they will first send binding requests to their respective STUN servers.
	The STUN servers return their respective public IPs over all NATS in their path.
	Now the peers can then use the established public IP and port tuples to exchange data.

	TURN: Traversal Using Relays around NAT protocol [RFC 5766]
	*************************************************************
	Both clients begin their connections by sending an allocate request to the same	TURN server, followed by permissions negotiation.
	Once the negotiation is complete, both peers communicate by sending their data to the TURN server, which then relays it to the other peer.
	Running a relay server is costly, best used as a fallback like 8% of transactions.
_____________________________________________________________________________________________________________________________________________________________

CHAPTER 4: TLS-SSL (Transport layer Security)
*********************************************
Originally from NetScape as SSL2.0/3.0, but IETF formally made it TLS.
TLS runs on top of TCP, 
DTLS The Datagram Transport Layer Security protocol, defined in RFC 6347, runs on top of UDP
SSL is not computationally expensive anymore, even google runs it on commodity hardware, given their user loads.

Encryption
***********
	A mechanism to obfuscate what is sent from one computer to another. Agreed upon public-private key to encrypt content.
Authentication
**************
	A mechanism to verify the validity of provided identification material. 3rd party like VeriSign to declare that this certificate is from 'Bank of America'.
	Chain of Trust.
Integrity
*********
	A mechanism to detect message tampering and forgery. Checksum created from payload from agreed-upon key using TLS in-built MAC(Message Authentication Code) algorithm.

TLS Handshake:
*****************
1| Regular TCP SYN,SYN-ACK,ACK
2| The client sends the server the client SSL version number, cipher settings, session-specific data, and other information that the server needs to communicate with the client using SSL.
3| The server sends the client the server SSL version number, cipher settings, session-specific data, and other information that the client needs to communicate with the server over SSL. The server also sends its own certificate, and if the client is requesting a server resource that requires client authentication, the server requests the client certificate.
4| The client uses the information sent by the server to authenticate the server—e.g., in the case of a web browser connecting to a web server, the browser checks whether the received certificate subject name actually matches the name of the server being contacted, whether the issuer of the certificate is a trusted certificate authority, whether the certificate has expired, and, ideally, whether the certificate has been revoked.[7] If the server cannot be authenticated, the user is warned of the problem and informed that an encrypted and authenticated connection cannot be established. If the server can be successfully authenticated, the client proceeds to the next step.
5| Using all data generated in the handshake thus far, the client (with the cooperation of the server, depending on the cipher in use) creates the pre-master secret for the session, encrypts it with the server public key (obtained from the server certificate, sent in step 2), and then sends the encrypted pre-master secret to the server.
6| If the server has requested client authentication (an optional step in the handshake), the client also signs another piece of data that is unique to this handshake and known by both the client and server. In this case, the client sends both the signed data and the client own certificate to the server along with the encrypted pre-master secret.
7| If the server has requested client authentication, the server attempts to authenticate the client. If the client cannot be authenticated, the session ends. If the client can be successfully authenticated, the server uses its private key to decrypt the pre-master secret, and then performs a series of steps (which the client also performs, starting from the same pre-master secret) to generate the master secret.
8| Both the client and the server use the master secret to generate the session keys, which are symmetric keys used to encrypt and decrypt information exchanged during the SSL session and to verify its integrity (that is, to detect any changes in the data between the time it was sent and the time it is received over the SSL connection).
9| The client sends a message to the server informing it that future messages from the client will be encrypted with the session key. It then sends a separate (encrypted) message indicating that the client portion of the handshake is finished.
10| The server sends a message to the client informing it that future messages from the server will be encrypted with the session key. It then sends a separate (encrypted) message indicating that the server portion of the handshake is finished.
11| Session Keys can help if the server cannot handle huge loads of unique sessionids, the data now is stored on the client side.	

ALPN:
*******
Since HTTP/HTTPS is prevalent, new extensions like SPDY. WebSockets need to piggyback on them until the intermediary devices like router/hub become compatible.
Application Layer Protocol Negotiation (ALPN) is a TLS extension that introduces support for application protocol negotiation into the TLS handshake
(Figure 4-2), thereby eliminating the need for an extra roundtrip required by the HTTP Upgrade workflow. 
Specifically, the process is as follows:
The client appends a new ProtocolNameList field, containing the list of supported application protocols, into the ClientHello message.
The server inspects the ProtocolNameList field and returns a ProtocolName field indicating the selected protocol as part of the ServerHello message.

NPN: Next Protocol Negotiation
******************************
Next Protocol Negotiation (NPN) is a TLS extension, which was developed as part of the SPDY effort at Google to enable efficient application protocol negotiation during the TLS handshake. 
ALPN is a revised and IETF approved version of the NPN extension. 
In NPN, the server advertised which protocols it supports, and the client then chose and confirmed the protocol. 
In ALPN, this exchange was reversed: the client now specifies which protocols it supports, and the server then selects and confirms the protocol.
The rationale for the change is that this brings ALPN into closer alignment with other protocol negotiation standards.
In other words, ALPN is a successor to NPN, and NPN is deprecated. 

SNI:Server Name Indication
*****************************
'Server Name Indication' is the means by which a single server can host multiple TLS handshakes of web sites with different host names.
Allows the client to indicate the hostname the client is attempting to connect to at the start of the handshake. 
As a result, a web server can inspect the SNI hostname, select the appropriate certificate, and continue the handshake.
Similar to hostname in request headers.

Chain of Trust:
***************
Verifying the chain of trust requires that the browser traverse the chain, starting from the site certificate, and recursively verifying the certificate of the parent until it reaches a trusted root.
Every browser ships with a preinitialized list of trusted certificate authorities (“roots”)

CRL: Certification Revocation List
***********************************
Certificate Revocation List (CRL) is defined by RFC 5280 and specifies a simple mechanism to check the status of every certificate: each certificate authority maintains and
periodically publishes a list of revoked certificate serial numbers.Anyone attempting to verify a certificate is then able to download the revocation list and check the presence
of the serial number within it—if it is present, then it has been revoked.

OCSP: Online Certificate Status Protocol
*****************************************
Made to overcome CRL limitations, client looks up from this DB maintained by CA(Certifying authority) by looking up by serial no, instead of downloading all revoked serial numbers.

Early Termination:
******************
A nearby server can also terminate the TLS session, which means that the TCP and TLS handshake roundtrips are much quicker and the total connection setup latency is greatly
reduced. In turn, the same nearby server can then establish a pool of long-lived, secure connections to the origin servers and proxy all incoming requests and responses to and
from the origin servers. CDNs help in achieving this.

Compression:
************
Compression algorithm is negotiated during the TLS handshake, and compression is applied prior to encryption of each record.
But disable in practice, since its hackable, repeated compression on already compressed data like image/video.
_____________________________________________________________________________________________________________________________________________________________________________________________________

CHAPTER 9: HTTP HISTORY:
************************
In 1991, Berners-Lee outlined the motivation for the new protocol and listed several high-level design goals: file transfer functionality, ability to request an index search of
a hypertext archive, format negotiation, and an ability to refer the client to another server. 

HTTP 0.9
*********
• Client request is a single ASCII character string.
• Client request is terminated by a carriage return (CRLF).
• Server response is an ASCII character stream.
• Server response is a hypertext markup language (HTML).
• Connection is terminated after the document transfer is complete.

Telnet is the simplest HTTP client.

HTTP 1.0
*********
Introduced request/response headers and status codes. 
Both the request and response headers were kept as ASCII encoded, but the response object itself could be of any type: an HTML file, a plain text file, an image, or any other content type.
In addition to media type negotiation, the RFC also documented a number of other commonly implemented capabilities: content encoding, character set support, multipart types, authorization, caching, proxy behaviors, date formats, and more.

HTTP 1.1
*********
The HTTP 1.1 standard resolved a lot of the protocol ambiguities found in earlier versions and introduced a number of critical performance optimizations: 
keepalive connections, chunked encoding transfers, byte-range requests, additional caching mechanisms, transfer encodings, and request pipelining.

HTTP 2.0
********
The primary focus of HTTP 2.0 is on improving transport performance and enabling both lower latency and higher throughput. 
TCP need nort be the primary transport protocol.

_____________________________________________________________________________________________________________________________________________________________________________________________________
CHAPTER10: WEB PERFORMANCE:
***************************
PLT: Page Load time
********************
It is “the time until the loading spinner stops spinning in the browser.” A more technical definition is time to onload event in the browser

On Browser:
**************
HTML->DOM (Document Object Model)
	|   	
	JS		---> RENDER TREE -> LAYOUT -> PAINT
	|
CSS ->CSSOM (CSS Object Model) 
JS, DOM.CSSOM can interwine, deadlock and block, all based on the dependency graph.

• Streaming an HD video from the Yahoo! homepage is bandwidth limited.
• Loading and rendering the Yahoo! homepage is latency limited.

Measuring mechanisms:
*********************
Navigation API - caniuse.com/nav-timing.
Resource waterfall - webpagetest.com

Browsers do 
*************
Document-aware optimization - load critical resources first to get better user experience.
Speculative optimization - learn user patterns and pre-resolve DNS names

Recommendations:
*****************
• Critical resources such as CSS and JavaScript should be discoverable as early as possible in the document.
• CSS should be delivered as early as possible to unblock rendering and JavaScript execution.
• Noncritical JavaScript should be deferred to avoid blocking DOM and CSSOM construction.
• The HTML document is parsed incrementally by the parser; hence the document should be periodically flushed for best performance.
* Hint browser by
	<link rel="dns-prefetch" href="//hostname_to_resolve.com"> => Pre-resolve specified hostname.
	<link rel="subresource" href="/javascript/myapp.js"> => Prefetch critical resource found later on this page. 
	<link rel="prefetch" href="/images/big.jpeg"> => Prefetch resource for this or future navigation.
	<link rel="prerender" href="//example.org/next_page.html"> =>Prerender specified page in anticipation of next user destination.

__________________________________________________________________________________________________________________________________________________________________________________________________
CHAPTER 11: HTTP 1.1
******************
• Persistent connections to allow connection reuse (PRIMARY FEATURE) (single TCP connection to fetch html, required js, css)
• Chunked transfer encoding to allow response streaming
• Request pipelining to allow parallel request processing
• Byte serving to allow range-based resource requests
• Improved and much better-specified caching mechanisms	

Example: Consider a page load with N resources(js,css,html) from server
********
HTTP 1.0 					-> N TCP handshakes + N RTT
HTTP 1.1 KeepAlive 			-> 1 TCP handshake + N RTT
HTTP 1.1 KA+ Pipelining		-> 1 TCP handshake +  1 RTT for all N resources 
Server serial processing of resources 

head-of-line blocking:
**********************
Parallel processing (multiple threads) of N resources into response stream is not possible, 
So if one resource blocks in the pipeline, everyone is blocked.
This results in suboptimal delivery: underutilized network links, server buffering costs.

6 parallel TCP connections from browser:
******************************************
A gap in the HTTP 1.X protocol has forced browser vendors to introduce and maintain a connection pool of up to six TCP streams per host. 
The good news is all of the connection management is handled by the browser itself.
6 is number decided technically, also limited to prevent DOS attacks from single client.
Ironically, this same safety check enables the reverse attack on some browsers: 
if the maximum connection limit is exceeded on the client, then all further client requests are blocked. 
As an experiment, open six parallel downloads to a single host, and then issue a seventh request: it will hang until one of the previous requests has completed

Domain Sharding:
****************
Even 6 TCP connections is not enough, then fetch ur page resources from multiple hosts (sub.domains like static.vijayrc.com)

Concatenation
***************
Multiple JavaScript or CSS files are combined into a single resource 

Spriting
********
Multiple images are combined into a larger, composite image.

Both concatenation and spriting suffer in cache when changes happen to them.

Resource Inlining:
*******************
In practice, a common rule of thumb is to consider inlining for resources under 1–2 KB, as resources below this threshold often incur higher HTTP overhead than the resource itself.
<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAAAAACH5BAAAAAAALAAAAAABAAEAAAICTAEAOw=="alt="1x1 transparent (GIF) pixel" />
The image is part of the DOM now, so no caching, no separate request.

__________________________________________________________________________________________________________________________________________________________________________________________________
CHAPTER 12: HTTP 2.0
*********************
SPDY made in Google, targeted a 50% reduction in page load time (PLT)
Chrome & some browsers, some sites now support SPDY
SPDY was the catalyst for HTTP 2.0, but SPDY is not HTTP 2.0.

It is expected that HTTP/2.0 will:
	• Substantially and measurably improve end-user perceived latency in most cases, over HTTP 1.1 using TCP.
	• Address the “head of line blocking” problem in HTTP.
	• Not require multiple TCP connections to a server to enable parallelism, thus improving its use of TCP, especially regarding congestion control.
	• Retain the semantics of HTTP 1.1, leveraging existing documentation, including (but not limited to) HTTP methods, status codes, URIs, and where appropriate,header fields.
	• Clearly define how HTTP 2.0 interacts with HTTP 1.x, especially in intermediaries.
	• Clearly identify any new extensibility points and policy for their appropriate

Architecture:
****************
A Binary Framing layer is between TLS and HTTP which communicates in binary encoded messages instead of plaintext
HTTP Semantics do not change so applications can be blissfully unaware.

Stream, Messages and Frames
*********************************
• All communication is performed with a single TCP connection.
• The stream is a virtual channel within a connection, which carries bidirectional messages. Each stream has a unique integer identifier (1, 2, ..., N).
• The message is a logical HTTP message, such as a request, or response, which consists of one or more frames.
• The frame is the smallest unit of communication, which carries a specific type of data—e.g., HTTP headers, payload, and so on.
• Interleave multiple requests in parallel without blocking on any one
• Interleave multiple responses in parallel without blocking on any one
• Use a single  connection to deliver multiple requests and responses in parallel
• Deliver lower page load times by eliminating unnecessary latency
• Remove unnecessary HTTP 1.x workarounds from our application code
• Avoid Head of Line Blocking.
• Each stream can be assigned a 31-bit priority value: (0->[2^31-1]). DOM, CSSOM streams are typically assigned higher priority. 
• A http-2.0 implementing server will send the higher priority streams first
• ALPN - Application Layer Protocol Negotiation is again used to check if HTTP 2.0 can be used.
• HTTP2.0 will take another decade to full establish in Internet.

Flow Control in HTTP2.0:
*************************
• Is hop-by-hop, not end-to-end.
• Is based on window update frames: receiver advertises how many bytes it is prepared to receive on a stream and for the entire connection.
• Flow control window size is updated by a WINDOW_UPDATE frame, which specifies the stream ID and the window size increment value.
• Flow control is directional: receiver may choose to set any window size that it desires for each stream and for the entire connection.
• Flow control can be disabled by a receiver, both for an individual stream or for the entire connection.

Server Push
**************
A powerful new feature of HTTP 2.0 is the ability of the server to send multiple replies for a single client request. That is, in addition to the response for the original request,
the server can push additional resources to the client (Figure 12-4), without the client having to explicitly request each one!
For eg, a server need not wait to push css, js resources with just analysing the html requested by client.
Apache’s mod_spdy looks for X-Associated-Content header,which lists the resources to be pushed.

Header Compression
**********************
• Instead of retransmitting the same data on each request and response, HTTP 2.0 uses “header tables” on both the client and server to track and store previously sent key-value pairs.
• Header tables persist for the entire HTTP 2.0 connection and are incrementally updated both by the client and server.
• Each new header key-value pair is either appended to the existing table or replaces a previous value in the table.
__________________________________________________________________________________________________________________________________________________________________________________________________
CHAPTER 13: Optimising Application Delivery
**************************************************
Best practices like domain sharding, image spriting, multiple TCP connections ..etc applied  for HTTP 1.1 should NOT be applied for HTTP 2.0 
Look into mod_pagespeed of Apachex
__________________________________________________________________________________________________________________________________________________________________________________________________
 CHAPTER 5: Performance of Wireless Network
***************************************************
	Claude Shannon formula: C = BW*log2(1+S/N) 
		where 	C => Channel Capacity
				BW => Bandwidth in Hz
				S => Signal 	
Types of wireless networks
	1) Personal area network (PAN): Within reach of a person peripherals |Cable replacement for Bluetooth, ZigBee, NFC  
	2) Local area network (LAN): Within a building or campus Wireless extension of wired network| IEEE 802.11 (WiFi) 
	3) Metropolitan area network(MAN) : Within a city Wireless inter-network connectivity| IEEE 802.15 (WiMAX)
 	4) Wide area network (WAN): Worldwide Wireless network access| Cellular (UMTS, LTE, etc.)
 
* Doubling in available frequency range will double the data rate—e.g., going from 20 to 40 MHz of bandwidth can double the channel data rate,  which is exactly how 802.11n is improving its performance over earlier WiFi standards!
* Different countries may, and often do, assign different spectrum ranges to the same wireless technology.
* Low-frequency signals travel farther and cover large areas (macrocells), but at the cost of requiring larger antennas and having more clients competing for access. 
   On the other hand, high-frequency signals can transfer more data but won’t travel as far, resulting in smaller coverage areas (microcells) and a requirement for more infrastructure.
* Path loss, or path attenuation, is the reduction in signal power with respect to distance traveled—the exact reduction rate depends on the environment. 
Near-far problem
*******************
A condition in which a receiver captures a strong signal and thereby makes it impossible for the receiver to detect a weaker signal, effectively “crowding out” the weaker signal.
Cell-breathing
***************
A condition in which the coverage area, or the distance of the signal, expands and shrinks based on the cumulative noise and interference levels
Modulation:
**************
Modulation is the process of digital-to-analog conversion, and different “modulation alphabets” can be used to encode the digital signal with different efficiency. 
The combination of the alphabet and the symbol rate is what then determines the final throughput of the channel. 
__________________________________________________________________________________________________________________________________________________________________________________________________
 CHAPTER 6: WiFi
******************
* Technically, a device must be submitted to and certified by the WiFi Alliance to carry the WiFi name and logo, but in practice, the name is used to refer to any product based on the IEEE 802.11 standards.
* Operating in the unlicensed 2.4 GHz ISM band allowed anyone to easily provide a “wireless extension” to their existing local area network. 
* 802.3 is wire Ethernet , 802.11(b->n..) family is wireless---
* Ethernet Standard is based on 
	CSMA - Carrier Sense Multiple Access - Listen before you send, check if anyone is transmitting, then send
	CSMA/CD - Collision Detection - if a collision is detected, then both parties stop transmitting immediately and sleep for a random interval (with exponential backoff).
* WiFi relies on collision avoidance (CSMA/CA), where each sender attempts to avoid collisions by transmitting only when the channel is sensed to be idle, and then sends its full message frame in its entirety.
* 2.4 GHz band provides three non-overlapping 20 MHz radio channels: 1, 6, and 11

• WiFi provides NO bandwidth or latency guarantees or assignment to its users.
• WiFi provides variable bandwidth based on signal-to-noise in its environment.
• WiFi transmit power is limited to 200 mW, and likely less in your region.
• WiFi has a limited amount of spectrum in 2.4 GHz and the newer 5 GHz bands.
• WiFi access points overlap in their channel assignment by design.
• WiFi access points and peers compete for access to the same radio channel.

* Instead of direct TCP packet loss, you are much more likely to see higher variability in packet arrival times due to the underlying collisions and retransmissions performed by the lower link and physical layers.

 * adaptive bitrate streaming is used for long-lived streams such as video and audio content like Youtube., Netflix. They store multiple bit-rate versions of the same file again split into segments (5-10sec chunks), serving them dynamically as the bit rate changes.

* DTIM: delivery traffic indication message, used to maintain a live connection
__________________________________________________________________________________________________________________________________________________________________________________________________
 CHAPTER 7: Mobile Networks
********************************
Network|Desc|data rate| latency
1G | no data Analog systems
2G | First digital systems as overlays or parallel to analog systems | 100–400 Kbit/s | 300–1000 ms
3G | Mbit/s Dedicated digital networks deployed in parallel to analog systems | 0.5–5 Mbit/s | 100–500 ms
4G | Gbit/s Digital and packet-only networks | 1–50 Mbit/s |  < 100 ms

History:
*********
1979 	| 1G  from Japan, an analog system with no data.
1991 	| 2G from Finland based on GSMA
199* 	| GPRS (General Radio Packet Service)+2G => 2.5G
2003 	| EDGE (Enhanced Data rates for GSM Evolution) + 2G=>2.75G	 

Industry standards for RAN (Radio Access Network)
****************************************************
199* 	| ETSI (European Telecom Standards Institute) gave GSMA - 80->85%
		| IS 95 (US Qualcomm) 15%
		Both were incompatible

1998 	| 3GPP Org	=> 3G successor to GSMA			
		| 3GPP2  Org => 3G successor to IS 95 based on CDMA2000

ALL
****
Generation 		Organization 		Release
----------------------------------------------------------------------------------------------------------------------------------------
2G 				3GPP 				GSM
				3GPP2 				IS-95 (cdmaOne)
----------------------------------------------------------------------------------------------------------------------------------------
2.5/2.75G		3GPP 				GPRS, EDGE (EGPRS)
				3GPP2 				CDMA2000
----------------------------------------------------------------------------------------------------------------------------------------	
3G				3GPP 				UMTS
				3GPP2 				CDMA 2000 1x EV-DO Release 0
----------------------------------------------------------------------------------------------------------------------------------------	
3.5/3.75/3.9G 	3GPP 				HSPA, HSPA+, LTE (High Speed Packet Access)
				3GPP2 				EV-DO Revision A, EV-DO Revision B, EV-DO Advanced
----------------------------------------------------------------------------------------------------------------------------------------	
4G				3GPP 				LTE-Advanced, HSPA+ Revision 11+
									No 3GPP2, convergence happening here
----------------------------------------------------------------------------------------------------------------------------------------
 
4G Network
*************
Requirements of IMT-Advanced include the following:
• Based on an IP packet switched network
• Interoperable with previous wireless standards (3G and 2G)
• 100 Mbit/s data rate for mobile clients and Gbit/s+ when stationary
• Sub 100 ms control-plane latency and sub 10 ms user-plane latency
• Dynamic allocation and sharing of network resources between users
• Use of variable bandwidth allocation, from 5 to 20 MHz

LTE-Advanced is a standard that was specifically developed to satisfy all the IMT-Advanced criteria.
LTE-Advanced is still 3.9G as it does not satify all requirements.

LTE - Long Term Advanced
****************************
• All IP core network
• Simplified network architecture to lower costs
• Low latencies in user (<10 ms) and control planes (<100 ms)
• New radio interface and modulation for high throughput (100 Mbps)
• Ability to use larger bandwidth allocations and carrier aggregation
• MIMO as a requirement for all devices

HSPA+ is Leading Worldwide 4G Adoption
********************************************
HSPA+ was first introduced in 3GPP release 7, back in 2007, is widely implemented by carriers owing to the cost effectiveness with incremental updates instead of radically changing infrastructure for 4G.

MIMO: Multiple Input Multiple Output , LTE depends on UE (User Equipment), smartphones can have 2-4 radios unlike older phones with 1 radio

RRC: Radio Resource Controller:
**********************************  
	* assumes full responsibility over scheduling of who talks when, allocated bandwidth, the signal power used, the power state of each device, and a dozen other variables.
	* In LTE, each radio base station (eNodeB) hosts the RRC, which maintains the RRC state machine and performs all resource assignment for each active user in its cell.
	* in LTA state machine, 
		RRC Idle [ low-power state (<15 mW) and only listening to control traffic ], 
		RRC Connected [Device radio is in a high-power state (1000–3500 mW) while it either transmits data or waits for data]
			| Continuous reception: Highest power state, established network context, allocated network resources.
			| Short Discontinuous Reception (Short DRX):  Established network context, no allocated network resources.
			| Long Discontinuous Reception (Long DRX): Established network context, no allocated network resources.
	* in 3G-HSPA state machine
		Idle: The device radio is in a low-power state and only listening to control traffic from the network
		Cell DCH: The device is in a high-power state, and network resources are assigned both for upstream and downstream data transfer.
		Cell FACH: An intermediate power state, which consumes significantly less power than DCH, no dedicated network resources, still transfers data at low rate.
	* in CDMA
		Idle: The device radio is in a low-power state and only listening to control traffic from the network. 
		Connected:  The device is in a high-power state and network resources are assigned for both upstream and downstream data transfers.
	* Why RRC state machine is important?
		Pandora pushes the entire song in one burst thereby leaving radio device free while it plays song, which is good for radio usage.
		But Pandora pings every 60s for usage analytics, this brings the radio from idle to active, leading to latency/battery costs

LTE Architecture:
******************
Radio Access Network: User device moves from one tower to the other based on tracking location, this is called Tower Handoffs.
Core Network: Also called EPC (Evolved Packet Core) connects the radio network to the public Internet.
Consists of 
	SWG
	*****
	Service Gateway				
	MME
	*****
	The Mobility Management Entity component is effectively a user database, which manages all the state for every user on the network: 
	their location on the network, type of account, billing status, enabled services, plus all other user metadata.
	PCRF
	******
	Policy and Charging Rules Function (PCRF) component is responsible for maintaining and evaluating these rules for the packet gateway
	PGW -> Packet gateway (PGW)
	*********************************
	It is the public gateway that connects the mobile carrier to the public Internet. 
	The PGW is the termination point for all external connections, regardless of the protocol. 
	When a mobile device is connected to the carrier network, the IP address of the device is allocated and maintained by the PGW
	PGW acts as a NAT assigning dynamic public IPs to devices
Flow:
*****
• Data arrives at the external packet gateway, which connects the core network to the public Internet.
• A set of routing and packet policies is applied at the packet gateway.
• Data is routed from the public gateway to one or more serving gateways, which act as mobility anchors for the devices within the radio network.
• A user database service performs the authentication, billing, provisioning of services, and location tracking of each user on the network.
• Once the location of the user within the radio network is determined, the data is routed from the serving gateway to the appropriate radio tower.
• The radio tower performs the necessary resource assignment and negotiation with the target device and then delivers the data over the radio interface.

Latency:
*********
	#OUTBOUND data flow: A  user initiating a new request incurs several different latencies:
	Control-plane latency=Fixed, one-time latency cost incurred for RRC negotiation and state transitions: <100 ms for idle to active, and <50 ms for dormant to active.
	User-plane latency=Fixed cost for every application packet transferred between the device and the radio tower: <5 ms.
	Core network latency=Carrier dependent cost for transporting the packet from the radio tower to the packet gateway: in practice, 30–100 ms.
	Internet routing latency=Variable latency cost between the carrier’s packet gateway and the destination address on the public Internet
	
	#INBOUND data flow: network to user device
	Step1:	First packet:	Network->PGW->SGW->MME sends paging-message to get device location->SGW
	Step2: 	Context established, further communication between device and SGW only
______________________________________________________________________________________________________________________________________________
CHAPTER 8- Optimising for Mobile Networks:
*************************************************
Radio is second only to screen in battery power usage.
Radio usage is non-linear with data 
Radio power is increasing with poweful spectrum

• Polling is exceptionally expensive on mobile networks; minimize it.
• Where possible, push delivery and notifications should be used.
• Outbound and inbound requests should be coalesced and aggregated.
• Noncritical requests should be deferred until the radio is active
• Content pre-fetching to avoid latency
______________________________________________________________________________________________________________________________________________
CHAPTER 14- Primer on Browser Networking
*************************************************
Socket Management:
**********************
Sockets are organized in pools (Figure 14-2), which are grouped by origin, and each pool enforces its own connection limits and security constraints. 
Pending requests are queued, prioritized, and then bound to individual sockets in the pool. Consequently, unless the server intentionally closes the connection, the same socket can be automatically reused across multiple requests!
In practice, all major browsers limit the maximum pool size to six sockets.
Chrome does speculative optimisation by observing user patterns. (dns prefetching, tcp pre-connect, pre-render pages..)

Security:
*********
Prevention of web apps making raw socket access 
Same origin policy
TLS handshake
Connection limits to avoid resource starving attacks on both server/client side.
______________________________________________________________________________________________________________________________________________
CHAPTER 15- XMLHttpRequest
*********************************
AJAX apps depend on this API.
Outlook Web Access developed this API, XML got nothing to with this API, name tag just got attached for historical release reasons.
Strict on making sure crossdomain calls being not made, some headers are prohibited to be changed by user.
Does not support request streaming but you can slice you file upload into chunks and still send it
Does not support complete response streaming like reading parital data from server but a workaround exists with responseText for text-only data
Both streams are not efficient, so wait for Streams API.

Polling is the most common use case, and HTTP is one-way from client to server. It incurs the usual latency cost and a anti-pattern in wireless.
The common fault of short-polling with minimum interval would result in high load on servers, even with the basic http headers in the payload.
code:
******
	var blob = ...;
	const BYTES_PER_CHUNK = 1024 * 1024;
	const SIZE = blob.size;
	var start = 0;
	var end = BYTES_PER_CHUNK;
	while(start < SIZE) {
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/upload');
		xhr.onload = function() { ... };
		xhr.setRequestHeader('Content-Range', start+'-'+end+'/'+SIZE);
		xhr.send(blob.slice(start, end));
		start = end;
		end = start + BYTES_PER_CHUNK;
	}

Long-Polling (aka Comet, ajax reverse push, server push) is wait/hold on to a connection until a real update happens on the server side then push it to client.
Facebook Chat used long polling in 2008 before switching over to SSE and WebSockets.
code:
******
	function checkUpdates(url) {
		var xhr = new XMLHttpRequest();
		xhr.open('GET', url);
		xhr.onload = function() { ... };
		xhr.send();
	}
	setInterval(checkUpdates('/updates'), 60000);
______________________________________________________________________________________________________________________________________________
CHAPTER 16- Server Side Events
*********************************
Under the hood, its a good implementation of long-polling XHR

var source = new EventSource("/path/to/stream-url");
source.onopen = function () { ... };
source.onerror = function () { ... };
source.addEventListener("foo", function (event) {processFoo(event.data);}); // act on custom event 'foo'
source.onmessage = function (event) {
	log_message(event.id, event.data);
	if (event.id == "CLOSE") {source.close();}	
}

IE and android browser does not support, use a polyfill which is not as efficient as EventSource
Event Stream Protocol
**************************
An SSE event stream is delivered as a streaming HTTP response: the client initiates a regular HTTP request, the server responds with a custom “text/event-stream” content-
type, and then streams the UTF-8 encoded event data.
Gzip supported
SE provides built-in support for reestablishing dropped connections, as well as recovery of messages the client may have missed while disconnected.

=> Request
	GET /stream HTTP/1.1
	Host: example.com
	Accept: text/event-stream
<= Response
	HTTP/1.1 200 OK
	Connection: keep-alive
	Content-Type: text/event-stream
	Transfer-Encoding: chunked
	retry: 15000

//msg with just text data
	data: First message is a simple string.
	data: {"message": "JSON payload"}

//msg with event/data
	event: foo
	data: Message of type "foo"

//message with id,event, multi-line data
	id: 42
	event: bar
	data: Multi-line message of
	data: type "bar" and id "42"

//msg again
	id: 43
	data: Last message, id "43"
The server can also associate an arbitrary ID string with each message. The browser automatically remembers the last seen ID and will automatically append a
“Last-Event-ID” HTTP header with the remembered value when issuing a reconnect request. 

Limitations:
**************
Flow from server to client only
specifically designed to transfer UTF-8 data: binary streaming, while possible, is inefficient
Again watch out for wireless networks


______________________________________________________________________________________________________________________________________________
CHAPTER 17- Web Sockets
***********************

WebSocket enables bidirectional, message-oriented streaming of text and binary data between client and server.
• Connection negotiation and same-origin policy enforcement
• Interoperability with existing HTTP infrastructure
• Message-oriented communication and efficient message framing
• Subprotocol negotiation and extensibility

code:
#####
var ws = new WebSocket('wss://example.com/socket');
	ws.onerror = function (error) { ... }
	ws.onclose = function () { ... }
	ws.onopen = function () {
	ws.send("Connection established. Hello server!");
}
ws.onmessage = function(msg) {
	if(msg.data instanceof Blob) {
		processBlob(msg.data);
	} else {
		processText(msg.data);
	}
}

Android browser does not support, SockJS and Socket.IO are alternatives
ws:// - text based
wss:// - tls based
Supports text (DOMObject) and binary transfers(BlobObject/ArrayBuffer)

ArrayBuffer:
*************
Server:
#######
struct someStruct {
	char username[16];
	unsigned short id;
	float scores[32];
};

Client:
#######
ws.onmessage = function(msg){
	var buffer = msg.data;
	var usernameView = new Uint8Array(buffer, 0, 16);
	var idView = new Uint16Array(buffer, 16, 1);
	var scoresView = new Float32Array(buffer, 18, 32);


	console.log("ID: " + idView[0] + " username: " + usernameView[0]);
	for (var j = 0; j < 32; j++) { 
		console.log(scoresView[j]) 
	}
}

Method send() is asynchronous, use the WebSocket.bufferedAmt method to check if full upload is done.
Does not have req/response headers, so you got use to your own metadata mechanism
Works on top of HTTP 80/443 but not limited ot HTTP

Binary Framing:
*****************
WebSocket uses a custom 'Binary framing format (Figure 17-1), which splits each application message into one or more frames, transports them to the destination, reassembles them, and finally
notifies the receiver once the entire message has been received.
Frame: The smallest unit of communication, each containing a variable-length frame header and a payload that may carry all or part of the application message.
Message: A complete sequence of frames that map to a logical application message
Head of line blocking will still occur when multiple messages are bundled together.
WebSocket needs a dedicated TCP connection while in future multplexing can solve this problem.

subprotocol negotiation
************************
var ws = new WebSocket('wss://example.com/socket',
['appProtocol', 'appProtocol-v2']);
ws.onopen = function () {
if (ws.protocol == 'appProtocol-v2') {
...
} else {
...
}

Deploying:
**********
Piggybacking on HTTPS is the safest for WebSockets as none of the intermediate routers, hubs can block it.
But timeouts by servers, routers on long-held TCP connections by WebSockets is an issue

Performance Checklist:
*********************
• Use secure WebSocket (WSS over TLS) for reliable deployments.
• Pay close attention to polyfill performance (if necessary).
• Leverage subprotocol negotiation to determine the application protocol.
• Optimize binary payloads to minimize transfer size.
• Consider compressing UTF-8 content to minimize transfer size.
• Set the right binary type for received binary payloads.
• Monitor the amount of buffered data on the client.
• Split large application messages to avoid head-of-line blocking.
• Leverage other transports where applicable.

______________________________________________________________________________________________________________________________________________
CHAPTER 18 - WebRTC	 
*******************
Web Real-Time Communication (WebRTC) is a collection of standards, protocols, and JavaScript APIs, the combination of which enables peer-to-peer audio, video, and data
sharing between browsers (peers).
• MediaStream: acquisition of audio and video streams
• RTCPeerConnection: communication of audio and video data
• RTCDataChannel: communication of arbitrary application data

MEDIASTREAM:
************
A MediaStream object represents a real-time media stream and allows the application code to acquire data, manipulate individual tracks, and specify outputs. All the audio
and video processing, such as noise cancellation, equalization, image enhancement, and more are automatically handled by the audio and video engines.

getUserMedia() is a simple API to acquire audio and video streams from the underlying platform. The media is automatically optimized, encoded,
and decoded by the WebRTC audio and video engines and is then routed to one or more outputs.

<video autoplay></video>
<script>
	var constraints = {
			audio: true,
			video: {mandatory: {width: { min: 320 },height: { min: 180 }},
			optional: [{ width: { max: 1280 }},	{ frameRate: 30 },	{ facingMode: "user" }]
		}
	}
	function gotStream(stream) {
		var video = document.querySelector('video');
		video.src = window.URL.createObjectURL(stream);
	}
	function logError(error) { ... }
	navigator.getUserMedia(constraints, gotStream, logError);
</script>


UDP is the protocol as timeliness is preferred over reliability
	No guarantee of message delivery
	No acknowledgments, retransmissions, or timeouts.
	No guarantee of order of delivery
	No packet sequence numbers, no reordering, no head-of-line blocking.
	No connection state tracking
	No connection establishment or teardown state machines.
	No congestion control
	No built-in client or network feedback mechanisms.


RTCPeerConnection API
**********************
Requires
• ICE: Interactive Connectivity Establishment (RFC 5245)
	—STUN: Session Traversal Utilities for NAT (RFC 5389)
	—TURN: Traversal Using Relays around NAT (RFC 5766)
• SDP: Session Description Protocol (RFC 4566)
• DTLS: Datagram Transport Layer Security (RFC 6347)
• SCTP: Stream Control Transport Protocol (RFC 4960)
• SRTP: Secure Real-Time Transport Protocol (RFC 3711)


• RTCPeerConnection manages the full ICE workflow for NAT traversal.
• RTCPeerConnection sends automatic (STUN) keepalives between peers.
• RTCPeerConnection keeps track of local streams and remote streams.
• RTCPeerConnection triggers automatic stream renegotiation as required.
• RTCPeerConnection provides necessary APIs to generate the connection offer, accept the answer, allows us to query the connection for its current state, and more.

DataChannel API enables exchange of arbitrary application data between peers—think WebSocket, but peer-to-peer

Signaling and Session Negotiation
*********************************
Before any connectivity checks of session negotiation can occur, we must find out if the other peer is reachable and if it is willing to establish the connection. 
We must extend an offer, and the peer must return an answer
However, now we have a dilemma: if the other peer is not listening for incoming packets, how do we notify it of our intent? At a minimum, we need a shared signaling channel.
For this, WebRTC defers to existing standards
	Session Initiation Protocol (SIP) => Application-level signaling protocol, widely used for voice over IP (VoIP) and videoconferencing over IP networks.
	Jingle => Signaling extension for the XMPP protocol, used for session control of voice over IP and videoconferencing over IP networks.
	ISDN User Part (ISUP) => Signaling protocol used for setup of telephone calls in many public switched telephone networks around the globe.

Skype is a great example of a peer-to-peer system with custom signaling: the audio and video communication are peer-to-peer, but Skype users have to connect to Skype’s signaling servers, which use their own proprietary protocol, to help initiate the peer-to-peer connection.
Asterisk can act as a signaling server with SIP

	Example: SDP Session Desc Protocol
	************************************
	var signalingChannel = new SignalingChannel();
	var pc = new RTCPeerConnection({});
	
	navigator.getUserMedia({ "audio": true }, gotStream, logError);
	
	function gotStream(stream) {
		pc.addstream(stream);
		pc.createOffer(function(offer) {pc.setLocalDescription(offer);signalingChannel.send(offer.sdp);});
	}
	function logError() { ... }

SDP:
****
WebRTC uses Session Description Protocol (SDP) to describe the parameters of the peerto- peer connection. SDP does not deliver any media itself; instead it is used to describe
the “session profile,” which represents a list of properties of the connection: types of media to be exchanged (audio, video, and application data), network transports, used codecs and their settings, bandwidth information, and other metadata.

ICE:
*****
Each RTCPeerConnection connection object contains an “ICE agent.”
ICE agent is responsible for 
• gathering local IP, port tuples (candidates).
• performing connectivity checks between peers.
• sending connection keepalives.

First hits OS for local IP, then STUN for public IP, if not, go to TURN server as a last resort.

________________________________________________________________________________________________________________________________________________
CHAPTER VRC0: Casting networks
************************************
Router:
********
A router is a device that forwards data packets between computer networks, creating an overlay internetwork. A router is connected to two or more data lines from different networks. When a data packet comes in one of the lines, the router reads the address information in the packet to determine its ultimate destination. Then, using information in its routing table or routing policy, it directs the packet to the next network on its journey.
Usually exchange routing information using the Border Gateway Protocol (BGP). 
RFC 4098[9] standard defines the types of BGP-protocol routers according to the routers' functions:

A router has two stages of operation called planes
	Control plane: 
	****************
	A router records a routing table listing what route should be used to forward a data packet, and through which physical interface connection. 
	It does this using internal preconfigured directives, called static routes, or by learning routes using a dynamic routing protocol. 
	Static and dynamic routes are stored in the Routing Information Base (RIB). 
	The control-plane logic then strips the RIB from non essential directives and builds a Forwarding Information Base (FIB) to be used by the fowarding-plane.
	Forwarding plane: 
	*******************
	The router forwards data packets between incoming and outgoing interface connections. 
	It routes it to the correct network type using information that the packet header contains. 		
	It uses data recorded in the routing table control plane.

Routers are of various types namely
	Access routers=> home, small business ones
	Distribution routers=> aggregate routers from multiple access routers, big enterprises
	Security router=> big enterprises
	Core router=> backbones of internet, very powerful but less features.
	Edge router=>that which connect to core router,is placed at the edge of an ISP network. The router uses External BGP to EBGP protocol routers in other ISPs, or a large enterprise 
	
	Subscriber edge router=> Also called a Customer Edge router, is located at the edge of the subscriber's network. 
	It also uses EBGP protocol to its provider's Autonomous System. It is 	typically used in an (enterprise) organization.
	
	Inter-provider border router=> Interconnecting ISPs, is a BGP-protocol router that maintains BGP sessions with other BGP protocol routers in ISP Autonomous Systems.
	Port forwarding: Routers are also used for port forwarding between private internet connected servers.[5]

	Voice/Data/Fax/Video Processing Routers=> Commonly referred to as access servers or gateways.
	These devices are used to route and process voice, data, video, and fax traffic on the internet. 

A Network Access Point (NAP) was a public network exchange facility where Internet service providers (ISPs) connected with one another in peering arrangements	
__________________________________________________________________________________________________________________________________________
CHAPTER VRC1: Real Time Streaming:
*****************************************
• The solution for multimedia over IP is to classify all traffic, allocate priority for different applications and make reservations. 
• The Integrated Services working group in the IETF (Internet Engineering Task Force) developed an enhanced Internet service model called Integrated Services that includes best-effort
service and real-time service, see RFC 1633. The real-time service will enable IP networks to provide quality of service to multimedia applications. 
• Resource ReServation Protocol (RSVP), together with Real-time Transport Protocol (RTP), Real-Time Control Protocol (RTCP), Real-Time Streaming Protocol (RTSP), provides a working foundation for real-time services. 
• Integrated Services allows applications to configure and manage a single infrastructure for multimedia applications and traditional applications. It is a comprehensive approach to provide applications with the type of service they need and in the quality they choose.

RSVP:Resource ReSerVation Protocol
****************************************
RSVP is used to set up reservations for network resources. 
When an application in a host (the data stream receiver) requests a specific quality of service (QoS) for its data stream, it uses RSVP to deliver its request to routers along the data stream paths.  RSVP is responsible for the negotiation of connection parameters with these routers. 
If the reservation is setup, RSVP is also responsible for maintaining router and host states to provide the requested service.

Policy control =>determines whether the user has administrative permission to make the reservation
Admission control=> keeps track of the system resources and determines whether the node has sufficient resources to supply the requested QoS.
PATH=>Sender sends PATH message to all receivers part of multicast.
RESV=>Receivers send back RESV to determine reverse send path, the RESV msg also contains flow and filter spec.
SOFT=>Reservations are SOFT at intermediate nodes, must be upheld by repeated pings
QoS=> (Quality of Service) is separated from RSVP, as it is a control protocol leaving implementation to others to maintain the requested quality. This leaves for future better implementatuions
Identifies receiver and sender, 2 RSVPs incase of videochat  mode.

RTP: Real-time Transport Protocol
************************************
Realtime transport protocol (RTP) is an IP-based protocol providing support for the transport of real-time data such as video and audio streams. 
The services provided by RTP include time reconstruction, loss detection, security and content identification
RTP provides timestamping, sequence numbering, and other mechanisms to take care of the timing issues.
RTP is no responsible for sequencing timestamped packets, it is done by the application layer.
Timestamping alone cannot order packets, as they can be shared over  some packets, the sequence number is critical here.
Payload type identifier=> specifies the payload format as well as the encoding/compression schemes.
Source identification=> It allows the receiving application to know where the data is coming from
Why UDP over TCP?
	RTP is primarily designed for multicast, the connection-oriented TCP does not scale well and therefore is not suitable/
	For real-time data, reliability is not as important as timely delivery. 
Each media (audio/video) has a dedicated RTP Session running RTP and RTCP packets .

RTCP: Real Time Control Protocol
***********************************
RTCP is the control protocol designed to work in conjunction with RTP.
In an RTP session, participants periodically send RTCP packets to convey feedback on quality of data delivery and information of membership

#TYPES
	#1 RR
	Receiver reports are generated by participants that are not active senders.
	They contain reception quality feedback about data delivery, including the highest packets number received, 
	the number of packets lost, inter-arrival jitter, and timestamps to calculate the round-trip delay between the sender and the receiver.
	#2 SR
	Sender reports are generated by active senders. 
	In addition to the reception quality feedback as in RR, they contain a sender information section, providing information on inter-media synchronization, cumulative packet counters, 	and number of bytes sent.
	#3 SDES
	Source description items. They contains information to describe the sources.
	#4 BYE
	Indicates end of participation.
	#5 APP
	Application specific functions. It is now intended for experimental use as new applications and new features are developed.

FUNCTIONS
	#1 QoS monitoring and congestion control
		This is the primary function of RTCP. RTCP provides feedback to an application about the quality of data distribution to the senders, the receivers and third-party monitors. 
		The sender can adjust its transmission based on the receiver report feedback. 
		The receivers can determine whether a congestion is local, regional or global. 
		Network managers can evaluate the network performance for multicast distribution.
	#2 source identification
		In RTP data packets, sources are identified by randomly generated 32-bit identifiers.
	 	RTCP SDES (source description) packets contain textual information called canonical names as globally unique identifiers of the session participants. 
		It may include user's name, telephone number, email address and other information.
	#3 Inter-media synchronization
 		RTCP sender reports contain an indication of real time and the corresponding RTP timestamp. 
		This can be used in inter-media synchronization like lip synchronization in video.
	#4 Control information scaling
		RTCP packets are sent periodically among participants. When the number of  participants increases, it is necessary to balance between getting up-to-date control
		information and limiting the control traffic. In order to scale up to large multicast  groups, RTCP has to prevent the control traffic from overwhelming network
		resources. RTP limits the control traffic to at most 5% of the overall session traffic. This is enforced by adjusting the RTCP generating rate according to the number of
		participants.

RTSP---Real-Time Streaming Protocol
****************************************
Jointly developed by RealNetworks, Netscape Communications and Columbia University
RTSP is an application-level protocol designed to work with lower-level protocols like RTP, RSVP to provide a complete streaming service over internet. 
RTSP, is a client-server multimedia presentation protocol to enable controlled delivery of streamed multimedia data over IP network. 
It provides "VCR-style" remote control functionality for audio and video streams, like pause, fast forward, reverse, and absolute positioning.
Sources of data include both live data feeds and stored clips.
It provides means for choosing delivery channels (such as UDP, multicast UDP and TCP), and delivery mechanisms based upon RTP. 
It works for large audience multicast as well as single-viewer unicast.

Presentation File => The overall presentation and the properties of the media are defined in a presentation description file, which may include the
encoding, language, RTSP URLs, destination address, port, and other parameters. The presentation description file can be obtained by the client using HTTP, email or other means.
In RTSP, each presentation and media stream is identified by an RTSP URL. 

RSTP differs from HTTP in
	HTTP is stateless but RSTP must maintain session states
	HTTP is request from client/response server but RSTP anyone can request in any direction
	
package com;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author cse.dep@equifax.com
 * @version %I% %G%
 * @since 1.0
 */
public class TestHttpClient {

    public void call() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(builder.build());
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(factory).build();

        HttpGet httpGet = new HttpGet("https://some-server");
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        }
    }
}













































		
			








			


 































				 

























