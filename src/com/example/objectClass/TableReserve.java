package com.example.objectClass;

public class TableReserve {

private int tables, people,id;
private static TableReserve tr=null;

private TableReserve(){
	this.id=0;
	setPeople(0);
	setTables(0);
}
public static TableReserve getInstance(){
	if(tr==null){
		tr= new TableReserve();
	}
	return tr;
}
public int getTables() {
	return tables;
}

public void setTables(int tables) {
	this.tables = tables;
}

public int getPeople() {
	return people;
}

public void setPeople(int people) {
	this.people = people;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
public String getValuesString(){
	return Integer.toString(id)+" "+Integer.toString(tables)+" "+Integer.toString(people);
}
public String getTables_toS(){
	return Integer.toString(tables);
}
public String getPeople_toS(){
	return Integer.toString(people);
}
}
