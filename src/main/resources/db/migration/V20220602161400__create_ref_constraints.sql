--------------------------------------------------------
--  Ref Constraints for Table CART
--------------------------------------------------------

alter table if exists cart
    add constraint cart_user_id_fk foreign key (user_id)
        references users;

--------------------------------------------------------
--  Ref Constraints for Table CART_PRODUCTS
--------------------------------------------------------

alter table if exists cart_products
    add constraint cart_prod_prod_id_fk foreign key (products_id)
        references products;

alter table if exists cart_products
    add constraint cart_prod_cart_id_fk foreign key (cart_id)
        references cart;