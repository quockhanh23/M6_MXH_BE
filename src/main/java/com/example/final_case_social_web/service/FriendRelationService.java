package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.FriendRelation;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface FriendRelationService extends GeneralService<FriendRelation> {
    Iterable<BigInteger> findAllIdUserNotFriend(Long id, Long id1);

    Iterable<BigInteger> findRequest(Long idUser);

    Optional<FriendRelation> findByIdUserAndIdFriend(Long idUser, Long idFriend);

    Iterable<BigInteger> findIdFriend(Long idUser);

    List<FriendRelation> findAllListRequestAddFriendById(Long idUser);
}
