package com.example.springbootexam;

import com.example.springbootexam.entity.Address;

public class MockData {
    public static Address getJaden() {
        return Address.builder().id(1).name("Jaden").email("jaden@test.com").address("서울").age(40).build();
    }

    public static Address getJade() {
        return Address.builder().id(2).name("Jade").email("jade@test.com").address("송파").age(40).build();
    }

    public static Address getIann() {
        return Address.builder().id(3).name("Iann").email("iann@test.com").address("울산").age(38).build();
    }

    public static Address getBradley() {
        return Address.builder().id(4).name("Bradley").email("bradley@test.com").address("부산ja").age(38).build();
    }

    public static Address getMatt() {
        return Address.builder().id(5).name("Matt").email("matt@test.com").address("천호").age(28).build();
    }
}
