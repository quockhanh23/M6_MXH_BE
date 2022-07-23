package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.common.Constants;
import com.example.final_case_social_web.model.FriendRelation;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.FriendRelationService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/friends")
@Slf4j
public class FriendRelationController {

    @Autowired
    private FriendRelationService friendRelationService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Iterable<FriendRelation>> listAllFriendRelation() {
        Iterable<FriendRelation> friendRelations = friendRelationService.findAll();
        if (friendRelations == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(friendRelations, HttpStatus.OK);
    }

    // Danh sách những người đã gửi kết bạn trong trạng thái chờ đồng ý
    @GetMapping("/findAllListRequestAddFriendById")
    public ResponseEntity<Iterable<FriendRelation>> findAllListRequestAddFriendById(@RequestParam Long idUser) {
        Iterable<FriendRelation> friendRelations = friendRelationService.findAllListRequestAddFriendById(idUser);
        if (!friendRelations.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(friendRelations, HttpStatus.OK);
    }

    // Danh sách bạn bè
    @GetMapping("/listFriend")
    public ResponseEntity<Iterable<FriendRelation>> listFriend(@RequestParam Long idUser) {
        Iterable<FriendRelation> friendRelations = friendRelationService.listFriendByIdUser(idUser);
        if (!friendRelations.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(friendRelations, HttpStatus.OK);
    }

    // Gửi lời mời kết bạn
    @DeleteMapping("/sendRequestFriend")
    public ResponseEntity<Iterable<FriendRelation>> senRequestFriend(@RequestParam Long idUser, @RequestParam Long idFriend) {
        Optional<FriendRelation> optionalFriendRelation = friendRelationService.findByIdUserAndIdFriend(idUser, idFriend);
        Optional<FriendRelation> optionalFriendRelation2 = friendRelationService.findByIdUserAndIdFriend(idFriend, idUser);
        Optional<User> user = userService.findById(idFriend);
        Optional<User> user2 = userService.findById(idUser);
        if (Objects.equals(idUser, idFriend)) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        if (user.get().getStatus().equals(Constants.statusUser2)) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
        if (!optionalFriendRelation.isPresent()) {
            FriendRelation friendRelation1 = new FriendRelation();
            friendRelation1.setUserLogin(user2.get());
            friendRelation1.setIdFriend(idFriend);
            friendRelation1.setStatusFriend(Constants.statusFriend3);
            friendRelation1.setIdUser(idUser);
            friendRelation1.setFriend(user.get());
            friendRelationService.save(friendRelation1);
        } else {
            optionalFriendRelation.get().setStatusFriend(Constants.statusFriend3);
            friendRelationService.save(optionalFriendRelation.get());
        }
        if (!optionalFriendRelation2.isPresent()) {
            FriendRelation friendRelation2 = new FriendRelation();
            friendRelation2.setFriend(user2.get());
            friendRelation2.setIdUser(idFriend);
            friendRelation2.setStatusFriend(Constants.statusFriend3);
            friendRelation2.setIdFriend(idUser);
            friendRelation2.setUserLogin(user.get());
            friendRelationService.save(friendRelation2);
        } else {
            optionalFriendRelation2.get().setStatusFriend(Constants.statusFriend3);
            friendRelationService.save(optionalFriendRelation2.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Đồng ý kết bạn
    @DeleteMapping("/acceptRequestFriend")
    public ResponseEntity<Iterable<FriendRelation>> acceptRequestFriend(@RequestParam Long idUser, @RequestParam Long idFriend) {
        Optional<FriendRelation> optionalFriendRelation = friendRelationService.findByIdUserAndIdFriend(idUser, idFriend);
        Optional<FriendRelation> optionalFriendRelation2 = friendRelationService.findByIdUserAndIdFriend(idFriend, idUser);
        if (!optionalFriendRelation.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!optionalFriendRelation2.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (Objects.equals(idUser, idFriend)) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        Optional<User> user = userService.findById(idFriend);
        if (user.get().getStatus().equals(Constants.statusUser2)) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
        optionalFriendRelation.get().setStatusFriend(Constants.statusFriend1);
        optionalFriendRelation2.get().setStatusFriend(Constants.statusFriend1);
        friendRelationService.save(optionalFriendRelation.get());
        friendRelationService.save(optionalFriendRelation2.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // API tìm kiếm User gửi Request kết bạn đến mình
    @GetMapping("/friendRequest/{idUser}")
    public ResponseEntity<List<User>> findRequest(@PathVariable("idUser") Long idUser) {
        List<User> users = new ArrayList<>();
        Iterable<BigInteger> idFriend = friendRelationService.findRequest(idUser);
        for (BigInteger id : idFriend) {
            Optional<User> user = userService.findById(id.longValue());
            users.add(user.get());
        }
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // API đồng ý kết bạn
    @GetMapping("/acceptance/{idUser}/{idRequest}")
    public ResponseEntity<Iterable<FriendRelation>> acceptFriend(@PathVariable("idUser") Long idUser, @PathVariable("idRequest") Long idRequest) {
        Optional<FriendRelation> friendRelationSend = friendRelationService.findByIdUserAndIdFriend(idRequest, idUser);
        Optional<FriendRelation> friendRelationReceive = friendRelationService.findByIdUserAndIdFriend(idUser, idRequest);
        if (friendRelationSend == null || friendRelationReceive == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        friendRelationSend.get().setStatusFriend("2");
        friendRelationService.save(friendRelationSend.get());
        friendRelationReceive.get().setStatusFriend("2");
        friendRelationService.save(friendRelationReceive.get());
        List<FriendRelation> result = new ArrayList<>();
        result.add(friendRelationSend.get());
        result.add(friendRelationReceive.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // API hủy kết bạn, Hủy yêu cầu kết bạn
    @DeleteMapping(value = "/{idUser}/{idFriend}")
    public ResponseEntity<FriendRelation> deleteFriendRelation(@PathVariable("idUser") Long idUser, @PathVariable("idFriend") Long idFriend) {
        Optional<FriendRelation> friendRelationSend = friendRelationService.findByIdUserAndIdFriend(idUser, idFriend);
        Optional<FriendRelation> friendRelationReceive = friendRelationService.findByIdUserAndIdFriend(idFriend, idUser);
        if (friendRelationSend == null || friendRelationReceive == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        friendRelationService.remove(friendRelationSend.get().getId());
        friendRelationService.remove(friendRelationReceive.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Tìm danh sách bạn chung
    @GetMapping("/listMutualFriend/{idUser}/{idFriend}")
    public ResponseEntity<List<User>> listMutualFriend(@PathVariable("idUser") Long idUser, @PathVariable("idFriend") Long idFriend) {
        List<User> listFriendOfUser = new ArrayList<>();
        List<User> listFriendOfFriend = new ArrayList<>();
        List<User> listMutualFriend = new ArrayList<>();
        Iterable<BigInteger> idFriendOfIdUser = friendRelationService.findIdFriend(idUser);
        Iterable<BigInteger> idFriendOfIdFriend = friendRelationService.findIdFriend(idFriend);
        for (BigInteger id : idFriendOfIdUser) {
            Optional<User> user = userService.findById(id.longValue());
            listFriendOfUser.add(user.get());
        }
        for (BigInteger id : idFriendOfIdFriend) {
            Optional<User> user = userService.findById(id.longValue());
            listFriendOfFriend.add(user.get());
        }
        for (int i = 0; i < listFriendOfUser.size(); i++)
            for (int j = 0; j < listFriendOfFriend.size(); j++) {
                if (listFriendOfUser.get(i).getId() == listFriendOfFriend.get(j).getId()) {
                    listMutualFriend.add(listFriendOfUser.get(i));
                }
            }
        if (listMutualFriend == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listMutualFriend, HttpStatus.OK);
    }
}
