package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Category;
import com.colvir.shop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
public class CategoryRepositoryPostgresImpl implements CategoryRepository {
    // Реализация с использованием Hibernate

    // Для Hibernate
    private final SessionFactory sessionFactory;

    public Category save(Category category) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(category);

        return category;
    }

    public Category getByCode(String categoryCode) {

        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("select c from Category c where c.code = :categoryCode", Category.class);
        query.setParameter("categoryCode", categoryCode);
        Category category = query.getResultList().stream()
                .findFirst().orElse(null);

        return category;
    }

    public Category update(Category categoryForUpdate) {

        Session session = sessionFactory.getCurrentSession();
        Category persistentCategory = session.get(Category.class, categoryForUpdate.getId());

        persistentCategory.setName(categoryForUpdate.getName());
        persistentCategory.setCatalogId(categoryForUpdate.getCatalogId());

        return persistentCategory;
    }

    public void deleteByCode(String categoryCode) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Category where code = :categoryCode");
        query.setParameter("categoryCode", categoryCode);
        query.executeUpdate();
    }

    public Set<Category> getCategories() {

        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("select c from Category c", Category.class);
        Set<Category> categories = query.getResultList().stream()
                .map(category -> new Category(category.getId(), category.getCode(), category.getName(), category.getCatalogId()))
                .collect(Collectors.toSet());

        return categories;
    }
}
