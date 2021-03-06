JVM Notes

Structure
	Heap
		Objects data stored, GC done here.
	Method Area
		Class Grammar -methods/fields
		type constant pool
		Method Table
		Big pool of all classes 
		Small pool per class
	Stack
		Stack frames
			Local Variable array
			operand stack
			Frame Data
		Native frames


#---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CHAPTER1: INTRODUCTION
**********************
1.1| 	Java architecture arises out of four distinct but interrelated technologies:
			the Java programming language
			the Java class file format
			the Java Application Programming Interface
			the Java virtual machine
1.2| 	JVM supports all three prongs of Java network-oriented architecture: 'platform independence, security, and network-mobility'.
1.3| 	JVM has a 'Classloader' (to load the byte from classes) and an 'Execution Engine'(to execute the bytecode)
1.4| 	Execution Engine offers many implementations
		Simple Interpretation -> one at a time
		Just In Time Compilation -> bytecode to native machine code just before execution of a method, then saved to future use.
		Adaptive Optimiser -> compile to native code only the heavily used portion of the code.
		Direct Native -> JVM built on top of a chip that executes Java bytecodes natively, the execution engine is actually embedded in the chip.
1.5| 	In Java, there are two kinds of methods: Java and native. 
	A 'Java method' is written in the Java language, compiled to bytecodes, and stored in class files.
	A 'native method' is written in some other language, such as C, C++, or assembly, and compiled to the native machine code of a particular processor. 
	'Native methods' are stored in a dynamically linked library whose exact form is platform specific. While 'Java methods' are platform independent, native methods are not. 
	When a running Java program calls a native method, the virtual machine loads the dynamic library that contains the native method and invokes it.
	Use of 'Native methods' can make your program platform-specific, can be alleviated using 'JNI', but vendors are mandated to implement it.

1.6|
Many JVM implementations exists
'HotSpot'=>Oracle implementation with JIT and adaptive optimisation.
	'Client' version is tuned for quick loading. It makes use of interpretation. 
	'Server' version loads more slowly, putting more effort into producing highly optimized JIT compilations, that yield higher performance
'JRockit'=> Weblogic
'Zing'=> Azul
'J9'=> IBM
#---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CLASS LOADER
*************
2 types are 
	'Bootstrap' -> implemented inside JVM in native code
	'user-defined' -> written by coders in Java
Boundaries exist between classes loaded by different class loaders, only the JVM can co-relate.
CLASS
******
Provides platform-independence and network-mobility, by enabling a common form to be ported to JVMs hosted on different platform

JAVA API
********
API is implemented for different platforms
Consistent primitives across platforms -> Int is always 4byte 
Provides security by using 'Security manager' (1.2) or 'Access Controllers' (1.3+). Any malicious calls are intersected at stack levels by security.
Your program -> Java API -> Native library -> Host OS 

JAVA LANGUAGE
*************
OOP, Garbage Collection, no direct memory handling, only references

ARCHITECTURAL TRADEOFFS
************************
Performance still being worked to be on par with C/C++
Differs for every JVM implementation
Adaptive Optimiser can do only static linking 
Unpredictable Garbage collection and Threads
Dynamic linking of classes is all symbolic links, thereby making it easier to decompile back to source. This makes your code open to competitors
	Can be alleviated by using obfuscation.
	
	'Static linking' is the result of the linker copying all library routines used in the program into the executable image. 
	This may require more disk space and memory than dynamic linking, but is both faster and more portable, 
	since it does not require the presence of the library on the system where it is run.
	
	'Dynamic linking' is accomplished by placing the name of a sharable library in the executable image. 
	Actual linking with the library routines does not occur until the image is run, when both the executable and the library are placed in memory. 
	An advantage of dynamic linking is that multiple programs can share a single copy of the library.	
#-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER2: PLATFORM INDEPEDENCE:
****************************************
Consistent primitives across platforms -> Int is always 4byte 
Sun initially started Java for embedded systems only has desktops were dominated by MS and Apple
J2ME, J2SE, J2EE
#-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER3: SECURITY
***************************
Javas security model is focused on protecting end-users from hostile programs downloaded across a network from untrusted sources. 
To accomplish this goal, Java provides a customizable "sandbox" in which Java programs run. A Java program must play only inside its sandbox. 
It can do anything within the boundaries of its sandbox, but can't take any action outside those boundaries. 
The sandbox for untrusted Java applets, for example, prohibits many activities, including:

    reading or writing to the local disk,
    making a network connection to any but the host from which the applet came,
    creating a new process, and
    loading a new dynamic library and directly calling a native method.
    
