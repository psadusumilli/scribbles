#Ex1: function passed as parameter
---------------------------------------------------------------
    def f1(x,y):
        print "f1 returns"
        return x*y
    
    def f2(func, x1, y1):
        return func(x1*y1)

    f2(f1, 3, 4)
    => f1 returns 12


M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
M
#Ex2: function passed as parameter
---------------------------------------------------------------
