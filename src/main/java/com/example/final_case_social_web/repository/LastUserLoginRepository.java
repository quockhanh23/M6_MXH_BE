package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.LastUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LastUserLoginRepository extends JpaRepository<LastUserLogin, Long> {

    @Modifying
    @Query(value = "select * from last_user_login order by login_time desc limit 3", nativeQuery = true)
    List<LastUserLogin> historyLogin ();

    Optional<LastUserLogin> findAllByIdUser (Long id);
}
