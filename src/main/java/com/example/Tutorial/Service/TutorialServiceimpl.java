package com.example.Tutorial.Service;

import com.example.Tutorial.ExceptionHandler.TutorialAlreadyExistException;
import com.example.Tutorial.ExceptionHandler.TutorialNotFoundByGivenID;
import com.example.Tutorial.ExceptionHandler.TutorialNotFoundException;
import com.example.Tutorial.ExceptionHandler.TutorialNotPublishedException;
import com.example.Tutorial.Model.Tutorial;
import com.example.Tutorial.Repository.TutorialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceimpl implements TutorialService {
    private static final Logger log = LoggerFactory.getLogger(TutorialServiceimpl.class);
    private final TutorialRepository tutorialRepository;

    @Autowired
    public TutorialServiceimpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public List<Tutorial> findAll(String title) {
        try {
            log.info("Searching for tutorials with title: {}", title == null ? "All titles" : title);
            List<Tutorial> tutorials = (title == null || title.isEmpty())
                    ? tutorialRepository.findAll()
                    : tutorialRepository.finByTitleContaining(title);
            if (tutorials == null || tutorials.isEmpty()) {
                throw new TutorialNotFoundException("No tutorials found matching the given criteria.");
            }
            return tutorials;
        } catch (Exception e) {
            log.error("Error occurred while searching tutorials with title: {}", title, e);
            throw new TutorialNotFoundException("Unable to retrieve tutorials. Please try again later.");
        }
    }

    @Override
    public Tutorial findById(long id) {
        try {
            log.info("Searching tutorial by id: {}", id);
            Tutorial tutorial = tutorialRepository.findById(id);
            if (tutorial == null) {
                throw new TutorialNotFoundByGivenID("No tutorial found with the provided ID: " + id);
            }
            return tutorial;
        } catch (Exception e) {
            log.error("Error occurred while finding tutorial by id: {}", id, e);
            throw new TutorialNotFoundByGivenID("Unable to find the tutorial with the given ID. Please try again.");
        }
    }

    @Override
    public Tutorial createTutorial(Tutorial tutorial) {
        try {
            log.info("Creating new tutorial with title: {}, description: {}, published: {}",
                    tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
            int result = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
            if (result > 0) {
                log.info("New tutorial added successfully with title: {}", tutorial.getTitle());
                return tutorial;
            } else {
                log.warn("Tutorial creation failed for title: {}", tutorial.getTitle());
                throw new TutorialAlreadyExistException("A tutorial with this title already exists.");
            }
        } catch (Exception e) {
            log.error("Error occurred while creating tutorial: {}", tutorial.getTitle(), e);
            throw new TutorialAlreadyExistException("Unable to create the tutorial. Please check the details and try again.");
        }
    }

    @Override
    public Tutorial updateTutorial(long id, Tutorial tutorial) {
        try {
            log.info("Starting update process for tutorial with id: {}", id);
            Tutorial existingTutorial = tutorialRepository.findById(id);
            if (existingTutorial == null) {
                throw new TutorialNotFoundByGivenID("No tutorial found with the provided ID: " + id);
            }
            log.debug("Updating tutorial with id: {}", id);
            existingTutorial.setTitle(tutorial.getTitle());
            existingTutorial.setDescription(tutorial.getDescription());
            existingTutorial.setPublished(tutorial.isPublished());
            tutorialRepository.update(existingTutorial);
            log.info("Successfully updated tutorial with id: {}", id);
            return existingTutorial;
        } catch (Exception e) {
            log.error("Error occurred while updating tutorial with id: {}", id, e);
            throw new TutorialNotFoundByGivenID("Unable to update the tutorial. Please try again later.");
        }
    }

    @Override
    public boolean deleteById(long id) {
        try {
            log.info("Searching for tutorial with id: {} to delete", id);
            int result = tutorialRepository.deleteById(id);
            if (result > 0) {
                log.info("Successfully deleted tutorial with id: {}", id);
                return true;
            } else {
                log.warn("No tutorial found with id: {}, deletion failed", id);
                throw new TutorialNotFoundByGivenID("No tutorial found with the provided ID: " + id + " to delete.");
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting tutorial with id: {}", id, e);
            throw new TutorialNotFoundByGivenID("Unable to delete the tutorial. Please try again later.");
        }
    }

    @Override
    public int deleteAll() {
        try {
            log.info("Deleting all tutorials");
            int deletedCount = tutorialRepository.deleteAll();
            if (deletedCount == 0) {
                throw new TutorialNotFoundException("No tutorials available to delete.");
            } else if (deletedCount < 0) {
                throw new IllegalStateException("Unexpected error occurred while deleting tutorials.");
            }
            log.info("Successfully deleted {} tutorials", deletedCount);
            return deletedCount;
        } catch (Exception e) {
            log.error("Error occurred while deleting all tutorials", e);
            throw new TutorialNotFoundException("Unable to delete tutorials. Please try again later.");
        }
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        try {
            log.info("Searching for tutorials by published status: {}", published);
            List<Tutorial> tutorials = tutorialRepository.findByPublished(published);
            if (tutorials == null || tutorials.isEmpty()) {
                throw new TutorialNotPublishedException("No tutorials found with the published status: " + (published ? "published" : "not published"));
            }
            return tutorials;
        } catch (Exception e) {
            log.error("Error occurred while searching tutorials by published status: {}", published, e);
            throw new TutorialNotPublishedException("Unable to retrieve tutorials by published status. Please try again later.");
        }
    }
}
