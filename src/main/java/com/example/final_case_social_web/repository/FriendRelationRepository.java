package com.example.final_case_social_web.repository;

import com.example.final_case_social_web.model.FriendRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    @Modifying
    @Query(value = "select id from user_table where id not in (select id_friend from friend_relation where id_user = :id1) and id != :id2 ", nativeQuery = true)
    Iterable<BigInteger> findIdUserNotFriend(@Param("id1") Long id2, @Param("id2") Long id3);

    Optional<FriendRelation> findByIdUserAndIdFriend(Long idUser, Long idFriend);

    @Modifying
    @Query(value = "select id_friend from friend_relation where id_user = :id1 and status_friend = '2'", nativeQuery = true)
    Iterable<BigInteger> findIdFriend(@Param("id1") Long id2);

    @Modifying
    @Query(value = "select * from user_table join friend_relation on user_table.id = friend_relation.id_user where id_user= :idUser and friend_relation.status_friend = 'waiting'", nativeQuery = true)
    List<FriendRelation> findAllListRequestAddFriendById(@Param("idUser") Long idUser);

    @Modifying
    @Query(value = "select * from friend_relation where id_user= :idUser and friend_relation.status_friend = 'Friend'", nativeQuery = true)
    List<FriendRelation> listFriendByIdUser(@Param("idUser") Long idUser);
}
