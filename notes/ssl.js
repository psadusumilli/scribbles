http://www.ossmentor.com/2015/03/one-way-and-two-way-ssl-and-tls.html

'Encryption' uses a private key/public key pair which ensures that the data can be encrypted by one key but can only be decrypted by the other key pair.   
This is referred to as the Public-Key Infrastructure (PKI) Scheme.  
The public key is shared while the private key is kept locally.

'Trust' is achieved through the use of certificate trust.   
Certificate trust can be thought of as a chain that starts with the Certificate Authority (or CA).  
A CA is a company or entity that issues SSL Certificates

'SSL vs TSL'
SSL is the predecessor for TLS. SSL 3.0 -> TLS 1.0

------------------------------------------------------------------------------------------------------------------------------------------------------
1-Way SSL 
	Client verifies Server 
	1.1 Client requests data from Server 
	1.2 Server sends SERVER-PUBLIC key to Client
	1.3 Client checks SERVER-PUBLIC key for expiry, signed by CAs(VeriSign), name matches domain..etc. Clients come with prestored CA root certificates.
	1.4 Client sends SYMMETRIC key to Server using SERVER-PUBLIC key
	1.5 Server decrypts SYMMETRIC key using SERVER-PRIVATE key
	1.6 Server sends encrypted pages using SYMMETRIC key	

2-Way SSL 
	Client verifies Server, then Server verifies Client 
	2.1 Client requests data from Server  
	2.2 Server sends SERVER-PUBLIC key to browser
	2.3 Client checks SERVER-PUBLIC key for expiry, signed by CAs(VeriSign), name matches domain..etc. Clients come with prestored CA root certificates.
	2.4 Client sends SYMMETRIC key to Server using SERVER-PUBLIC key
	2.5 Client sends its CLIENT-PUBLIC to Server
	2.6 Server verifies CLIENT-PUBLIC with its own trust store 
	2.7 Server decrypts SYMMETRIC key using SERVER-PRIVATE key
	2.8 Server sends encrypted pages using SYMMETRIC key	

-----------------------------------------------------------------------------------------------------------------------------------------------------
Two types of SSL certificates: 
1 full-authentication 
	will contain CN(common name=website domain)|OU(organizational unit=Software)|O(Organization=IBM)|L-location,S-state,C-country
2 domain-authentication
	will contain only CN domain

------------------------------------------------------------------------------------------------------------------------------------------------------
1 Keystore contains private keys, and the certificates with their corresponding public keys. 
   You only need this if you are a server, or if the server requires client authentication.
2 Truststore contains certificates from other parties that you expect to communicate with, or from Certificate Authorities that you trust to identify other parties.
