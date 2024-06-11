package com.colvir.shop.repository.impl;

import com.colvir.shop.expception.DatabaseException;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.repository.CatalogRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class CatalogRepositoryPostgresImpl implements CatalogRepository {
    // TODO. Пока это копия CatalogRepositoryMemoryImpl. Необходимо адаптировать на работу с БД

    private final JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<Catalog> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Catalog.class);

    private Long generateId() {

        String statementString = "SELECT nextval('sequence_catalog_id')";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(statementString);
        sqlRowSet.next();
        return sqlRowSet.getLong(1);
    }

    public Catalog save(Catalog catalog) {

        if (catalog.getId() == null) catalog.setId(generateId());
        String preparedStatementString = "INSERT INTO catalogs (id, code, name) VALUES (?, ?, ?);";
        try {
            jdbcTemplate.update(preparedStatementString, catalog.getId(), catalog.getCode(), catalog.getName());
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
        return catalog;
    }

    public Catalog getByCode(String catalogCode) {

        String preparedStatementString = "SELECT * FROM catalogs WHERE code = ?";
        Catalog catalog;

        try {
            catalog = jdbcTemplate.query(preparedStatementString, beanPropertyRowMapper, new Object[]{catalogCode}).stream()
                    .findFirst().orElse(null);
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
        return catalog;
    }

    public Catalog update(Catalog catalogForUpdate) {

        String preparedStatementString = "UPDATE catalogs SET code = ?, name = ? WHERE id = ?";

        try {
            jdbcTemplate.update(preparedStatementString, catalogForUpdate.getCode(), catalogForUpdate.getName(), catalogForUpdate.getId());
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
        return catalogForUpdate;
    }

    public void deleteByCode(String catalogCode) {

        String preparedStatementString = "DELETE FROM catalogs WHERE code = ?";

        try {
            jdbcTemplate.update(preparedStatementString, catalogCode);
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Set<Catalog> getCatalogs() {

        String preparedStatementString = "SELECT * FROM catalogs";
        Set<Catalog> catalogs;

        try {
            catalogs = new HashSet<>(jdbcTemplate.query(preparedStatementString, beanPropertyRowMapper));
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
        return catalogs;
    }
}
