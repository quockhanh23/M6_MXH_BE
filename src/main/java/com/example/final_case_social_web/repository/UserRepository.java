package com.example.final_case_social_web.repository;


import com.example.final_case_social_web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Modifying
    @Query(value = "select * from user_table join user_role  on user_table.id = user_role.user_id where role_id = 1", nativeQuery = true)
    Iterable<User> findAllRoleUser();
    User findByUsername(String username);
    @Modifying
    @Query(value = "select * from user_table join user_role  on user_table.id = user_role.user_id where role_id = 1 and status = 'Active' order by user_id desc limit 0, 10", nativeQuery = true)
    List<User> findAllUser();

}
