package com.example.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo1.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    // यहाँ कोई फालतू मेथड न लिखें!
    // JpaRepository में findAll() पहले से आता है।
}