The fundamental components responsible for Java's sandbox are:
	the class loader architecture
	the class file verifier
	safety features built into the Java virtual machine (and the language)
	the security manager and the Java API

3.1| 'CLASS LOADER'
--------------------------------------------
Each call to load a class, is made first to the primordial class loader(Boot), if it does not find in its trusted store, then trickle down to custom loaders.

Rules to write a custom class loader
	0	Make sure your class loader prevents loading any class that impersonates like 'java.lang' apis
	1	If packages exist that this class loader is not allowed to load from, the class loader checks whether the requested class is in one of those forbidden packages. If so, it throws a security exception. Else, it continues on to step two. 
	2	The class loader passes the request to the primordial class loader. If the primordial class loader successfully returns the class, 
		the class loader returns that same class. Else, it continues on to step three. 
	3	If trusted packages exist that this class loader is not allowed to add classes to, 
		the class loader checks whether the requested class is in one of those restricted packages. 
		If so, it throws a security exception. Else, it continues on to step four. 
	4	Finally, the class loader attempts to load the class in the custom way, such as by downloading it across a network. 
		If successful, it returns the class. Else, it throws a "no class definition found" error.
		
3.2| 'CLASS FILE VERIFIER'
--------------------------------------------------
Working in conjunction with the class loader, the class file verifier ensures that loaded class files have a proper internal structure. 
If the class file verifier discovers a problem with a class file, it throws an exception
Two steps:
	After classloading, verifies the integrity of the bytecodes it import
	During execution, confirms the existence of symbolically referenced classes, fields, and methods. 
	
3.2.1| Step1: Internal Checks:
--------------------------------------------------------
	Phase one of the class file verifier makes sure the imported class file is properly formed, internally consistent, adheres to the constraints of the Java programming language, and contains bytecodes that will be safe for the Java Virtual Machine to execute. 
	If the class file verifier finds that any of these are not true, it throws an error, and the class file is never used by the program.
	Checks if bytecodes neither truncated nor enhanced with extra trailing bytes. 
	Checks if method descriptor have a context free grammar
	
	The bytecode streams that represent Java methods are a series of one-byte instructions, called opcodes, each of which may be followed by one or more operands. 
	The operands supply extra data needed by the Java Virtual Machine to execute the opcode instruction. 
	The activity of executing bytecodes, one opcode after another, constitutes a thread of execution inside the Java Virtual Machine. 
	Each thread is awarded its own Java Stack, which is made up of discrete frames. 
	Each method invocation gets its own frame, a section of memory where it stores, among other things, local variables and intermediate results of computation. 
	The part of the frame in which a method stores intermediate results is called the method's  operand stack. 
	An opcode and its (optional) operands may refer to the data stored on the operand stack or in the local variables of the method's frame. 
	Thus, the virtual machine may use data on the operand stack, in the local variables, or both, in addition to any data stored as operands following an opcode when it executes the opcode.
	
3.2.2| STEP2: Verfication of symbolic references	
------------------------------------------------------------------------------------------------
Dynamic linking is the process of resolving symbolic references into direct references.
As the JVM executes bytecodes and encounters an opcode that, for the first time, uses a symbolic reference to another class, the virtual machine must resolve the symbolic reference. 
The virtual machine performs two basic tasks during resolution:
    a) find the class being referenced (loading it if necessary)
    b) replace the symbolic reference with a direct reference, such as a pointer or offset, to the class, field, or method
Throws ClassNotFoundException, MethodNotFoundException for cases when they could not have been detected during compile time.
Also does the following, though the language compiler has already done it
    a) type-safe reference casting
    b) structured memory access (no pointer arithmetic)
    c) automatic garbage collection (cant explicitly free allocated memory)
    d) array bounds checking
    e) checking references for null

3.3| Memory access
--------------------------------
The runtime data areas are the memory areas in which the Java Virtual Machine stores the data it needs to execute a Java application: 
	Java stacks (one for each thread), 
	a method area, where bytecodes are stored, and 
	a garbage-collected heap, where the objects created by the running program are stored. 
If you peer into a class file, you wonít find any memory addresses, every address is decided at runtime by JVM.
The prohibition on unstructured memory access is a solid barrier against the malicious manipulation of memory. 
Native methods are still points of weakness as they dont fall under Java Security Sandbox

