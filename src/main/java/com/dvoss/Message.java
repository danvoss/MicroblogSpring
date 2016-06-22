package com.dvoss;

import javax.persistence.*;

/**
 * Created by Dan on 6/20/16.
 */
@Entity
@Table(name = "messages")
public class Message implements Comparable<Message> {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String text;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    // override compareTo, remember to implement Comparable above:

    @Override
    public int compareTo(Message m) {
        if (this.id > m.id) {
            return -1;
        }
        if (this.id < m.id) {
            return 1;
        }
        return 0;
    }
}
