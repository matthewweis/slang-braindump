// https://json.org

grammar JSON;

options {
	output=AST;
	ASTLabelType=Object;
	k=1;
}

@header { package org.sireum.parser.json; }

@lexer::header { package org.sireum.parser.json; }


valueFile: value EOF ;

value: STRING | NUMBER | object | array | 'true' | 'false' | 'null' ;

object: '{' ( STRING ':' value ( ',' STRING ':' value )* )? '}' ;

array: '[' ( value (',' value)* )? ']' ;

STRING: '"' ( ESC | ~ ( '"' | '\\' | '\u0000' .. '\u001F' ) )* '"' ;

NUMBER: '-'? INT ( '.' ( '0' .. '9' )+ )? EXP? ;

fragment ESC: '\\' ( '"' | '\\' | '/' | 'b' | 'f' | 'n' | 'r' | 't' | 'u' HEX HEX HEX HEX ) ;

fragment HEX: ( '0' .. '9' ) | ( 'a' .. 'f' ) | ( 'A' .. 'F' ) ;

fragment INT: '0' | ( '1' .. '9' ) ( '0' .. '9' )* ;

fragment EXP: ( 'E' | 'e' ) ( '+' | '-' )? INT ;

WS: ( ' ' | '\n' | '\r' | '\t' )+ {$channel=HIDDEN;} ;