from twonumbers import twonumbers
	
try:
	tnos = twonumbers(3,2)
	print "add=%d |multiply=%d" % (tnos.add(),tnos.multiply())
	print "loop=",tnos.loop()
	tnos.ask()
except:
	print "error"
finally:
	print "completed run"
