package com.example.Tutorial.Repository;

import com.example.Tutorial.Model.Tutorial;

import java.util.List;

public abstract interface TutorialRepository {
    int save(Tutorial book);

    int update(Tutorial book);

    Tutorial findById(Long id);

    int deleteById(Long id);

    List<Tutorial> findAll();

    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> finByTitleContaining(String title);

    int deleteAll();
}