3.4| Exceptions 
--------------------------------
One final mechanism that is built into the Java Virtual Machine that contributes to security is structured error handling with exceptions. 
Because of its support for exceptions,  JVM has something structured to do when a security violation occurs.
Instead of crashing, the Java Virtual Machine can throw an exception or an error, which may result in the death of the offending thread, but shouldnt crash the system. 
Throwing an error (as opposed to throwing an exception) almost always results in the death of the thread in which the error was thrown.

3.5|
Security Manager
----------------------
By using class loaders, you can prevent code loaded by different class loaders from interfering with one another inside the JVM, but to protect assets external to JVM, you must use a security manager customisable via security.policy.

The classes of the Java API check with the security manager before they:

    accept a socket connection from a specified host and port number
    modify a thread (change its priority, stop it, etcÖ)
    open a socket connection to a specified host and port number
    create a new class loader
    delete a specified file
    create a new process
    cause the application to exit
    load a dynamic library that contains native methods
    wait for a connection on a specified local port number
    load a class from a specified package (used by class loaders)
    add a new class to a specified package (used by class loaders)
    access or modify system properties
    access a specified system property
    read from a specified file
    write to a specified file

#----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER4: NETWORK MOBILITY:
**********************************
Dynamic linking - all symbolic references only, no inlining of called code.
Dynamic extensions - URLCLassloaders 
Compaction - jar files.

The main way Javas architecture facilitates network-mobility directly is by breaking up the monolithic binary executable into compact class files, which can be loaded as needed.
Bytecode streams they contain are designed to be compact. They are called "bytecodes" because each instruction occupies only one byte
The JAR file is to group classes to consolidate TCP handshake times of mutiple classes.

Applets made JAVA future, as Sun released it along with Netscape.
Java applets can run on any platform so long as there is a Java-capable browser for that platform. Java applets also demonstrated Javaís security capabilities, because they run inside a strict sandbox. But most significantly, Java applets demonstrated the promise of network-mobility.
#----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER 5: JVM:
*******************
when you say "Java Virtual Machine." You may be speaking of:
    the abstract specification,
    a concrete implementation, or
    a runtime instance.
The base architecture of JVM comprises of 
	Class loader system
	Execution Engine
	Runtime data areas
		Stack - local variables,method args, method returns of thread, made up of Frames (1 frame/1 method call)
		Heap - objects reside here
		Method Area - class structure data
		PC registers - Program Counter Registers 1/thread, hold the next instruction to execute
		Native method stacks
Data Types		
	Value Types  -  primitives (byte, char, short, int, long, float, double). boolean is implemented by a int (0,1).
	Reference types - objects (class, interface, collections)

JVM specifies only primitive ranges (for efg, int(8bits) -2^7 to +2^7-1) and not their actual sizes, its upto the implementers.

5.1| Class Loader: 
	Loading: finding and importing the binary data for a type
    Linking: performing verification, preparation, and (optionally) resolution
        a. Verification: ensuring the correctness of the imported type
        b. Preparation: allocating memory for class variables and initializing the memory to default values
        c. Resolution: transforming symbolic references from the type into direct references.
	Initialization: invoking Java code that initializes class variables to their proper starting values.
-------------------------------------------------------------------------------------------------------------------
5.2| Method Area
When JVM loads a type, it uses a class loader to locate the appropriate class file. The class loader reads in the class file--a linear stream of binary data--and passes it to the virtual machine. The virtual machine extracts information about the type from the binary data and stores the information in the method area. Memory for class (static) variables declared in the class is also taken from the method area.
Designers can choose whatever data structures and organization they feel optimize their implementations performance, in the context of its requirements.
All threads share the same method area, so access to the method areaís data structures must be designed to be thread-safe. If two threads are attempting to find a class named Lava, for example, and Lava has not yet been loaded, only one thread should be allowed to load it while the other one waits.
Expands and contracts, can also be garbage collected.
In addition to the basic type information listed above, the virtual machine must also store for each loaded type:

    a. The constant pool for the type
    b. Field information  (name, type, order inside type)
    c. Method information (name, params,return, order inside type)
    d. All class (static) variables declared in the type, except constants(final) which go to pool
    e. A reference to class ClassLoader
    f. A reference to class Class
    g. A method table is an array of direct references to all the instance methods that may be invoked on a class instance, including instance methods inherited from superclasses. 
-------------------------------------------------------------------------------------------------------------------
5.3| Heap
Objects and collections go here
Collected by Garbage Collector, design left to implementers
Need not be contiguous, expands and contracts as per need
Object representation--an integral aspect of the overall design of the heap and garbage collector--is a decision of implementation designers

