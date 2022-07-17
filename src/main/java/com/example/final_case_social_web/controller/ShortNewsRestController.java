package com.example.final_case_social_web.controller;

import com.example.final_case_social_web.model.ShortNews;
import com.example.final_case_social_web.model.User;
import com.example.final_case_social_web.service.ShortNewsService;
import com.example.final_case_social_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/api/news")
@Slf4j
public class ShortNewsRestController {
    @Autowired
    private ShortNewsService shortNewsService;

    @Autowired
    private UserService userService;

    // Lưu ngày mới
    @GetMapping("/newDay")
    public ResponseEntity<Iterable<ShortNews>> newDay() {
        Iterable<ShortNews> shortNews = shortNewsService.findAll();
        List<ShortNews> shortNewsList;
        shortNewsList = (List<ShortNews>) shortNews;
        for (int i = 0; i < shortNewsList.size(); i++) {
            shortNewsList.get(i).setToDay(new Date());
            shortNewsService.save(shortNewsList.get(i));
        }
        return new ResponseEntity<>(shortNews, HttpStatus.OK);
    }

    // Kiểm tra hạn sử dụng
    @GetMapping("/allShortNews")
    public ResponseEntity<Iterable<ShortNews>> allShortNews() {
        Iterable<ShortNews> shortNews = shortNewsService.findAll();
        List<ShortNews> shortNewsList;
        shortNewsList = (List<ShortNews>) shortNews;
        for (int i = 0; i < shortNewsList.size(); i++) {

            int today = Integer.parseInt(shortNewsList.get(i).getToDay().toString().substring(8, 10));
            int createDay = Integer.parseInt(shortNewsList.get(i).getCreateAt().toString().substring(8, 10));

            int monthToday = Integer.parseInt(shortNewsList.get(i).getToDay().toString().substring(5, 7));
            int monthCreate = Integer.parseInt(shortNewsList.get(i).getCreateAt().toString().substring(5, 7));

            int yearToday = Integer.parseInt(shortNewsList.get(i).getToDay().toString().substring(0, 4));
            int yearCreate = Integer.parseInt(shortNewsList.get(i).getCreateAt().toString().substring(0, 4));

            if (today < createDay) {
                int dayF = 30;
                if (monthToday > monthCreate || (monthToday < monthCreate && yearCreate < yearToday)) {
                    if (monthToday == 1 || monthToday == 3 || monthToday == 5 ||
                            monthToday == 7 || monthToday == 8 || monthToday == 10 || monthToday == 12) {
                        today = dayF + 1;
                    }
                    if (monthToday == 4 || monthToday == 6 || monthToday == 9 || monthToday == 11) {
                        today = dayF;
                    }
                    if (monthToday == 2) {
                        if (createDay == 29) {
                            today = dayF + 2;
                        }
                        if (createDay == 28) {
                            today = dayF + 3;
                        }
                    }
                }
            }

            int dayRemaining = today - createDay;
            if (dayRemaining < 0) {
                dayRemaining = dayRemaining / -1;
            }
            shortNewsList.get(i).setRemaining(shortNewsList.get(i).getExpired() - dayRemaining);
            shortNewsService.save(shortNewsList.get(i));
        }
        return new ResponseEntity<>(shortNews, HttpStatus.OK);
    }

    // Tạo tin
    @PostMapping("/createShortNews")
    public ResponseEntity<ShortNews> createShortNews(@RequestBody ShortNews shortNews,
                                                     @RequestParam Long idUser) {

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        shortNews.setCreateAt(new Date());
        shortNews.setToDay(new Date());
        shortNews.setExpired(3);
        shortNews.setRemaining(3);
        if (shortNews.getImage() == null) {
            shortNews.setImage("https://loanthehongnhan.vn/hinh-nen-don-gian/imager_28148.jpg");
        }
        shortNewsService.save(shortNews);
        return new ResponseEntity<>(shortNews, HttpStatus.OK);
    }

    // Xóa tin nhưng vẫn còn trong thùng rác
    @DeleteMapping("/deleteShortNews")
    public ResponseEntity<ShortNews> deleteShortNews(@RequestParam Long idSortNew,
                                                     @RequestParam Long idUser) {

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<ShortNews> shortNewsOptional = shortNewsService.findById(idSortNew);
        if (!shortNewsOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        shortNewsOptional.get().setRemaining(-1);
        shortNewsService.save(shortNewsOptional.get());
        return new ResponseEntity<>(shortNewsOptional.get(), HttpStatus.OK);
    }

    // Xóa hẳn tin
    @DeleteMapping("/deleteShortNews2")
    public ResponseEntity<ShortNews> deleteShortNews2(@RequestParam Long idSortNew,
                                                      @RequestParam Long idUser) {

        Optional<User> userOptional = userService.findById(idUser);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<ShortNews> shortNewsOptional = shortNewsService.findById(idSortNew);
        if (!shortNewsOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        shortNewsService.delete(shortNewsOptional.get());
        return new ResponseEntity<>(shortNewsOptional.get(), HttpStatus.OK);
    }
}
