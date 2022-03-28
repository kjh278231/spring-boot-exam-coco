package com.example.springbootexam.service;

import com.example.springbootexam.entity.Address;
import com.example.springbootexam.entity.CellPhone;
import com.example.springbootexam.exception.AddressNotFoundException;
import com.example.springbootexam.exception.AlreadyExistCellPhoneException;
import com.example.springbootexam.exception.AlreadyExistEmailException;
import com.example.springbootexam.repository.CellPhoneRepository;
import org.springframework.stereotype.Service;
import com.example.springbootexam.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private CellPhoneRepository cellPhoneRepository;

    public AddressService(AddressRepository addressRepository, CellPhoneRepository cellPhoneRepository) {
        this.addressRepository = addressRepository;
        this.cellPhoneRepository = cellPhoneRepository;
    }

// TODO 1.1 AddressService 전체 조회 구현
//    public List<Address> getAddressList() {
//        return null;
//    }

    // TODO 2.1 AddressService 단건 조회 구현
//    public Address getAddress(Integer id) {
//        return null;
//    }

    // TODO 3.1 AddressService 저장 구현
//    public Address saveAddress(Address address) {
//        return null;
//    }

    //TODO 4.1 AddressService 삭제 구현
//    public void deleteAddress(Integer id) {}

    //TODO 5.1 AddressService 조건 조회 구현
//    public List<Address> getAddressListBySearchKeyword(String searchKeyword) {
//        return null;
//    }

    public List<Address> getAddressList() {
        return addressRepository.findAll();
    }

    public Address getAddress(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(String.format("%s not found", id)));
    }

    public Address saveAddress(Address address) {
        if (addressRepository.findByEmail(address.getEmail()).isPresent()) {
            throw new AlreadyExistEmailException(String.format("%s already exist", address.getEmail()));
        }

        return addressRepository.save(address);
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    public List<Address> getAddressListBySearchKeyword(String searchKeyword) {
        return addressRepository.findAllBySearchKeyword(searchKeyword);
    }

    public CellPhone saveCellPhone(Integer id, CellPhone cellPhone) {
        Address address = this.getAddress(id);

        boolean isAlreadyCellPhoneExist = address.getCellPhoneList().stream().anyMatch(phone -> phone.getCellPhoneNo().equals(cellPhone.getCellPhoneNo()));

        if (isAlreadyCellPhoneExist) {
            throw new AlreadyExistCellPhoneException("%s is exist");
        }

        cellPhone.setAddress(address);

        return cellPhoneRepository.save(cellPhone);
    }
}