#design possiblility #1
Divides the heap into two parts: A handle pool and an object pool. An object reference is a native pointer to a handle pool entry. 
A handle pool entry has two components: a pointer to instance data in the object pool and a pointer to class data in the method area
Disadvantage of using 2 pointers for an obj reference.

#design possiblility #2
Object reference points to a data aggregate in heap, which is a collection of pointers to instance data and one pointer to class data in method area.

#design possiblility #3
one pointer to instance data in heap and another to a method table in methods area. Methods table is not mandated by JVM Spec.

#design possiblility #4
each instance in heap can hold an copy of class image to boost performance, but it drains heaps space.

#lock
Each object has a lock for threads and a wait-set for storing threads waiting on it
Additional data can also be stored for garbage collection mechanisms. A mark and sweep algorithm, for instance, could potentially use a separate bitmap for marking referenced and unreferenced objects

#finaliser
Garbage collectors must run the finalizer of any object whose class declares one before it reclaims the memory occupied by that object. 
The specification states that a garbage collector will only execute an objects finalizer once, but allows that finalizer to "resurrect" the object: to make the object referenced again. 
When the object becomes unreferenced for a second time, the garbage collector must not finalize it again

Arrays, like classes, are stored in the heap.
 For example, 
 the class name for an array of ints is "[I". 
 The class name for a three-dimensional array of bytes is "[[[B",
 the class name for a two-dimensional array of Objects is "[[Ljava.lang.Object". 

-------------------------------------------------------------------------------------------------------------------
5.4|Program Counter
Each thread of a running program has its own pc register, or program counter, which is created when the thread is started. 
The pc register is one word in size, so it can hold both a native pointer and a returnValue. 
As a thread executes a Java method, the pc register contains the address of the current instruction being executed by the thread. 
An "address" can be a native pointer or an offset from the beginning of a methodís bytecodes. If a thread is executing a native method, the value of the pc register is undefined.
-------------------------------------------------------------------------------------------------------------------
5.5|Java Stack
One/thread, made of frames
all local variables are exclusive for each thread.
The method that is currently being executed by a thread is the threadís current method. The stack frame for the current method is the current frame. 
The class in which the current method is defined is called the current class, and the current classís constant pool is the current constant pool.
Like the method area and heap, the Java stack and stack frames need not be contiguous in memory.
Frames could be allocated on a contiguous stack, or they could be allocated on a heap, or some combination of both. 
The actual data structures used to represent the Java stack and stack frames is a decision of implementation designers
-------------------------------------------------------------------------------------------------------------------
5.6|Stack Frame
The stack frame has three parts: local variables, operand stack, and frame data. 
The sizes of the local variables and operand stack, which are measured in words, depend upon the needs of each individual method. 
These sizes are determined at compile time and included in the class file data for each method. The size of the frame data is implementation dependent.
When the jvm invokes a Java method, it checks the class data to determine the number of words required by the method in the local variables and operand stack. 
It creates a stack frame of the proper size for the method and pushes it onto the Java stack.

5.6.1|Local variable array
The local variables section of the Java stack frame is organized as a zero-based array of words. 
Instructions that use a value from the local variables section provide an index into the zero-based array. 
	Values of type int, float, reference, and returnValue occupy one entry in the local variables array. 
	Values of type byte, short, and char are converted to int before being stored into the local variables. 
	Values of type long and double occupy two consecutive entries in the array.

'this' - object reference is always sent as hidden param into the local variables array, apart from the regular arguments when a instance method is called.
the array positions can be reused for multiple local variables based on their scope.


5.6.2|Operand Stack
Like the local variables, the operand stack is organized as an array of words. But unlike the local variables, which are accessed via array indices, the operand stack is accessed by pushing and popping values. If an instruction pushes a value onto the operand stack, a later instruction can pop and use that value
	iload_0 // push the int in local variable 0 onto the stack
	iload_1 // push the int in local variable 1 onto the stack
	iadd // pop two ints, add them, push resultbcdefghijklmnopqrs
	istore_2 // pop int, store into local variable 2

