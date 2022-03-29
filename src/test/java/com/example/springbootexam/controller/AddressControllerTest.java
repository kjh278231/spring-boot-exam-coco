package com.example.springbootexam.controller;

import com.example.springbootexam.entity.Address;
import com.example.springbootexam.exception.AddressNotFoundException;
import com.example.springbootexam.exception.AlreadyExistEmailException;
import com.example.springbootexam.exception.ExceptionResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.springbootexam.service.AddressService;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.example.springbootexam.MockData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddressService mockService;

    @Test
    @DisplayName("GET /addresses 를 호출하면 ID에 해당하는 json 정보와 HttpStatus 200을 받는다.")
    public void retrieveAddressList() throws Exception{
        given(mockService.getAddressList()).willReturn(Arrays.asList(getJaden(), getJade(), getIann(), getBradley(), getMatt()));

        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[1].name").value("Jade"));
    }


    @Test
    @DisplayName("PathVariable에 ID를 입력하고 GET /addresses 를 호출하면 ID에 해당하는 json 정보와 HttpStatus 200을 받는다.")
    public void retrieveAddress() throws Exception{
        given(mockService.getAddress(eq(1))).willReturn(getJaden());

        mockMvc.perform(get("/addresses/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.all-address.href").value("http://localhost/addresses"))
                .andExpect(jsonPath("$.name").value("Jaden"));
    }

    @Test
    @DisplayName("저장할 값을 입력하고 POST /addresses를 호출하면 저장된 201 HttpStatus code와 header.Location에 해당 값을 확인 할 수 있는 uri를 받는다.")
    public void saveAddress() throws Exception {
        given(mockService.saveAddress(Address.builder().name("New").email("new@test.com").address("경기").build()))
                .willReturn(Address.builder().id(6).name("New").email("new@test.com").address("경기").build());

        mockMvc.perform(post("/addresses").characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Address.builder().name("New").email("new@test.com").address("경기").build())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/addresses/6"));
    }

    @Test
    @DisplayName("삭제할 ID를 입력하고 DELETE /addresses를 호출하면 해당 id에 해당하는 Address 정보를 삭제한다.")
    public void deleteAddress() throws Exception {
        mockMvc.perform(delete("/addresses/{id}", 1));

        then(mockService).should().deleteAddress(eq(1));
    }

    @Test
    @DisplayName("삭제할 ID를 입력하고 DELETE /addresses를 호출하면 해당 id에 해당하는 Address 정보를 삭제한다.")
    public void getAddressBySearchKeyword() throws Exception {
        given(mockService.getAddressListBySearchKeyword("ja")).willReturn(Arrays.asList(getJaden(), getJade(), getBradley()));

        mockMvc.perform(get("/addresses").param("searchKeyword", "ja"))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("AddressNotFoundException이 발생하면 정제 된 ExceptionResponse를 리턴한다.")
    public void handleAddressNotFoundExceptionTest() throws Exception {
        given(mockService.getAddress(99)).willThrow(new AddressNotFoundException(String.format("%s not found", 99)));

        MvcResult mvcResult = mockMvc.perform(get("/addresses/{id}", 99))
                .andExpect(status().isNotFound())
                .andReturn();

        ExceptionResponse exceptionResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ExceptionResponse>() {
        });

        assertEquals("99 not found", exceptionResponse.getMessage());
        assertEquals("uri=/addresses/99", exceptionResponse.getDetail());
    }

    @Test
    @DisplayName("MethodArgumentNotValidException이 발생하면 정제된 ExceptionResponse를 리턴한다.")
    public void handleMethodArgumentNotValidTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/addresses").characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Address.builder().name("n").email("new@test.com").address("경기").build())))
                .andExpect(status().isInternalServerError())
                .andReturn();

        ExceptionResponse exceptionResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ExceptionResponse>() {
        });

        assertTrue(exceptionResponse.getMessage().contains("Validation failed"));
        assertEquals("uri=/addresses", exceptionResponse.getDetail());
    }

    @Test
    @DisplayName("MethodArgumentNotValidException이 발생하면 정제된 ExceptionResponse를 리턴한다.")
    public void handleAlreadyExistEmailExceptionTest() throws Exception {
        given(mockService.saveAddress(Address.builder().name("New").email("exist@test.com").address("경기").build()))
                .willThrow(new AlreadyExistEmailException(String.format("exist@test.com already exist", "exist@test.com")));

        MvcResult mvcResult = mockMvc.perform(post("/addresses").characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Address.builder().name("New").email("exist@test.com").address("경기").build())))
                .andExpect(status().isConflict())
                .andReturn();

        ExceptionResponse exceptionResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ExceptionResponse>() {
        });
        assertEquals("exist@test.com already exist", exceptionResponse.getMessage());
        assertEquals("uri=/addresses", exceptionResponse.getDetail());
    }

    @Test
    @DisplayName("/addresses/average-age API를 호출하면 모든 사용자의 평균 나이가 조회 된다.")
    public void retrieveAverageAge() throws Exception {
        given(mockService.getAverageAge()).willReturn(36.8);

        mockMvc.perform(get("/addresses/average-age"))
                .andExpect(jsonPath("$").value(36.8));
    }

    @Test
    @DisplayName("/addresses/all-user-name API를 호출하면 모든 사용자의 이름이 ,와 함께 조회 된다.")
    public void retrieveAllUserName() throws Exception {
        given(mockService.getAllUserNameWithComma()).willReturn("Jaden, Jade, Iann, Bradley, Matt");

        mockMvc.perform(get("/addresses/all-user-name"))
                .andExpect(jsonPath("$").value("Jaden, Jade, Iann, Bradley, Matt"));
    }
}