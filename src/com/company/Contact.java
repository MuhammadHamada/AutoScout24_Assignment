package com.company;

import java.util.Date;

public class Contact implements Comparable<Contact>{

    private int id;
    private Date date;

    public Contact(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Contact c) {
        return this.date.compareTo(c.date);
    }
}