5.6.3|Frame Data
In addition to the local variables and operand stack, the Java stack frame includes data to support constant pool resolution, normal method return, and exception dispatch. This data is stored in the frame data portion of the Java stack frame.
The frame data must also contain some kind of reference to the method exception table. When a method throws an exception, the Java Virtual Machine uses the exception table referred to by the frame data to determine how to handle the exception. If the virtual machine finds a matching catch clause in the methodís exception table, it transfers control to the beginning of that catch clause. If the virtual machine doesnít find a matching catch clause, the method completes abnormally. The virtual machine uses the information in the frame data to restore the invoking methodís frame. It then rethrows the same exception in the context of the invoking method.
-------------------------------------------------------------------------------------------------------------------
5.7| Native Stack
For native method execution
thread leaves java stack and enters native stack and can return back to java stacks
-------------------------------------------------------------------------------------------------------------------
5.8| Execution Engine
The spec indicates what should be done but not how
The implementors can decide to interpret, just-in-time compile, adaptive optimisation, direct machine code.
Each thread of a running Java application is a distinct instance of the virtual machineís execution engine. 

5.8.1|Instruction Set
A method bytecode stream is a sequence of instructions for the Java Virtual Machine. Each instruction consists of a one-byte opcode followed by zero or more operands. The opcode indicates the operation to be performed. Operands supply extra information needed by the Java Virtual Machine to perform the operation specified by the opcode. The opcode itself indicates whether or not it is followed by operands, and the form the operands (if any) take. Many Java Virtual Machine instructions take no operands, and therefore consist only of an opcode. Depending upon the opcode, the virtual machine may refer to data stored in other areas in addition to (or instead of) operands that trail the opcode. When it executes an instruction, the virtual machine may use entries in the current constant pool, entries in the current frameís local variables, or values sitting on the top of the current frameís operand stack.
#----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER 6: THE CLASS FILE
****************************
1 class file/1 class - no multiple classes/interfaces in a class file.
not tied to java alone, any language can compile to bytecode like clojure.

The Java class file contains everything a Java Virtual Machine needs to know about one Java class or interface. The remainder of this chapter describes the class file format using tables. Each table has a name and shows an ordered list of items that can appear in a class file. Items appear in the table in the order in which they appear in the class file. Each item has a type, a name, and a count. The type is either a table name or one of the "primitive types" shown in Table 6-1. All values stored in items of type u2, u4, and u8 appear in the class file in big-endian order.

Table 6.1|"primitive types"
-------------------------
u1	a single unsigned byte
u2	two unsigned bytes
u4	four unsigned bytes
u8	eight unsigned bytes

The major components of the class file, in their order of appearance in the class file, are shown in Table 6-2. Each of these components is described in more detail below.

Table 6-2|Format of a ClassFile Table
----------------------------------------
Type&			#9;Name					Count
u4				magic					1
u2				minor_version			1
u2				major_version			1
u2				constant_pool_count		1
cp_info			constant_pool			constant_pool_count	- 1	
u2				access_flags			1
u2				this_class				1
u2				super_class				1
u2				interfaces_count		1
u2				interfaces				interfaces_count	
u2				fields_count			1
field_info		fields					fields_count	
u2				methods_count			1
method_info		methods					methods_count	
u2				attributes_count		1
attribute_info	attributes	attributes_count	

#1 magic: - oxCAFEBABE => magic number to indicate that the file is a class file.
#2 minor_version/major_version => class file versions
#3 constant_pool => final variables, string literals, class/method/field names and descriptors. Many entries in the constant pool refer to other entries in the constant pool, and many items that follow the constant pool in the class file refer back to entries in the constant pool. Throughout the class file, constant pool entries are referred to by the integer index that indicates their position in the constant_pool list. Plays a important role in dynamic linking.
Each constant pool entry starts with a one-byte tag that indicates the type of constant making its home at that position in the list
Table 6-3|Constant pool tags
------------------------------
Entry Type	Tag Value	Description
CONSTANT_Utf8	1	A UTF-8 encoded Unicode string
CONSTANT_Integer	3	An int literal value
CONSTANT_Float	4	A float literal value
CONSTANT_Long	5	A long literal value
CONSTANT_Double	6	A double literal value
CONSTANT_Class	7	A symbolic reference to a class or interface
CONSTANT_String	8	A String literal value
CONSTANT_Fieldref	9	A symbolic reference to a field
CONSTANT_Methodref	10	A symbolic reference to a method declared in a class
CONSTANT_InterfaceMethodref	11	A symbolic reference to a method declared in an interface
CONSTANT_NameAndType	12	Part of a symbolic reference to a field or method

#4 Access flags
Flag Name		Value	Meaning if Set						Set By
ACC_PUBLIC		0x0001	Type is public						Classes and interfaces
ACC_FINAL		0x0010	Class is final						Classes only
ACC_SUPER		0x0020	Use new 							invokespecial semanticsClasses and interfaces(obsolete)
ACC_INTERFACE	0x0200	Type is an interface, not a class	All interfaces, no classes
ACC_ABSTRACT	0x0400	Type is abstract					All interfaces, some classes

