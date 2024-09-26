package com.example.Tutorial.Service;

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
        log.info("Searching for tutorials with title: {}", title == null ? "All titles" : title);
        try {
            if (title == null || title.isEmpty()) {
                return tutorialRepository.findAll();
            } else {
                return tutorialRepository.finByTitleContaining(title);
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving tutorials: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Tutorial findById(long id) {
        log.info("Searching tutorial by id: {}", id);
        try {
            return tutorialRepository.findById(id);
        } catch (Exception e) {
            log.error("Error occurred while finding tutorial by id: {}", id, e);
            return null;
        }
    }

    @Override
    public Tutorial createTutorial(Tutorial tutorial) {
        log.info("Creating new tutorial with title: {}, description: {}, published: {}",
                tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
        try {
            int result = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            if (result > 0) {
                log.info("New tutorial added successfully with title: {}", tutorial.getTitle());
                return tutorial;
            } else {
                log.warn("Tutorial creation failed for title: {}", tutorial.getTitle());
                return null;
            }
        } catch (Exception e) {
            log.error("Error occurred while adding new tutorial with title: {}", tutorial.getTitle(), e);
            return null;
        }
    }

    @Override
    public Tutorial updateTutorial(long id, Tutorial tutorial) {
        log.info("Starting update process for tutorial with id: {}", id);
        try {
            Tutorial existingTutorial = tutorialRepository.findById(id);
            if (existingTutorial != null) {
                log.debug("Updating tutorial with id: {}", id);
                existingTutorial.setTitle(tutorial.getTitle());
                existingTutorial.setDescription(tutorial.getDescription());
                existingTutorial.setPublished(tutorial.isPublished());
                tutorialRepository.update(existingTutorial);
                log.info("Successfully updated tutorial with id: {}", id);
                return existingTutorial;
            } else {
                log.warn("Tutorial with id: {} not found, update operation aborted", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error occurred while updating tutorial with id: {}", id, e);
            return null;
        }
    }

    @Override
    public boolean deleteById(long id) {
        log.info("Searching for tutorial with id: {} to delete", id);
        try {
            int result = tutorialRepository.deleteById(id);
            if (result > 0) {
                log.info("Successfully deleted tutorial with id: {}", id);
                return true;
            } else {
                log.warn("No tutorial found with id: {}, deletion failed", id);
                return false;
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting tutorial with id: {}", id, e);
            return false;
        }
    }

    @Override
    public int deleteAll() {
        log.info("Deleting all tutorials");
        try {
            int deletedCount = tutorialRepository.deleteAll();
            log.info("Successfully deleted {} tutorials", deletedCount);
            return deletedCount;
        } catch (Exception e) {
            log.error("Error occurred while deleting all tutorials", e);
            return 0;
        }
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        log.info("Searching for tutorials by published status: {}", published);
        try {
            return tutorialRepository.findByPublished(published);
        } catch (Exception e) {
            log.error("Error occurred while searching tutorials by published status: {}", published, e);
            return null;
        }
    }
}
