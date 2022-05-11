CREATE TABLE IF NOT EXISTS public.products
(
    product_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_description character varying(255) COLLATE pg_catalog."default",
    product_image bytea,
    product_name character varying(255) COLLATE pg_catalog."default",
    product_price double precision,
    product_rate double precision,
    CONSTRAINT products_pkey PRIMARY KEY (product_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;
