package com.example.springbootexam.controller;

import com.example.springbootexam.entity.Address;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootexam.service.AddressService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.DoubleStream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //TODO 1.2 AddressController 전체 조회 구현
    @GetMapping("/addresses")
    public List<Address> retrieveAddressList() {
        return addressService.getAddressList();
    }

    //TODO 2.2 AddressController 단건 조회 구현
    @GetMapping("/addresses/{id}")
    public EntityModel<Address> retrieveAddress(@PathVariable Integer id) {
        EntityModel<Address> resource = EntityModel.of(addressService.getAddress(id));
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).retrieveAddressList());
        resource.add(linkBuilder.withRel("all-address"));
        return resource;
    }


    //TODO 3.2 AddressController 저장 구현
    @PostMapping("/addresses")
    public ResponseEntity<Address> saveAddress(@Valid @RequestBody Address address) {
//        System.out.println(address.getName());
        Address createdAddress = addressService.saveAddress(address);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAddress.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //TODO 4.2 AddressController 삭제 구현
    @DeleteMapping("/addresses/{id}")
    public void deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
    }

    // TODO 5.2 AddressController.retrieveAddressListBySearchKeyword 조건 조회 구현
    @GetMapping(value = "/addresses",params = "searchKeyword")
    public List<Address> retrieveAddressListBySearchKeyword(@RequestParam(value = "searchKeyword") String searchKeyword) {
        return addressService.getAddressListBySearchKeyword(searchKeyword);
    }

    // TODO 7.2 평균 나이 조회 하기
    @GetMapping("/addresses/average-age")
    public double retrieveAverageAge() {
        return this.addressService.getAverageAge();
    }

    // TODO 7.3 전체 이름 가져오기
    @GetMapping("/addresses/all-user-name")
    public String retrieveAllUserName() {
        return this.addressService.getAllUserNameWithComma();
    }

    //TODO 7.4 CellPhone
//    public List<CellPhone> retrieveCellPhoneListByAddress(@PathVariable Integer id) {
//        return null;
//    }

//    public ResponseEntity<CellPhone> saveCellPhone(@PathVariable Integer id, @RequestBody CellPhone cellPhone) {
//       return null;
//    }
}
