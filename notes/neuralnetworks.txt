NEURAL NETWORKS 
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Chap #1:
Biological neurons receive input signals from other neurons via its dendrites, sends a output signal based on certain conditions
Bio Neuron is analog
NN research had a big pause in development since 1950, now picked up momentum.
NN is good for pattern recognition and learning problems.
One cannot lay down the path of execution in a NN solution
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Chap #2:
There is a input layer, 0 or more hidden layers and an output layer.
Weight Matrix function			| u = Sum(x[i]*w[i]) where i -> 1-4 (or number of edges)
Threshold/Activation function		| t=0 if u<0 
 				    		  		  t=1 if u>0	(sample)
TANH Activation function			| (e^u-e^-u)/(e^u+e^-u)
								public double tanh (double u){   double a = Math.exp( u );   double b = Math.exp( -u );  return ((a-b)/(a+b));  }

Hopfield Network:
*******************
A Hopfield neural network has every Neuron connected to every other neuron. A 4 neuron network will have 4*3 = 12 connections
Below is the weight matrix of a Hopfield network(the weightage w of the link between 2 nodes)
Step#1
 				N1	N2	N3	N4
			N1	0	-1	1	-1
			N2	-1	0	-1	1
			N3	1	-1	0	-1
			N4	-1	1	-1	0
Given an input x(i)=0101, it should return the same 0101
Step#2:Weight function: 
			N1= (0*0)+(-1*1)+(1*0)+(-1*1) = -2
			N1= (-1*0)+(0*-1)+(-1*0)+(1*1) = 1
			N1= (1*0)+(-1*1)+(0*0)+(-1*1) = -2
			N1= (-1*0)+(1*1)+(-1*0)+(0*1) = 1

Step#3:Threshold/Activation function		
			 t=0 if u<0 
 			 t=1 if u>0	
After applying activation function, [-2,1-,2,1] becomes [0,1,0,1]
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Deriving the Weight Matrix for 0101
Step#1: Start with blank matrix 
			0 0 0 0
			0 0 0 0	
			0 0 0 0
			0 0 0 0
Step#2:To convert 0101 to bipolar we convert all of the zeros to –1’s. This results in: 0 = -1,1 = 1,0 = -1,1 = 1
Step#3: Multiply –1, 1, -1, 1 by its Inverse.
[–1, 1, -1, 1] *[ -1           1  -1   1 - 1
			   1    =    -1   1  -1   1
  			  -1		1  -1   1 - 1
			   1]          -1   1  -1   1     
Step#4: make the diagonal elements 0 (as neurons are not self-connected)			
			N1	N2	N3	N4
			N1	0  -1   1	-1
			N2	-1  0  -1	1
			N3	1  -1   0	-1
			N4	-1  1  -1	 0
This contribution matrix can now be added to whatever connection weight matrix you already had. If you only want this network to recognize 0101, then this contribution matrix becomes your connection weight matrix. If you also wanted to recognize 1001, then you would calculate both contribution matrixes and add each value in their contribution matrixes to result in a combined matrix, which would be the connection weight matrix.
You can get the inverse of a bit pattern by flipping all 0’s to 1’s and 1’s to zeros. The inverse of 0101 is 1010. As a result, the connection weight matrix we just calculated would also recognize 1010.
















