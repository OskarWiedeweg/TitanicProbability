package com.example.TitanicProbability.repositorys;

import com.example.TitanicProbability.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);

}
