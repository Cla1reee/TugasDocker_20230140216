package com.tugas.deploy.repository;

import com.tugas.deploy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Spring Data JPA akan otomatis membuatkan logika pencariannya
    User findByNim(String nim);
}