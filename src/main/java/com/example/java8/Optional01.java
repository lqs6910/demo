package com.example.java8;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class Optional01 {

    public static void main(String[] args) {
        Insurance insurance = new Insurance("北京求实技术有限公司","北京后沙峪");
        Car car = new Car(Optional.of(insurance));
        People people = new People(Optional.of(car));
        
        String name = Optional.of(people)
            .flatMap(People::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName)
            .orElse("Unkonwn");
        System.out.println(name);

        Optional<Insurance> insuranceOptional = Optional.of(insurance);
        insuranceOptional.filter(in -> "北京求实技术有限公司".equals(in.getName()))
            .ifPresent(in -> System.out.println(in.getAddr()));

    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class People { 
    private Optional<Car> car;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Car{
    private Optional<Insurance> insurance;
}

@Data
@AllArgsConstructor
class Insurance{
    @NonNull private String name;
    private String addr;
}