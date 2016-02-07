A) 1-Way SSL -> Browser access a public https website
	1) Browser requests https://google.com
	2) Server sends PUBLIC key to browser
	3) Client checks PUBLIC key for expiry, signed by CAs(VeriSign), name matches domain..etc. Clients come with prestored CA root certificates.
	4) Client sends SYMMETRIC key to Server using PUBLIC key
	5) Server decrypts SYMMETRIC key using PRIVATE key
	6) Server sends encrypted pages using SYMMETRIC key	

B) 2-Way SSL


------------------------------------------------------------------------------------------------------------------------------------------------------
There are two types of SSL certificates: 
A) full-authentication - will contain CN(common name=website domain)|OU(organizational unit=Software)|O(Organization=IBM)|L-location,S-state,C-country
B) domain-authentication - will contain only CN domain

------------------------------------------------------------------------------------------------------------------------------------------------------
A) Keystore contains private keys, and the certificates with their corresponding public keys. 
   You only need this if you are a server, or if the server requires client authentication.
B) Truststore contains certificates from other parties that you expect to communicate with, or from Certificate Authorities that you trust to identify other parties.
