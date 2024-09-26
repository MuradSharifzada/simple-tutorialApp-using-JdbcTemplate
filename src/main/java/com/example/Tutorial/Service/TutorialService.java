package com.example.Tutorial.Service;

import com.example.Tutorial.Model.Tutorial;

import java.util.List;

public interface TutorialService {

        List<Tutorial> findAll(String title);
        Tutorial findById(long id);
        Tutorial createTutorial(Tutorial tutorial);
        Tutorial updateTutorial(long id, Tutorial tutorial);
        boolean deleteById(long id);
        int deleteAll();
        List<Tutorial> findByPublished(boolean published);

}
