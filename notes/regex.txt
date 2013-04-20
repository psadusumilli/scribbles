0:ENGINE TEST

regex|regex not => regex only is 'regex-directed', it is eager
regex|regex not => regex, regex not, is text-directed, it is lazy and allows back quantifiers

1: LITERAL 
grey =>  grey, earlgrey NOT gray


2:CHARACTER CLASSES
^,-,\	are the only metacharacters inside [ ], so we can freely use * +

gr[ae]y => grey, gray NOT greey graey graeey
gr[e-g,a-b]y => grey, gray
gr[e-g0-9]y => grey,gr8y NOT gre8y
gr[ae][ae]y => greey graey
gr[^e]y => gray,gr8y NOT grey
gr[*]y => gr*y
gr[\^]y => gr^y - \ to escape metacharacters
gr[\d]y => gr8y - any digit shortform to gr[0-9]
gr[\w]y => grey,gray,gr8y,gr_y - any word character includes [a-zA-Z0-9_]
gr[\s]y => gr y, gr
y - space, tab and newline - equivalent to gr[\^s]y
gr[\D]y => grey,gray NOT gr8y - equivalent to gr[\^d]y
gr[\S]y => grey,gr8y NOT gr y


3.REPETITION
colou*r => color colour colouur NOT coloxr- 'u' 0 or more 
colou?r => color colour NOT colouur, coloxr - 'u' 0 or 1
colou+r => colour, colourr NOT color - 'u' 1 or more
colo[ux]*r => color, colour, coloxr, colouxr , colouur
colo[ux]?r => color, colour, coloxr NOT colouxr , colouur - as entire character class [ux] can match 0 or 1 times only.
f{2} => stuff stufff	- f appearing exactly 2 times, so matches ff twice in stu{ff} and stu{ff}f
f{2,} => stuff, stufff - f 2 or more times, matches ff and fff
f{2,3} => stuff, stufff, stuffff - will get ff and fff only not ffff
".*" => matches "colour" - '.' dot matches any character within the double quotes, * multiplies to 0 or many

3:ANCHOR
^gr => grey gray NOT angry - ^ will find match only at start of line/string (varies with engines)
ay$ => gray NOT grey - $ will find match at end of line/string(varies with engines)
^grey$ => grey NOT earlgrey
\Ag => g in grey - start of string
y\z => y in grey- end of string 
\bis\b => The island is beautiful - will match only word 'is' and NOT 'is' in island, word boundary match


4:ALTERNATE
kenny|token|stan => kenny, token, stan from sample (kenny cartman kyle stan chef token streisand)
get(Value)?|set(Value)? => get getValue set setValue

5:GROUPING/BACKREFERENCES
Feb(ruary)? 23(rd)? => February 23rd, February 23, Feb 23rd and Feb 23 - ? is a greedy operator so given priority by engine.
Feb(?:ruary)? => February Feb - ?: after the opening bracket will make regex to avoid storing backreferencesm in this case 'ruary'.
<([A-Z][A-Z0-9]*)\b[^>]*>.*?</\1> => <p>blue</p>, <p><span>red</span></p> - \1 denotes the first backreference ([A-Z][A-Z0-9]*) which will be 'p' of <p>.
([a-c])x\1x\1 => axaxa, bxbxb and cxcxc, repeated references.
(q?)b\1 => b, ab ('a' is backreference), (q)?b\1 may not match 'b' but will match 'ab' as \1 refers to a Failed Group. JS regex does not fail for failed groups.
(\2two|(one))+ => oneonetwo, forward referencing
(\1two|(one))+ => oneonetwo, nested referencing
(a)(b)?\7 => invalid referencing 

[feb]* => f, e, b, fe,ee,fff, feb
(feb)* => feb NOT ff ee
\b(\w+)\s+\1\b => sheep sheep, to select doubled words
(?i)CA(?-i)T => caT, CAT NOT cat, CAt, case insensitive search

6:GREEDINESS/LAZINESS/POSSESSIVE
<.+> => <p>blue</p> matches whole '<p>blue</p>'' instead of <p> or </p> - + is greedy, engine will apply rule until a mismatch occurs,then backtracks to the best match
<(/)*[a-zA-Z][a-zA-Z0-9]*> => will match <p></p> properly
<.+?> => <p> </p>, '?' will make + lazy 
<[^>]+> => <p> </p>, alternative, better performance as it avoids back tracking
"[^"]*+" => "abc", '+' will make * possessive, improves performance as it avoids backtracking which will happen for "[^"]*"
 