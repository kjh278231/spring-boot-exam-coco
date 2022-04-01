package com.example.springbootexam.service;

import com.example.springbootexam.entity.Address;
import com.example.springbootexam.exception.AddressNotFoundException;
import com.example.springbootexam.exception.AlreadyExistEmailException;
import org.springframework.stereotype.Service;
import com.example.springbootexam.repository.AddressRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // TODO 1.1 AddressService 전체 조회 구현
    public List<Address> getAddressList() {
        return addressRepository.findAll();
    }

    // TODO 2.1 AddressService 단건 조회 구현
    public Address getAddress(Integer id) {
        Optional<Address> address = addressRepository.findById(id);

        if(!address.isPresent()) {
            System.out.println("error occur!");
            throw new AddressNotFoundException(String.format("{%s} not found", id));
        }
        return address.get();
    }

    // TODO 3.1 AddressService 저장 구현
    public Address saveAddress(Address address) {
//        System.out.println(address.getName());
        for(Address add : this.getAddressList()){
            if(Objects.equals(add.getEmail(), address.getEmail()))
                throw new AlreadyExistEmailException(String.format("%s already exist", add.getEmail()));
        }
        return addressRepository.save(address);
    }

    //TODO 4.1 AddressService 삭제 구현
    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    //TODO 5.1 AddressService 조건 조회 구현
    public List<Address> getAddressListBySearchKeyword(String searchKeyword) {
        return addressRepository.findAllBySearchKeyword(searchKeyword);
    }

    //TODO 7.2 평균 나이 조회 하기
    public double getAverageAge() {
        DoubleStream doubleStream = addressRepository.findAll().stream().mapToDouble(address -> address.getAge());
        return (doubleStream.average().orElse(0));
    }

    //TODO 7.3 전체 이름 가져오기
    public String getAllUserNameWithComma() {
        Stream<String> stringStream = addressRepository.findAll().stream().map(address -> address.getName());
        return stringStream.reduce((a,b)->a+", "+b).orElse("");

    }
}
