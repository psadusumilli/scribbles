
grep - global regular expression print
fgrep - fast grep - no regex => grep -F
egrep - extended regex => grep -E

echo grey | grep -E "[a]*"
echo grey | grep -E "[a]{0}"
echo grey | grep -E "(a){0}"
echo grey | grep -E "[r-z]{1}"
echo grey | grep -E "[^a]"
echo "the greying grey monster"| grep '\bgrey\b' -o
>grey

echo grey | grep -E "[a]"
>no result

$ echo '<p>man</p>' | grep -E "(<[/]*p>)" -o
<p>	
</p>
#using back references 1 points to previous match 'p'
echo '<p>man</p>' | grep -E "<(p)>(.*)</\1>" -o
<p>man</p>

$ echo "He is a man" | grep -E "^He" -o 
He
echo "He is a man" | grep -E "man$" -o
man

#------------------------------------------------------------------------------------------------------------------------------------------------------
#1:get time occurrences of key
grep MQRC_Q_MGR_NOT_AVAILABLE *.log | grep [0-9]/[0-9][0-9]/[0-9][0-9]\\s*[0-9][0-9]*:[0-9][0-9]:[0-9][0-9] -o | sort | uniq > time.txt
5/14/13 11:46:37
5/14/13 14:48:15
#------------------------------------------------------------------------------------------------------------------------------------------------------
#2: get data within xml tags
grep  "Sent TXT message to" b2_postprocess.log*  -B 200 | grep "<messageId>" | sort | uniq -c > ids.txt
<messageId>23124314xxxdwdqc</messageId>

echo "<messageId>91b7753f3c6b0ae0573dd2adf424cc95</messageId>" | sed s/\<[\/]messageId\>// | sed s/\<messageId\>//
91b7753f3c6b0ae0573dd2adf424cc95

#------------------------------------------------------------------------------------------------------------------------------------------------------
#4: pick table names of certain sqls
grep -E "SELECT|INSERT" SystemOut.log | grep -E "(FROM|INTO)\s+?[A-Z]+\.[A-Z_0-9]+" -o | sed s/FROM// | sed s/INTO//

PNASCMOD.B2_CONTACT_XREF
PNASCMOD.IPP_CLM_SEARCH

#pick predicates of SELECT sqls
grep -E "SELECT" SystemOut.log | head -n 3 | grep -E "UR\[.*\]" 
grep -E "INSERT" SystemOut.log | head -n 3 | grep -E "\)\[.*\]"

#------------------------------------------------------------------------------------------------------------------------------------------------------
#5: pick between timelines
grep "6/3/13" SystemOut.log| grep -E "16:0[0-1]"

from 6/3/13 16:00 to 16:01
[6/3/13 16:12:16:001 EDT] 00000795 SystemOut     O [2013-06-03 16:12:16,001] [MessageListenerThreadPool : 20] INFO  com.bcbsa.blue2.dao.Blue2Dao - INSERT INTO PNASCO.IPP_MSG_STATE(MSG_ID, MSG_ST_TS, MSG_ST_CD, BLUE2_USER_ID, PART_KEY_NUM) VALUES(?, ?, ?, ?, ?)[6ba545d1d88cfc186d01fc0847d047db, 2013-06-03 16:12:15.868, PRSD, NZQG6S, 76]
[6/3/13 16:12:16:013 EDT] 00004106 SystemOut     O [2013-06-03 16:12:16,013] [WebContainer : 13381] INFO  com.bcbsa.csdp.util.Blue2Config - For boid: 8342 , returning Business Unit : WellPoint HOME
#------------------------------------------------------------------------------------------------------------------------------------------------------


