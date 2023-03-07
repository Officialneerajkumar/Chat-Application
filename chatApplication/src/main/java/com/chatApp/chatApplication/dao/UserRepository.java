package com.chatApp.chatApplication.dao;

import com.chatApp.chatApplication.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    @Query(value = "select * from tbl_user where user_name = :userName and status_id = 1",nativeQuery = true)
    // : denotes that the userName is a variable  that comes from the method
    public List<Users> findByUserName(String userName);

    @Query(value = "select * from tbl_user where user_id = :userId and status_id = 1",nativeQuery = true)
    public List<Users> getUserByUserId(int userId);

    @Query(value = "select * from tbl_user where status_id = 1",nativeQuery = true)
    public List<Users> getAllUsers();

    // use this method if the springboot version is lower/upper than 3.0.3
    @Modifying
    @Transactional
    @Query(value = "update tbl_user set status_id = 2 where user_id = :userId",countQuery = "select count(*) from tbl_user",nativeQuery = true)
    public void deleteUserByUserId(int userId);


}
