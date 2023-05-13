package com.example.demo.web.model;

import java.net.URI;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Table()
@Data
@ToString
//@AllArgsConstructor
//@NoArgsConstructor
public class AccountHolder {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@Column
private String name;
@Column
private String surname;
@Column
private URI httpImageLink;
public AccountHolder(int id, String name, String surname, URI httpImageLink) {
	super();
	this.id = id;
	this.name = name;
	this.surname = surname;
	this.httpImageLink = httpImageLink;
}
public AccountHolder() {
	super();
	// TODO Auto-generated constructor stub
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSurname() {
	return surname;
}
public void setSurname(String surname) {
	this.surname = surname;
}
public URI getHttpImageLink() {
	return httpImageLink;
}
public void setHttpImageLink(URI httpImageLink) {
	this.httpImageLink = httpImageLink;
}


}
