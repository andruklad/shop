package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Product;
import com.colvir.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
public class ProductRepositoryPostgresImpl implements ProductRepository {
    // Реализация с использованием Hibernate

    // Для Hibernate
    private final SessionFactory sessionFactory;

    public Product save(Product product) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(product);

        return product;
    }

    public Product getByArticle(String article) {

        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p where p.article = :article", Product.class);
        query.setParameter("article", article);
        Product product = query.getResultList().stream()
                .findFirst().orElse(null);

        return product;
    }

    public Product update(Product productForUpdate) {

        Session session = sessionFactory.getCurrentSession();
        Product persistentProduct = session.get(Product.class, productForUpdate.getId());

        persistentProduct.setName(productForUpdate.getName());
        persistentProduct.setPrice(productForUpdate.getPrice());
        persistentProduct.setCategoryId(productForUpdate.getCategoryId());

        return persistentProduct;
    }

    public void deleteByArticle(String article) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Product where article = :article");
        query.setParameter("article", article);
        query.executeUpdate();
    }

    public Set<Product> getProducts() {

        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p", Product.class);
        Set<Product> products = query.getResultList().stream()
                .map(product -> new Product(product.getId(), product.getArticle(), product.getName(), product.getPrice(), product.getCategoryId()))
                .collect(Collectors.toSet());

        return products;
    }
}
