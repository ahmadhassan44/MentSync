package com.example.mentsync.Chat;

import com.example.mentsync.R;

import java.util.ArrayList;
import java.util.List;

public class Topics {
    String topic;
    String descriprtion;
    int logo;
    static List<Topics> list=new ArrayList<Topics>();
    public Topics(String topic, String descriprtion, int logo) {
        this.topic = topic;
        this.descriprtion = descriprtion;
        this.logo = logo;
    }

    public static List<Topics> getList() {
        list.add(new Topics("Web Development","Talk about the high demand web wizardry", R.drawable.web));
        list.add(new Topics("App Development","Talk about the Mobile Application Development and SDLC",R.drawable.app));
        list.add(new Topics("Data Structures and Algorithms","The heart of Computer Science",R.drawable.dsa));
        list.add(new Topics("Masters","Foreign Universities applications for higher studies",R.drawable.foreign));
        list.add(new Topics("Interviews","Cracking the interview to your dream job",R.drawable.interview));
        list.add(new Topics("Work life balance","How to ace both without compromising on any",R.drawable.balance));
        return list;
    }

    public void setList(List<Topics> list) {
        this.list = list;
    }
}
