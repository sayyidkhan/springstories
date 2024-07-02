package com.main.springstories.service;

import com.main.springstories.models.AuthorEntity;
import com.main.springstories.repository.AuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    void saveAuthors(List<AuthorEntity> authorEntities) {
        List<AuthorEntity> authorEntityList = authorRepository.findAll();
        Set<String> authorNames = authorEntityList.stream()
                .map(AuthorEntity::getName) // Assuming AuthorEntity has a getName() method
                .collect(Collectors.toCollection(HashSet::new));
        for (AuthorEntity entity : authorEntities) {
            // make all values uppercase
            entity.setName(entity.getName().toUpperCase());
            // if author name is in the database prevent any update of records
            if (authorNames.contains(entity.getName())) continue;
            authorRepository.save(entity);
        }
    }

    @Transactional
    public List<AuthorEntity> findByNameInAuthorsTables(String names) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorEntity> query = cb.createQuery(AuthorEntity.class);
        Root<AuthorEntity> root = query.from(AuthorEntity.class);

        List<String> namesList = new ArrayList<>();
        // if there is more than 1 names
        if (names.contains(",")) {
            List<String> nameList = Arrays.asList(names.split(","));
            List<String> processedNames = nameList.stream()
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .toList();
            namesList.addAll(processedNames);
        }
        // one name only
        else {
            namesList.add(names.trim().toUpperCase());
        }

        query.select(root).where(root.get("name").in(namesList));
        return entityManager.createQuery(query).getResultList();
    }

}
