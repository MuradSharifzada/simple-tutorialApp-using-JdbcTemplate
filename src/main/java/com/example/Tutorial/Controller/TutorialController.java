package com.example.Tutorial.Controller;

import com.example.Tutorial.Model.Tutorial;
import com.example.Tutorial.Service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorialController {
    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping("/tutorials")
    public List<Tutorial> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = tutorialService.findAll(title);
        return tutorials;
    }

    @GetMapping("/tutorials/{id}")
    public Tutorial getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorials = tutorialService.findById(id);
        return tutorials;
    }

    @PostMapping("/tutorials")
    public Tutorial createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial createdTutorial = tutorialService.createTutorial(tutorial);
        return createdTutorial;
    }

    @PutMapping("/tutorials/{id}")
    public Tutorial updateTutorial(@PathVariable("id") Long id, @RequestBody Tutorial tutorial) {
        Tutorial updatedTutorial = tutorialService.updateTutorial(id, tutorial);
        return updatedTutorial;
    }

    @DeleteMapping("/tutorials/{id}")
    public boolean deleteTutorial(@PathVariable("id") Long id) {
        boolean deleted = tutorialService.deleteById(id);
        return deleted;
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<String> deleteAllTutorials() {
        int numDeleted = tutorialService.deleteAll();
        if (numDeleted > 0) {
            return new ResponseEntity<>("All tutorials deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/tutorials/published/{isPublished}")
    public List<Tutorial> findByPublished(@PathVariable("isPublished") boolean published) {
        List<Tutorial> tutorials = tutorialService.findByPublished(published);
        return tutorials;

    }

}