6.1|Constant Pool
----------------------------
The constant pool is an ordered list of cp_info tables, each of which follows the general form shown in Table 6-8. The tag item of a cp_info table, an unsigned byte, indicates the tableís variety and format. cp_info tables come in eleven varieties, each of which is described in detail in the following sections.

General form of a cp_info table
---------------------
Type	Name 	Count
u1		tag		1
u1		info 	depends on tag value

6.1.1|CONSTANT_Utf8_info Table=>Stores string literals, class|method|fields simple|fully qualified names
6.1.2|CONSTANT_Integer_info Table=>store integers| (tag u1 |bytes u4)
6.1.3|CONSTANT_Float_info Tables=>stores floats| (tag u1 |bytes u4)
6.1.4|CONSTANT_long_info Tables=>stores long | (tag u1 |bytes u8)
6.1.5|CONSTANT_Double_info Tables=>stores double | (tag u1 |bytes u8)

6.1.6|CONSTANT_Class_info Table=>stores class names | (tag u1| name_index u2) 
6.1.7|CONSTANT_String_info Table=>stored string literals | (tag u1|string_index u2)
6.1.8|CONSTANT_Fieldref_info Table=>stored fields| (tag u1|class_index u2|name_and_type_index u2)
6.1.9|CONSTANT_Methodref_info Table=>stores methods | (tag u1|class_index u2|name_and_type_index u2)
6.1.10|CONSTANT_InterfaceMethodref_info=>stores interface methods | (tag u1|class_index u2|name_and_type_index u2)
6.1.11|CONSTANT_NameAndType_info=>part of a symbolic reference to a field or method| (tag u1|name_index u2|desc_index u2)

6.1.12| Attributes
Attributes appear in several places inside a Java class file. They can appear in the ClassFile, field_info, method_info, and Code_attribute tables. The Code_attribute table, an attribute itself, is described later in this section.

Name				Used By			Description
-------------------------------------------------------------------------------------
Code				method_info	The bytecodes and other data for one method
ConstantValue		field_info		The value of a final variable
Exceptions			method_info	The checked exceptions that a method may throw
InnerClasses			ClassFile		A description of the inner classes defined inside a class
LineNumberTable	Code_attribute	A mapping of line numbers to bytecodes for one method
LocalVariableTable	Code_attribute	A description of the local variables for one method
SourceFile			ClassFile		The name of the source file
Synthetic			field_info 		method_info	An indicator that a field or method was generated by the compiler

#----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER 6: CLASS LIFETIME
*******************************
loading -> linking (verification|prepare|resolve) -> initialisation

Active usage of a class:
	The invocation of a constructor on a new instance of the class 
	The creation of an array that has the class as its an element type
	The invocation of a method declared by the class (not inherited from a superclass) 
	The use or assignment of a field declared by the class (not inherited from a superclass or superinterface), except for fields that are both static and final, 
	and are initialized by a compile-time constant expression
Active usage of an interface:
	The use or assignment of a field declared by the interface (not inherited from a superinterface), except for fields that are initialized by a compile-time constant expression

6.1|Loading
-----------------
Load the class file via any means using class loaders
    	produce a stream of binary data that represents the type
    	parse the stream of binary data into internal data structures in the method area
    	create an instance of class java.lang.Class that represents the type
Class loaders can
	need not wait until the first active usage of Class, can load in anticipation of usage
	group and load related classes
	Reports LinkageError only during active usage even during early loading. 
	
6.2|Linking|Verification
--------------------------------------
Some verification happen during loading phase and some also happen during resolving stage.
	a) checking that final classes are not subclassed 
	b) checking that final methods are not overridden
	c) if the type being checked is a non-abstract class, checking that all the methods declared in any interfaces implemented by the class are indeed implemented by the class 	
        making sure no incompatible method declarations (such as two methods that have the same name, the same number, order, and types of parameters, but different return types) 		appear between the type and its supertypes
	d)bytecode verification => ability to verify bytecode streams all at once during linking, rather than on the fly as the program runs, gives a big boost to the potential execution speed 	of Java programs. 
