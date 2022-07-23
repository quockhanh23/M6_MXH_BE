package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.TheGroup;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.GroupParticipantService;
import com.example.final_case_social_web.service.GroupService;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/groups")
@Slf4j
public class GroupRestController {

    @Autowired
    private GroupParticipantService groupParticipantService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/allGroup")
    public ResponseEntity<Iterable<TheGroup>> allGroup() {
        Iterable<TheGroup> theGroupIterable = groupService.findAll();
        return new ResponseEntity<>(theGroupIterable, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheGroup> getOne(@PathVariable Long id) {
        Optional<TheGroup> theGroupOptional = groupService.findById(id);
        if (!theGroupOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(theGroupOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/createGroup")
    public ResponseEntity<TheGroup> createGroup(@RequestBody TheGroup theGroup, @RequestParam Long idUser) {

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        theGroup.setCreateBy(userOptional.get().getFullName());
        theGroup.setIdUserCreate(userOptional.get().getId());
        theGroup.setCreateAt(new Date());
        theGroup.setNumberUser(0L);
        if (theGroup.getAvatarGroup() == null) {
            theGroup.setAvatarGroup("");
        }
        if (theGroup.getCoverGroup() == null) {
            theGroup.setCoverGroup("");
        }
        if (theGroup.getGroupName() == null) {

        }
        if (theGroup.getType() == null) {

        }
        groupService.save(theGroup);
        return new ResponseEntity<>(theGroup, HttpStatus.OK);
    }
}
