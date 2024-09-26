package com.example.Tutorial.Repository;

import com.example.Tutorial.Model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class JdbcTutorialRepositoryImpl implements TutorialRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
  public JdbcTutorialRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public int save(Tutorial tutorial) {
        return jdbcTemplate.update("INSERT  into tutorials  (title,description,published) VALUES (?,?,?)",
                new Object[]{tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()});
    }

    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update("UPDATE tutorials SET title=?,description=?,published=? WHERE id=?",
                new Object[]{tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId()});
    }

    @Override
    public Tutorial findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM tutorials WHERE id = ?",
                    BeanPropertyRowMapper.newInstance(Tutorial.class),
                    id
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println("No tutorial found with id: " + id);
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM tutorials WHERE id=?", id);

    }

    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.
                query("SELECT * FROM tutorials", BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return jdbcTemplate.
                query("SELECT * FROM tutorials WHERE published=?", BeanPropertyRowMapper.newInstance(Tutorial.class), published);
    }

    @Override
    public List<Tutorial> finByTitleContaining(String title) {

        String query = "SELECT * FROM tutorials WHERE title LIKE'%" + title + "%'";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE  FROM tutorials");
    }
}