6.3|Linking|Preparing
----------------------------------
During the preparation phase, the Java Virtual Machine allocates memory for the class variables and sets them to default initial values. The class variables are not initialized to their proper initial values until the initialization phase. (No Java code is executed during the preparation step.)
Datastructures like Method tables are built.  A method table enables an inherited method to be invoked on an object without a search of superclasses at the point of invocation. 
6.4|Linking|Resolution
----------------------------------
After a type has been through the first two phases of linking: verification and preparation, it is ready for the third and final phase of linking: resolution. Resolution is the process of locating classes, interfaces, fields, and methods referenced symbolically from a typeís constant pool, and replacing those symbolic references with direct references. As mentioned above, this phase of linking is optional until (and unless) each symbolic reference is first used by the program.

6.5|Initialization 
-------------------------
The final step required to ready a class or interface for its first active use is initialization, the process of setting class variables to their proper initial values.
In Java code, a proper initial value is specified via a class variable initializer or static initializer.
All the class variable initializers and static initializers of a class are collected by the Java compiler and placed into one special method, the class initialization method. In the Java class file, the class initialization method is named "<clinit()"
Goes up to initialising all the superclasses first

6.6|Object Lifetime
--------------------------------
explicit=>Initiated with new, newInstance() or clone()
implicit=>string pool, StringBuffers during concantenation, 
a <init() method for every constructor with diiferent rules on what to run first with superclasses,this() calls
6.6.1|Finalisation and garbage collection
----------------------------------------------------------------
the garbage collector will execute 'finalizer' once on an instance of that class, before it frees the memory space occupied by that instance.
application code can call finalise multiple times.

6.7|Unloading of Class
---------------------------------
JVM will run a class classFinalize() method (if the class declares one) before it unloads the class. 
Types loaded through the primordial class loader will always be reachable and never be unloaded. Only dynamically loaded types--those loaded through class loader objects--can become unreachable and be unloaded by the virtual machine. A dynamically loaded type is unreachable if its Class instance is found to be unreachable through the normal process of garbage collecting the heap. 
#----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER 7: THE LINKING MODEL
************************************
There a 2 pools, a big global pool in Methods Area, a internal pool for every type loaded.
Resolution of the symbolic links from internal pools to global pool is called resolution
Resolution can be early(even before main() is called) or late, but all jvm implementations give the impression of being late, they throw any linking errors only after active usage.
One can write custom ClassLoaders which does loading, linking and resolution of new types.
loadClass() with resolve flag=true will link/resolve immediately.
Each classloader has its own namespace

Cat -> Mouse (Cat has symbolic ref to Mouse)
So jvm will search for Mouse in the same namespace as Cat
if not found, use the same classloader of Cat to load Mouse, this will happen even if Mouse is already loaded by another Classloader

Following sections will describe resolution of each entitity type.

7.1|Resolution of CONSTANT_Class_info Entries
---------------------------------------------------------------------------------
Step 1:Loading the class: the Java Virtual Machine makes sure a type is loaded, and if the type is a class, that all its superclasses are loaded. During this step, these types are not linked and initialized--just loaded.
Step 2. Link and Initialize the Type and any Superclasses

7.2|Resolution of CONSTANT_Fieldref_info Entries
-----------------------------------------------------------------------------------
If resolution of the CONSTANT_Class_info entry succeeds, the virtual machine checks to make sure the field exists and that the current class has permission to access it.
If the virtual machine discovers there is no field with the proper name and type in the referenced class, the virtual machine throws NoSuchFieldError. Otherwise, if the field exists, but the current class doesnít have permission to access the field, the virtual machine throws IllegalAccessError.

7.3|Resolution of CONSTANT_Methodref_info Entries
-----------------------------------------------------------------------------------
If the virtual machine discovers there is no method with the proper name, return type, and number and types of parameters in the referenced class, the virtual machine throws NoSuchMethodError. Otherwise, if the method exists, but the current class doesnít have permission to access the method, the virtual machine throws IllegalAccessError.

7.4|Resolution of CONSTANT_InterfaceMethodref_info Entries
-------------------------------------------------------------------------------------------
 If the resolution of the CONSTANT_Class_info entry succeeds, the virtual machine checks to make sure the method exists. (The virtual machine need not check to make sure the current class has permission to access the method, because all methods declared in interfaces are implicitly public.).
 If the virtual machine discovers there is no method with the proper name, return type, and number and types of parameters in the referenced interface, the virtual machine throw

7.5|Resolution of CONSTANT_String_info Entries
------------------------------------------------------------------------------
Putting the string encountered into one big string pool and reusing it.

