package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Category;
import com.colvir.shop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.Set;

@Getter
@Transactional
@RequiredArgsConstructor
public class CategoryRepositoryPostgresImpl implements CategoryRepository {
    // Реализация с использованием Hibernate

    // Для Hibernate
    private final SessionFactory sessionFactory;

    private final Set<Category> categories = new HashSet<>();

    public Category save(Category category) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(category);

        return category;
    }

    public Category getByCode(String categoryCode) {

        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("select c from Category c where c.code = :categoryCode", Category.class);
        query.setParameter("categoryCode", categoryCode);
        Category category = query.getSingleResult();

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
}
