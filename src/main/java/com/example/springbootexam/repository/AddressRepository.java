package com.example.springbootexam.repository;

import com.example.springbootexam.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query(value = "select address " +
            "from Address address " +
            "where lower(address.name) like lower(concat('%', :searchKeyword, '%'))" +
            "  or lower(address.email) like lower(concat('%', :searchKeyword, '%'))" +
            "  or lower(address.address) like lower(concat('%', :searchKeyword, '%'))"
    )
    List<Address> findAllBySearchKeyword(@Param("searchKeyword") String searchKeyword);

    Optional<Address> findByEmail(@Param("email") String email);
}
