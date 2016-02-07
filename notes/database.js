Database design:
#----------------------------------------------------------------------------------------------------------------------------------------------d

'evolution':
file - just string text files
hierarchies- to structure , 
networks - to allow for special relationships,
relational -popular
object- maps to oops
object-relational model - mix

'types'
1| transactional - small crud in transactions
	client-server => smaller scale, within intranet for a company
 	oltp => online transaction processing, large scala, public e-commerce websites.
2| decision support
	data warehouse => historical data archived from transactional dbs, used for forecasting
	data mart => smaller chunks of data extracted from warehouse
	reporting =>current data(not historical) used for reporting
3| hybrid - mix of both

'cardinality':
uniqueness of values in a column
high=> PK, identifiers like email address.
medium=> names and street address.
low=> status flags, enums.

#----------------------------------------------------------------------------------------------------------------------------------------------
'codd rules':

0|'(Yes, there is a Rule 0!)'
For a system to qualify as a RDBMS system, it must use its relational facilities (exclusively) to manage the database.
 
1|'The information rule'
The information rule simply requires all information in the database to be represented by values in column positions within rows of tables.
 
2|'The guaranteed access rule'
It says that every individual scalar value in the database must be logically addressable by specifying table name, column name and primary key.
 
3|'Systematic treatment of null values'
The DBMS is required to support a representation of "missing information and inapplicable information" that is systematic, distinct from all regular values 
(for example, "distinct from zero or any other number," in the case of numeric values), and independent of data type.
It is also implied that such representations must be manipulated by the DBMS in a systematic way.
 
4|'Active online catalog based on the relational model'
The system is required to support an online, inline, relational 'catalog' that is accessible to authorized users by means of their regular query language.
 
5|'comprehensive data sublanguage rule' (sql)
The system must support a least one relational language that 
    (a) has a linear syntax, 
    (b) can be used both interactively and within application programs, 
 	(c) supports data definition operations (including view definitions), 
 		data manipulation operations (update as well as retrieval), 
        security and integrity constraints, and transaction management 
        operations (begin, commit, and rollback).

6| 'The view updating rule'
All views that are theoretically updatable must be updatable by the system.
 
7|'High-level insert, update, and delete'
Support set-at-a-time INSERT, UPDATE, and DELETE operators at all relation levels.
 
8|'Physical data independence'
Should not depend on the underlying storage mechanisms.
 
9|'Logical data independence'
The user view should not be dependent on changes in logical structure (schema) (tough to implement)
 
10|'Integrity independence'
enforce integrity by itself not by external means
must be specified separately from application programs and stored in the catalog. 
must be possible to change such constraints as and when appropriate without unnecessarily affecting existing applications.

11| 'Distribution independence'
Existing applications should continue to operate successfully 
(a) when a distributed version of the DBMS is first introduced; 
(b) when existing distributed data is redistributed around the system.
 
12| 'The nonsubversion rule'
If the system provides a low-level (record-at-a-time) interface, then that interface cannot be used to subvert the system (e.g.) bypassing a relational 
security or integrity constraint.
#----------------------------------------------------------------------------------------------------------------------------------------------
'Normalisation-normal forms'
splitting data with relationships

'1NF'=>	all records in a table must have same number columns

'2NF'=>	non-key column is a fact about another column based on key column
		also satisfy '1NF'
	bad=> 	PART | WAREHOUSE | QUANTITY | WAREHOUSE-ADDRESS  #WAREHOUSE is related to key field PART but WAREHOUSE-ADDRESS is tied to WAREHOUSE
	good=> 	PART | WAREHOUSE | QUANTITY #table1
	  		WAREHOUSE | QUANTITY		#table2

'3NF'=>	non-key column is not related to key column, 
		also satisfy '2NF'
	bad=>	WORKER|SHOPFLOOR|LOCATION #	LOCATION is not wired to WORKER but SHOPFLOOR
	good=> 	WORKER|SHOPFLOOR  			#table1
			SHOPFLOOR|LOCATION			#table2

'4NF'=> a record type should not contain two or more independent multi-valued facts about an entity
		also satisfy '3F'
	bad=> 	WORKER|SKILL|LANGUAGE # language and skill are independent of each other, though related to worker
	good=> 	WORKER|SKILL 
			WORKER|LANGUAGE


					



