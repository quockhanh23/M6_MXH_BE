package com.example.final_case_social_web.service.friend_relation;

import com.example.final_case_social_web.model.FriendRelation;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.repository.FriendRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class FriendRelationServiceImpl implements IFriendRelationService {
    @Autowired
    private FriendRelationRepository friendRelationRepository;

    @Override
    public Iterable<FriendRelation> findAll() {
        return friendRelationRepository.findAll();
    }

    @Override
    public Optional<FriendRelation> findById(Long id) {
        return friendRelationRepository.findById(id);
    }

    @Override
    public FriendRelation save(FriendRelation friendRelation) {
        return friendRelationRepository.save(friendRelation);
    }

    @Override
    public void remove(Long id) {
        friendRelationRepository.deleteById(id);
    }


    @Override
    public Iterable<BigInteger> findAllIdUserNotFriend(Long id, Long id1) {
        return friendRelationRepository.findIdUserNotFriend(id, id1);
    }

    @Override
    public Iterable<BigInteger> findRequest(Long idUser) {
        return friendRelationRepository.findRequest(idUser);
    }

    @Override
    public Optional<FriendRelation> findByIdUserAndIdFriend(Long idUser, Long idFriend) {
        return friendRelationRepository.findByIdUserAndIdFriend(idUser, idFriend);
    }

    @Override
    public Iterable<BigInteger> findIdFriend(Long idUser) {
        return friendRelationRepository.findIdFriend(idUser);
    }

    @Override
    public List<FriendRelation> findAllListRequestAddFriendById(Long idUser) {
        return friendRelationRepository.findAllListRequestAddFriendById(idUser);
    }
}
