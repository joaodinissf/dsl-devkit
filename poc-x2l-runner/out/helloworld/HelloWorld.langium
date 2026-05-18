grammar HelloWorld
import 'Terminals'

entry Model infers Model:
    greetings+=Greeting * (keywordsExample=KeywordsExample )?  
;

Greeting infers Greeting:
    'Hello' name=ID  '!'  
;

KeywordsExample infers KeywordsExample:
    option=OptionOne  | option=OptionTwo  | option=OptionThree  | option=OptionFour  
;

OptionOne returns string:
    KeyOne 
;

OptionTwo returns string:
    KeyTwo KeyOther  
;

OptionThree returns string:
    SimpleKeyFour 
;

OptionFour returns string:
    SimpleKeyFive EnumLikeOther  
;

KeyOne returns string:
    ID 
;

KeyTwo returns string:
    ID 
;

KeyOther returns string:
    ID 
;

SimpleKeyFour returns string:
    "four" 
;

SimpleKeyFive returns string:
    "five" 
;

EnumLikeOther returns string:
    "four" | "five" | "six" 
;

