package com.example.SBStd.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
// @Entity
// @Table(name="")
@Setter
public class Person {
    private static int lastId = 0;

    private int id;
    private int age;
    private String name;

    public Person(int age, String name) { this(lastId++, age, name); }
}
