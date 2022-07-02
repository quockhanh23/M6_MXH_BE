package com.example.final_case_social_web.service.friend_relation;

import com.example.final_case_social_web.model.FriendRelation;
import com.example.final_case_social_web.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface IFriendRelationService extends IGeneralService<FriendRelation> {
    Iterable<BigInteger> findAllIdUserNotFriend(Long id, Long id1);

    Iterable<BigInteger> findRequest(Long idUser);

    Optional<FriendRelation> findByIdUserAndIdFriend(Long idUser, Long idFriend);

    Iterable<BigInteger> findIdFriend(Long idUser);

    List<FriendRelation> findAllListRequestAddFriendById(Long idUser);
}
