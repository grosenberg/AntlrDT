## Header

~~~ dot

digraph g {
	graph [rankdir=LR
		label="\n\nEntity Relation Diagram";
	 	fontsize=20;
	];
	
	node [shape=box width=1.1]
	node [fontsize=16, shape=ellipse, style=filled, color=lightgrey]; corpus; module; parser;
	node [fontsize=16, shape=record]; record_node0; record_node1;
	edge [];
	 
	record_node0 [label = "<f0> 0x10ba8| <f1>"];
	record_node1 [label = "<f0> 0xf7fc4380| <f1> | <f2> |-1"];
	 
	record_node0:f0 -> record_node1:f0 [id="0"];
	corpus->module[color=red constraint=true];
	parser->module[color=blue label="Search\nExemplars\n\n"];
}

~~~

## Next

text