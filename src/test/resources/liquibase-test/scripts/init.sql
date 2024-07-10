-- liquibase formatted sql

-- changeset akladkevich:1
create table catalogs
(
    id   integer not null
        constraint catalogs_pk_id
            primary key,
    code text
        constraint catalogs_pk_code
            unique,
    name text
);

comment on table catalogs is 'Catalogs';

comment on column catalogs.id is 'Identificator';

comment on column catalogs.code is 'Catalog code';

comment on column catalogs.name is 'Catalog name';

-- alter table catalogs
--     owner to postgres;


create sequence sequence_catalog_id
    as integer;

-- alter sequence sequence_catalog_id owner to postgres;


create table categories
(
    id         integer not null
        constraint categories_pk_id
            primary key,
    code       text
        constraint categories_pk_code
            unique,
    name       text,
    catalog_id integer
        constraint categories_catalogs_id_fk
            references catalogs
);

comment on table categories is 'Categories';

comment on column categories.id is 'Identificator';

comment on column categories.code is 'Category code';

comment on column categories.name is 'Category name';

comment on column categories.catalog_id is 'Catalog identificator';

-- alter table categories
--     owner to postgres;


create sequence sequence_category_id
    as integer;

-- alter sequence sequence_category_id owner to postgres;


create table products
(
    id          integer not null
        constraint products_pk
            primary key,
    article     text,
    name        text,
    price       double precision,
    category_id integer
        constraint products_categories_id_fk
            references categories
);

comment on table products is 'Products';

comment on column products.id is 'Identificator';

comment on column products.article is 'Article';

comment on column products.name is 'Name';

comment on column products.price is 'Price';

comment on column products.category_id is 'Category identificator';

-- alter table products
--     owner to postgres;


create sequence sequence_product_id
    as integer;

-- alter sequence sequence_product_id owner to postgres;