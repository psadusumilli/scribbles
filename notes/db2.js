#BASICS:

1) Atomicity:
Atomicity requires that each transaction is "all or nothing": if one part of the transaction fails, the entire transaction fails, and the database state is left unchanged. An atomic system must guarantee atomicity in each and every situation, including power failures, errors, and crashes. To the outside world, a committed transaction appears (by its effects on the database) to be indivisible ("atomic"), and an aborted transaction does not happen.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
2) Consistency
The consistency property ensures that any transaction will bring the database from one valid state to another. Any data written to the database must be valid according to all defined rules, including but not limited to constraints, cascades, triggers, and any combination thereof. This does not guarantee correctness of the transaction in all ways the application programmer might have wanted (that is the responsibility of application-level code) but merely that any programming errors do not violate any defined rules.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
3) Isolation
Main article: Isolation (database systems)
The isolation property ensures that the concurrent execution of transactions results in a system state that would be obtained if transactions were executed serially, i.e. one after the other. Providing isolation is the main goal of concurrency control. Depending on concurrency control method, the effects of an incomplete transaction might not even be visible to another transaction.[citation needed]
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
4) Durability
Main article: Durability (database systems)
Durability means that once a transaction has been committed, it will remain so, even in the event of power loss, crashes, or errors. In a relational database, for instance, once a group of SQL statements execute, the results need to be stored permanently (even if the database crashes immediately thereafter). To defend against power loss, transactions (or their effects) must be recorded in a non-volatile memory.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1) Lost updates: ONE UPDATE OVERWRITING THE OTHER
Two applications, A and B, might both read the same row from the database, and both calculate new values for one of its columns based on the data these applications read. 
If A updates the row with its new value and B then also updates the row, the update performed by A is lost
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
2) Non-repeatable reads: UPDATE/DELETE HAPPENING IN BACKGROUND
Access to uncommitted data: 
Application A might update a value in the database, and application B might read that value before it was committed. 
Then, if the value of A is not later committed, but backed out, the calculations performed by B are based on uncommitted (and presumably invalid) data.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
3) Phantom read :
Your application executes a query that reads a set of rows based on some search criterion. 
Another application inserts new data or updates existing data that would satisfy your applications query.
Your application repeats the query from the first step (within the same unit of work). 
Now some additional phantom rows are returned as part of the result set that were not returned when the query was initially executed in the first step.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

#ISOLATION LEVELS IN DB2:
DB2 Universal Database provides different levels of protection to isolate the data from each of the database applications while it is being accessed. These levels of protection are known as isolation levels, or locking strategies. Choosing an appropriate isolation level ensures data integrity and also avoids unnecessary locking. The isolation levels supported by DB2 are listed below, ordered in terms of concurrency, starting with the maximum:

1) Uncommitted Read: NO LOCKS
************************************
The Uncommitted Read (UR) isolation level, also known as "dirty read," is the lowest level of isolation supported by DB2. It can be used to access uncommitted data changes of other applications. For example, an application using the Uncommitted Read isolation level will return all of the matching rows for the query, even if that data is in the process of being modified and may not be committed to the database. You need to be aware that if you are using Uncommitted Read, two identical queries may get different results, even if they are issued within a unit of work, since other concurrent applications can change or modify those rows that the first query retrieves.
Uncommitted Read transactions will hold very few locks. Thus they are not likely to wait for other transactions to release locks. If you are accessing read-only tables or it is acceptable for the application to retrieve uncommitted data updated by another application, use this isolation level, because it is most preferable in terms of performance.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
2) Cursor Stability: SINGLE ROW LOCK WHERE CURSOR IS PLACED 
*********************************************************************
The Cursor Stability (CS) isolation level is the default isolation level and locks any row on which the cursor is positioned during a unit of work. The lock on the row is held until the next row is fetched or the unit of work is terminated. If a row has been updated, the lock is held until the unit of work is terminated. A unit of work is terminated when either a COMMIT or ROLLBACK statement is executed.
An application using Cursor Stability cannot read uncommitted data. In addition, the application locks the row that has been currently fetched, and no other application can modify the contents of the current row. As the application locks only the row on which the cursor is positioned, two identical queries may still get different results even if they are issued within a unit of work.
When you want the maximum concurrency while seeing only committed data from concurrent applications, choose this isolation level.

summary => Use the Cursor Stability isolation level when you want the maximum concurrency while seeing only committed data from concurrent applications.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
3) Read Stability: ROWS FETCHED ARE LOCKED
*************************************************
The Read Stability (RS) isolation level locks those rows that are part of a result set. If you have a table containing 100,000 rows and the query returns 10 rows, then only 10 rows are locked until the end of the unit of work.
An application using Read Stability cannot read uncommitted data. Instead of locking a single row, it locks all rows that are part of the result set. No other application can change or modify these rows. This means that if you issue a query twice within a unit of work, the second run can retrieve the same answer set as the first. However, you may get additional rows, as another concurrent application can insert rows that match to the query.

summary=>Use the Read Stability isolation level when your application operates in a concurrent environment. This means that qualified rows have to remain stable for the duration of the unit of work.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
4) Repeatable Read: TABLE L0CKED or MAX_LOCK ROWS POSSIBLE
**********************************************************************
The Repeatable Read (RR) isolation level is the highest isolation level available in DB2. It locks all rows that an application references within a unit of work, no matter how large the result set. In some cases, the optimizer decides during plan generation that it may get a table level lock instead of locking individual rows, since an application using Repeatable Read may acquire and hold a considerable number of locks. The values of the LOCKLIST and MAXLOCKS database configuration parameters will affect this decision.
An application using Repeatable Read cannot read uncommitted data of a concurrent application. As the name implies, this isolation level ensures the repeatable read to applications, meaning that a repeated query will get the same record set as long as it is executed in the same unit of work. Since an application using this isolation level holds more locks on rows of a table, or even locks the entire table, the application may decrease concurrency. You should use this isolation level only when changes to your result set within a unit of work are unacceptable.

summary=>Use the Repeatable Read isolation level if changes to your result set are unacceptable. This isolation level provides minimum concurrency.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

