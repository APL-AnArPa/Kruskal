OBJ = ../class

all: $(OBJ)/Graph.class $(OBJ)/Sort.class $(OBJ)/UnionFind.class $(OBJ)/Kruskal.class $(OBJ)/Main.class 

$(OBJ)/Main.class: Main.java
	javac -d ../class/ Main.java
	
$(OBJ)/Kruskal.class: Kruskal.java
	javac -d ../class/ Kruskal.java

$(OBJ)/Sort.class: Sort.java
	javac -d ../class/ Graph.java Sort.java	
	
$(OBJ)/UnionFind.class: UnionFind.java
	javac -d ../class/ Graph.java UnionFind.java

$(OBJ)/Graph.class: Graph.java
	javac -d ../class/ Graph.java
	
clean:
	rm -f ../class/*.class
