Jim Webber - Guerilla SOA
------------------------------------
Webservices are  good but centralised ESB is super bad.
bad: ESB takes the spagetti lines of integration and just hides it inside a box
bad: security, scalability (doubtful), all locked inside box
good: soap is good, services talk with business messages, completely decoupled from each other
good: push the nfrs like security/scalability to edges, let them be done by webservice on demand, incrementally
eai (enterprise application tools) provide adaptors to multiple technologies, but it can be done by yourself where SOAP contains business data + metadata for security/reliability
biztalk should be useful for wiring at low level, but dont expose business to it, and make it GOD. It makes testing tough

transactions across webservices:
----------------------------------------------





