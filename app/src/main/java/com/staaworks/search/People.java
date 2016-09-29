package com.staaworks.search;

import java.util.ArrayList;

/**
 * Created by Ahmad Alfawwaz on 9/17/2016
 */
public class People extends ArrayList<Person> {
    private static final long SERIAL_VERSION_UID = 736753L;
    private int current = 0;
    private boolean allTaken = false;


    @Override
    public boolean add(Person object) {
        if (!this.contains(object)) {
            return super.add(object);
        }
        else {
            return false;
        }
    }


    public People getNext() {
        int start = current;
        int stop = current + 10;
        current += 10;
        People people = new People();


        if (size() <= 10 && !allTaken) {
            people = this;
            System.out.println("CONTACT : " + size() + "\n" + people.size());
            allTaken = true;
        }

        else if (size() >= stop && !allTaken) {
            for (Person person: subList(start, stop)) {
                people.add(person);
            }
        }

        else if (!allTaken) {
            for (Person person: subList(start, size())) {
                people.add(person);
            }
            allTaken = true;
        }
        return people;
    }
}