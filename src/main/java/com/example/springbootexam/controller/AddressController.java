package com.example.springbootexam.controller;

import com.example.springbootexam.entity.Address;
import com.example.springbootexam.entity.CellPhone;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootexam.service.AddressService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    public List<Address> retrieveAddressList() {
        return addressService.getAddressList();
    }

    @GetMapping("/addresses/{id}")
    public EntityModel<Address> retrieveAddress(@PathVariable Integer id) {
        Address address = addressService.getAddress(id);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAddressList());
        EntityModel<Address> entityModel = EntityModel.of(address);
        entityModel.add(linkTo.withRel("all-address"));

        return entityModel;
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> saveAddress(@Valid @RequestBody Address address) {
        Address savedAddress = addressService.saveAddress(address);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedAddress.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/addresses/{id}")
    public void deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
    }

    @GetMapping(value = "/addresses", params = "searchKeyword")
    public List<Address> retrieveAddressListBySearchKeyword(@RequestParam String searchKeyword) {
        return addressService.getAddressListBySearchKeyword(searchKeyword);
    }

    @GetMapping("/addresses/{id}/cell-phones")
    public List<CellPhone> retrieveCellPhoneListByAddress(@PathVariable Integer id) {
        return addressService.getAddress(id).getCellPhoneList();
    }

    @PostMapping("/addresses/{id}/cell-phones")
    public ResponseEntity<CellPhone> saveCellPhone(@PathVariable Integer id, @RequestBody CellPhone cellPhone) {
        CellPhone savedCellPhone = addressService.saveCellPhone(id, cellPhone);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedCellPhone.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //TODO 1.2 AddressController 전체 조회 구현
//    public List<Address> retrieveAddressList() {
//        return null;
//    }

    //TODO 2.2 AddressController 단건 조회 구현
//    public EntityModel<Address> retrieveAddress(Integer id) {
//        return null;
//    }


    //TODO 3.2 AddressController 저장 구현
//    public ResponseEntity<Address> saveAddress(Address address) {
//       return null;
//    }

    //TODO 4.2 AddressController 삭제 구현
//    public void deleteAddress(Integer id) {
//
//    }

// TODO 5.2 AddressController.retrieveAddressListBySearchKeyword 조건 조회 구현
    //    public List<Address> retrieveAddressListBySearchKeyword(@RequestParam String searchKeyword) {
//        return null;
//    }


    //TODO 7.2 CellPhone
//    public List<CellPhone> retrieveCellPhoneListByAddress(@PathVariable Integer id) {
//        return null;
//    }
//
//    public ResponseEntity<CellPhone> saveCellPhone(@PathVariable Integer id, @RequestBody CellPhone cellPhone) {
//       return null;
//    }
}
