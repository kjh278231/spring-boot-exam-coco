package com.example.springbootexam.controller;

import com.example.springbootexam.entity.Address;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootexam.service.AddressService;

import java.util.List;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //TODO 1.2 AddressController 전체 조회 구현
    public List<Address> retrieveAddressList() {
        return null;
    }

    //TODO 2.2 AddressController 단건 조회 구현
    public EntityModel<Address> retrieveAddress(Integer id) {
        return null;
    }


    //TODO 3.2 AddressController 저장 구현
    public ResponseEntity<Address> saveAddress(Address address) {
       return null;
    }

    //TODO 4.2 AddressController 삭제 구현
    public void deleteAddress(Integer id) {

    }

    // TODO 5.2 AddressController.retrieveAddressListBySearchKeyword 조건 조회 구현
    public List<Address> retrieveAddressListBySearchKeyword(@RequestParam String searchKeyword) {
        return null;
    }


    //TODO 7.2 CellPhone
//    public List<CellPhone> retrieveCellPhoneListByAddress(@PathVariable Integer id) {
//        return null;
//    }

//    public ResponseEntity<CellPhone> saveCellPhone(@PathVariable Integer id, @RequestBody CellPhone cellPhone) {
//       return null;
//    }
}
