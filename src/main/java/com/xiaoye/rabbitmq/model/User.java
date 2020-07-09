package com.xiaoye.rabbitmq.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("User")  //声明为文档
public class User implements Serializable {
    public String name;
    public int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
