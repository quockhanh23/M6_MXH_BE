package com.example.final_case_social_web.service;

import com.example.final_case_social_web.model.ShortNews;

public interface ShortNewsService extends GeneralService<ShortNews> {
    void delete(ShortNews entity);

    void createShortNews(ShortNews shortNews);
}
