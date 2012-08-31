package com.jain.i18N;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import com.jain.i18N.domain.Person;

@SuppressWarnings("serial")
@SessionScoped
public class PersonDataHandler implements Serializable {
	private List<Person> persons;
	
	public PersonDataHandler() {
		persons = new ArrayList<Person>();
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public void addPerson(Person person) {
		this.persons.add(person);
	}
	
	public void removePerson(Person person) {
		this.persons.remove(person);
	}
}
