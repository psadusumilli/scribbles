loop gives me a thread/processor for both receive/react

one liner:  
	RECEIVE ACTOR: LOCKED THREADS
			I have a thread already, 
			I receive a message, work on it and can return a reply.
			I get blocked and lock my underlying thread (obj.wait()/notify) until the next msg arrives. 
			I dont have a delay of creating a thread dynamically on receipt of message, so can be useful when message processing time is low.
			
	REACT ACTOR: FREE THREADS
			I make a thread only on receipt of a message.
			The whole method body of the react method is captured as a closure and is executed by that arbitrary thread
			After processing message, I throw an exception to discard my thread stack frames
			This makes that freed thread to help process other messages.
 	LOOP: 
			just gives parallelism over cores to both actors


