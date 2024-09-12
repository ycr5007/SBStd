package com.example.SBStd.dto;

import com.example.SBStd.domain.Person;
import lombok.*;

public class PersonDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Requset {
        private int id;
        private int age;
        private String name;

        public Person toEntity() {
            Person persons = Person.builder()
                    .id(id)
                    .age(age)
                    .name(name)
                    .build();
            return persons;
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private int id;
        private int age;
        private String name;

        public Response(Person person) {
            this.id = person.getId();
            this.age = person.getAge();
            this.name = person.getName();
        }
    }
}
