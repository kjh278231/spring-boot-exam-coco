package com.example.springbootexam.service;

import com.example.springbootexam.entity.Address;
import com.example.springbootexam.exception.AddressNotFoundException;
import com.example.springbootexam.exception.AlreadyExistEmailException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.springbootexam.repository.AddressRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.springbootexam.MockData.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService subject;

    @Mock
    private AddressRepository mockRepository;

    @Captor
    private ArgumentCaptor<Address> captor;

    @Test
    @DisplayName("getAddressList를 호출하면 전체 Address List를 리턴한다.")
    public void getAddressList() {
        given(mockRepository.findAll()).willReturn(Arrays.asList(getJaden(), getJade(), getIann(), getBradley(), getMatt()));

        List<Address> result = subject.getAddressList();

        then(mockRepository).should().findAll();
        assertEquals(5, result.size());
    }

    @Test
    @DisplayName("존재하는 ID를 입력하고 getAddress를 호출하면 ID에 해당하는 사용자 정보를 리턴한다.")
    public void getAddress() {
        given(mockRepository.findById(1)).willReturn(Optional.ofNullable(getJaden()));

        Address result = subject.getAddress(1);

        then(mockRepository).should().findById(eq(1));
        assertEquals("Jaden", result.getName());
    }

    @Test(expected = AddressNotFoundException.class)
    @DisplayName("존재하지 않는 ID를 입력하고 getAddress를 호출하면 AddressNotFoundException이 발생 된다.")
    public void getAddress_thenThrowAddressNotFoundException() {
        subject.getAddress(999);
    }


    @Test
    @DisplayName("새로운 Address 정보와 함께 saveAddress를 호출하면 새롭게 저장된 Address 정보를 리턴한다.")
    public void saveAddress() {
        Address address = Address.builder().name("New").email("new@test.com").address("경기도").build();

        given(subject.saveAddress(address)).willReturn(Address.builder().id(6).name("New").email("new@test.com").address("경기도").build());

        Address result = subject.saveAddress(address);

        then(mockRepository).should().save(captor.capture());
        assertEquals("New", captor.getValue().getName());
        assertEquals("new@test.com", captor.getValue().getEmail());
        assertEquals("경기도", captor.getValue().getAddress());
        assertEquals(new Integer(6), result.getId());
    }

    @Test
    @DisplayName("ID를 입력 받으면 ID와 함께 AddressRepository.deleteById를 호출한다.")
    public void deleteAddress() {
        subject.deleteAddress(1);

        then(mockRepository).should().deleteById(eq(1));
    }

    @Test
    @DisplayName("SearchKeyword를 입력하면 해당 글자가 name, email, address에 포함 된 Address 정보를 리턴한다.")
    public void getAddressBySearchKeyword() {
        given(mockRepository.findAllBySearchKeyword("ja")).willReturn(
                Arrays.asList(getJaden(), getJade(), getBradley())
        );

        List<Address> result = subject.getAddressListBySearchKeyword("ja");

        then(mockRepository).should().findAllBySearchKeyword(eq("ja"));
        assertEquals(3, result.size());
    }

    @Test(expected = AlreadyExistEmailException.class)
    @DisplayName("이미 존재하는 email인 경우 AlreadyExistEmailException 발생")
    public void alreadyExistEmailException() {
        given(mockRepository.findByEmail("exist@email")).willReturn(Optional.ofNullable(Address.builder().build()));

        subject.saveAddress(Address.builder().name("new").email("exist@email").address("test").build());
    }

}