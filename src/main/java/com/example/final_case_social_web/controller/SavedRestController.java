package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.common.Constants;
import com.example.final_case_social_web.dto.PostDTO;
import com.example.final_case_social_web.dto.SavedDTO;
import com.example.final_case_social_web.dto.UserDTO;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.model.Saved;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.PostService;
import com.example.final_case_social_web.service.SavedService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/saves")
@Slf4j
public class SavedRestController {
    @Autowired
    private SavedService savedService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/listSavedPost")
    public ResponseEntity<List<SavedDTO>> listSavedPost(@RequestParam Long idUser) {

        List<Saved> savedList = savedService.findAllSavedPost(idUser);

        List<SavedDTO> savedDTOS = new ArrayList<>();
        SavedDTO savedDTO = new SavedDTO();
        PostDTO postDTO = new PostDTO();
        UserDTO userDTO = new UserDTO();

        for (int i = 0; i < savedList.size(); i++) {
            userDTO = modelMapper.map(savedList.get(i).getPost2().getUser(), UserDTO.class);
            postDTO = modelMapper.map(savedList.get(i).getPost2(), PostDTO.class);
            savedDTO = modelMapper.map(savedList.get(i), SavedDTO.class);
            postDTO.setUserDTO(userDTO);
            savedDTO.setPostDTO(postDTO);
            savedDTOS.add(savedDTO);
        }
        return new ResponseEntity<>(savedDTOS, HttpStatus.OK);
    }

    // Lưu trữ
    @GetMapping("/savePost")
    public ResponseEntity<String> savePost(@RequestParam Long idPost,
                                           @RequestParam Long idUser) {
        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Saved> savedIterable = savedService.findAll();
        List<Saved> savedList = (List<Saved>) savedIterable;
        for (int i = 0; i < savedList.size(); i++) {
            if (savedList.get(i).getIdUser().equals(userOptional.get().getId())
                    && savedList.get(i).getPost2().getId().equals(postOptional.get().getId())) {
                if (savedList.get(i).getStatus().equals(Constants.statusSaved1)) {
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                } else if (savedList.get(i).getStatus().equals(Constants.statusSaved2)) {
                    savedList.get(i).setStatus(Constants.statusSaved1);
                    savedService.save(savedList.get(i));
                    return new ResponseEntity<>(savedList.get(i).getStatus(), HttpStatus.OK);
                }
            }
        }
        Saved saved = new Saved();
        saved.setSaveDate(new Date());
        saved.setStatus(Constants.statusSaved1);
        saved.setIdUser(idUser);
        saved.setPost2(postOptional.get());
        savedService.save(saved);
        return new ResponseEntity<>(saved.getStatus(), HttpStatus.OK);
    }

    // Xóa lưu trữ
    @GetMapping("/removeSavePost")
    public ResponseEntity<String> removeSavePost(@RequestParam Long idPost, @RequestParam Long idSaved) {

        Optional<Post2> postOptional = postService.findById(idPost);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Saved> savedOptional = savedService.findById(idSaved);
        if (!savedOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (savedOptional.get().getPost2().getId().equals(postOptional.get().getId())) {
            savedOptional.get().setStatus(Constants.statusSaved2);
            savedService.save(savedOptional.get());
            return new ResponseEntity<>(savedOptional.get().getStatus(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_EXTENDED);
    }
}
