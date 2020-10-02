package com.crash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
