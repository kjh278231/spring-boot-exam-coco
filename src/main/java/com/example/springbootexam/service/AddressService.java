package com.example.springbootexam.service;

import com.example.springbootexam.entity.Address;
import org.springframework.stereotype.Service;
import com.example.springbootexam.repository.AddressRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // TODO 1.1 AddressService 전체 조회 구현
    public List<Address> getAddressList() {
        return null;
    }

    // TODO 2.1 AddressService 단건 조회 구현
    public Address getAddress(Integer id) {
        return null;
    }

    // TODO 3.1 AddressService 저장 구현
    public Address saveAddress(Address address) {
        return null;
    }

    //TODO 4.1 AddressService 삭제 구현
    public void deleteAddress(Integer id) {}

    //TODO 5.1 AddressService 조건 조회 구현
    public List<Address> getAddressListBySearchKeyword(String searchKeyword) {
        return null;
    }

    //TODO 7.2 평균 나이 조회 하기
    public double getAverageAge() {
        return 0;
    }

    //TODO 7.3 전체 이름 가져오기
    public String getAllUserNameWithComma() {
        return null;
    }
}
