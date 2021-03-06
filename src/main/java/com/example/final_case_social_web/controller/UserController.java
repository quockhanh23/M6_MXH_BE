package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.common.Constants;
import com.example.final_case_social_web.common.LogMessage;
import com.example.final_case_social_web.dto.UserDTO;
import com.example.final_case_social_web.model.*;
import com.example.final_case_social_web.repository.LastUserLoginRepository;
import com.example.final_case_social_web.service.RoleService;
import com.example.final_case_social_web.service.UserService;
import com.example.final_case_social_web.service.VerificationTokenService;
import com.example.final_case_social_web.service.impl.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LastUserLoginRepository lastUserLoginRepository;
    @Autowired
    private ModelMapper modelMapper;

    // L???ch s??? ????ng nh???p
    @GetMapping("/historyLogin")
    public ResponseEntity<List<LastUserLogin>> historyLogin() {
        List<LastUserLogin> userLogins = lastUserLoginRepository.historyLogin();
        return new ResponseEntity<>(userLogins, HttpStatus.OK);
    }

    // G???i ?? k???t b???n
    @GetMapping("/allUser")
    public ResponseEntity<Iterable<User>> listUserSuggest() {
        Iterable<User> users = userService.findAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (!userService.isCorrectConfirmPassword(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles() != null) {
            Role role = roleService.findByName("ROLE_ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            Role role1 = roleService.findByName("ROLE_USER");
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        userService.createDefault(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        user.setStatus(Constants.statusUser3);
        userService.save(user);
        VerificationToken token = new VerificationToken(user);
        token.setExpiryDate(10);
        verificationTokenService.save(token);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/matchPassword")
    public ResponseEntity<User> matches(@RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(user.getId());
        if (passwordEncoder.matches(user.getPassword(), userOptional.get().getPassword())) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        final double startTime = System.currentTimeMillis();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Iterable<User> list = userService.findAll();
        List<User> userList = new ArrayList<>();
        userList = (List<User>) list;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getStatus().equals(Constants.statusUser2)) {
                if (userList.get(i).getUsername().equals(user.getUsername())
                        && passwordEncoder.matches(user.getPassword(), userList.get(i).getPassword())) {
                    log.info(LogMessage.logMessageStrikeThrough);
                    log.info(LogMessage.logMessageLoginUser);
                    log.info(LogMessage.logMessageStrikeThrough);
                    return new ResponseEntity<>(HttpStatus.LOCKED);
                }
            }
        }

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        Optional<LastUserLogin> lastUserLogin = lastUserLoginRepository.findAllByIdUser(currentUser.getId());
        if (lastUserLogin.isPresent()) {
            lastUserLogin.get().setIdUser(currentUser.getId());
            lastUserLogin.get().setLoginTime(new Date());
            lastUserLogin.get().setUserName(currentUser.getUsername());
            lastUserLogin.get().setAvatar(currentUser.getAvatar());
            lastUserLogin.get().setFullName(currentUser.getFullName());
            lastUserLoginRepository.save(lastUserLogin.get());
        } else {
            LastUserLogin lastUserLogin1 = new LastUserLogin();
            lastUserLogin1.setIdUser(currentUser.getId());
            lastUserLogin1.setUserName(currentUser.getUsername());
            lastUserLogin1.setLoginTime(new Date());
            lastUserLogin1.setAvatar(currentUser.getAvatar());
            lastUserLogin1.setFullName(currentUser.getFullName());
            lastUserLoginRepository.save(lastUserLogin1);
        }
        final double elapsedTimeMillis = System.currentTimeMillis();
        log.info(LogMessage.logMessageStrikeThrough);
        System.out.println("Total execution time: " + (elapsedTimeMillis - startTime));
        System.out.println("Total execution time(s): " + (elapsedTimeMillis - startTime) / 1000);
        log.info(LogMessage.logMessageStrikeThrough);
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = new UserDTO();
        userDTO = modelMapper.map(userOptional.get(), UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        user.setUsername(userOptional.get().getUsername());
        user.setEnabled(userOptional.get().isEnabled());
        if (!user.getPassword().equals(userOptional.get().getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userOptional.get().getPassword());
        }
        user.setRoles(userOptional.get().getRoles());
        if (!user.getConfirmPassword().equals(userOptional.get().getConfirmPassword())) {
            user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        } else {
            user.setConfirmPassword(userOptional.get().getConfirmPassword());
        }
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/changeStatusUserActive/{id}")
    public ResponseEntity<User> changeStatusUserActive(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (userOptional.get().getStatus().equals(Constants.statusUser3)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (userOptional.get().getId().equals(id)) {
            userOptional.get().setStatus(Constants.statusUser3);
            userService.save(userOptional.get());
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/changeStatusUserLock/{id}")
    public ResponseEntity<User> changeStatusUserLock(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (userOptional.get().getStatus().equals(Constants.statusUser1)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (userOptional.get().getId().equals(id)) {
            userOptional.get().setStatus(Constants.statusUser1);
            userService.save(userOptional.get());
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NOT_MODIFIED);
    }
}