7.6|Resolution of Other Types of Entries
---------------------------------------------------------------------------
The CONSTANT_Integer_info, CONSTANT_Long_info, CONSTANT_Float_info, CONSTANT_Double_info entries contain the constant values they represent within the entry itself. These are straightforward to resolve. To resolve this kind of entry, many virtual machine implementations may not have to do anything but use the value as is. Other implementations, however, may choose to do some processing on it. For example, a virtual machine on a little-endian machine could choose to swap the byte order of the value at resolve time.

7.8|Direct references:
---------------------------------
Direct references to types, class variables, and class methods are likely native pointers into the method area. A direct reference to a type can simply point to the implementation-specific data structure in the method area that holds the type data. A direct reference to a class variable can point to the class variableís value stored in the method area. A direct reference to a class method can point to a data structure in the method area that contains the data needed to invoke the method. For example, the data structure for a class method could include information such as whether or not the method is native. If the method is native, the data structure could include a function pointer to the dynamically linked native method implementation. If the method is not native, the data structure could include the methodís bytecodes, max_stack, max_locals, and so on. If there is a just-in-time-compiled version of the method, the data structure could include a pointer to that just-in-time-compiled native code.

Direct references to instance variables and instance methods are offsets. A direct reference to an instance variable is likely the offset from the start of the objectís image to the location of the instance variable. A direct reference to an instance method is likely an offset into a method table.

Using offset to represent direct references to instance variables and instance methods depends on a predictable ordering of the fields in a classís object image and the methods in a classís method table. Although implementation designers may choose any way of placing instance variables into an object image or methods into a method table, they will almost certainly use the same way for all types. Therefore, in any one implementation, the ordering of fields in an object and methods in a method table is defined and predictable.

#----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
CHAPTER 8: GARBAGE COLLECTIONS:
***************************************
Reclaim heap memory by removing not-accessible objects in local variables and operand stack
Does heap fragmentation - unused memory spaces between objects in heap are bad.
code is implementation dependent.
Conservative - some garbage collectors, however, may choose not to distinguish between genuine object references and look-alikes (int primitive pointers)

Two basic approaches to distinguishing live objects from garbage are reference counting and tracing. 

 8.1|Reference counting:
-----------------------------------
	Garbage collectors distinguish live objects from garbage objects by keeping a count for each object on the heap. 
	The count keeps track of the number of references to that object.
	cannot detect islands of objects
	has overhead of counting every time a object is used.
	not used nowadays.
 8.2|Tracing garbage(Mark and Sweep) 
-------------------------------------------------------
	Collectors actually trace out the graph of references starting with the root nodes. 
	Objects that are encountered during the trace are marked in some way. After the trace is complete, unmarked objects are known to be unreachable and can be garbage collected. 
	8.2.1|Compacting collectors:
	------------------------------------------	
	move objects to one end of heap while garbage collecting, thereby compacting.
	Updating references to moved objects is sometimes made simpler by adding a level of indirection to object references. 
	Instead of referring directly to objects on the heap, object references refer to a table of object handles. The object handles refer to the actual objects on the heap.	
	8.2.2|Copying collectors
	------------------------------------
	divides the heap into two areas, used and free
	when one area is full, execution is paused,then all live objects are moved to the other side. Cycle is repeated periodically.
	8.2.3|Generational collectors:
	---------------------------------------------
	Heap is divided into generations, younger generation areas are frequently sweeped, once a object survives multiple swipes, it progresses to the next older generation.
	Old generations are sweeped with large intervals.
	8.2.4|Adaptive Collectors 
	-------------------------------------
	 An adaptive algorithm monitors the current situation on the heap and adjusts its garbage collection technique accordingly. 
	It may tweak the parameters of a single garbage collection algorithm as the program runs. It may switch from one algorithm to another on the fly. 
	Or it may divide the heap into sub-heaps and use different algorithms on different sub-heaps simultaneously. 
	
8.3| Reference Types
--------------------------------
	Strong Reference: the normal one used, never lets go off a object for garbage collection
	Soft Reference: little better than Weak Reference, allows the object to survive some cycles of collection
	Weak Reference: Lets go of the object in the first cycle. WeakReference, WeakHashMaps(key is the weak-rerference) APIs
	Phantom Reference: Returns null, even if finalise() resurrects the dead object.
8.4| Finaliser
--------------------
	If you unreferenced object implements finalise(), GC runs finalise()
		if resurrected in finalise(), then GC will treat it as an object with no finaliser, that is it always runs finalise() only once.
		if still dead, then wipe it out.







	
	




	






	































 
