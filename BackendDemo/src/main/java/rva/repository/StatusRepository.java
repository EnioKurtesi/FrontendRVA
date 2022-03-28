package rva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
