DELETE FROM cart_products WHERE cart_id = 2 AND products_id = 13;

INSERT INTO cart_products (cart_id, products_id) VALUES (2, 14);

INSERT INTO cart_products (cart_id, products_id) VALUES (2, 15);

INSERT INTO cart_products (cart_id, products_id) VALUES (2, 16);

UPDATE products
SET image = 'https://www.freshdirect.com/media/images/product/bakery_2/bak_pid_4651375_j.jpg?lastModify=2021-08-02'
WHERE id = 7;

UPDATE products
SET image = 'https://media.istockphoto.com/photos/two-delicious-angus-beef-burgers-isolated-on-a-white-background-picture-id171352175?k=20&m=171352175&s=612x612&w=0&h=bqHhmlNzOYLWGOBvH3Obve9__wdwtUBJ93q6QagOuOg='
WHERE id = 17;

UPDATE products
SET image = 'https://media.istockphoto.com/photos/raw-ground-beef-picture-id171292868?k=20&m=171292868&s=612x612&w=0&h=0jTMliaIZ4U_qL67oNGYiGY7mLNMXwjzOsFWNzEPDCU='
WHERE id = 18;

UPDATE products
SET image = 'https://cdn.perishablenews.com/2019/02/m11-2.jpg'
WHERE id = 24;

UPDATE products
SET image = 'https://media.istockphoto.com/photos/buffalo-ribeye-picture-id172636895?k=20&m=172636895&s=612x612&w=0&h=Re5gzxJHycBfflOTC36E0O2EGXxwTcfuNI8HYVP7d2s='
WHERE id = 25;

UPDATE products
SET image = 'https://www.freshdirect.com/media/images/product/fruit_3/fru_pid_2210794_j.jpg?lastModify=2019-05-14'
WHERE id = 52;

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 53,
        'These classic slender breads have a crisp, chewy crust and a sweet, tender interior. Just bake briefly, and they will emerge from your oven warm and fragrant. These baguettes are perfect for bruschetta, sandwiches, or as a table bread. They''re also just right for making meatball heroes at home — split lengthwise, fill with meatballs and marinara, top with mozzarella, and broil until the cheese melts.',
        'https://www.freshdirect.com/media/images/product/bakery_2/bak_brd_itl_pln2pk_j.jpg?lastModify=2020-08-04',
        'Plain Demi-Baguette', 5.99, 4.8, 'PACKABLE', 1, 112, 'BAKERY');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 54,
        'We''ve been told that this is the ""best brioche ever."" And we''d have to agree. It''s made using farm-fresh eggs, real butter, and plenty of time so it has that truly authentic, luxuriant texture. One taste and you''ll immediately know what sets Brooklyn Mills apart.',
        'https://www.freshdirect.com/media/images/product/bakery_1/bak_pid_4651290_j.jpg?lastModify=2020-02-10',
        'Brioche Loaf', 8.99, 4.2, 'PACKABLE', 1, 54, 'BAKERY');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 55,
        'Our Organic French Sourdough, or Pain au Levain, is one of our original breads. It is a naturally leavened bread that develops deep sourdough flavor from a heavy dose of liquid levain and overnight fermentation.',
        'https://www.freshdirect.com/media/images/product/bakery_2/bak_pid_4651350_j.jpg?lastModify=2020-10-09',
        'French Sourdough', 6.99, 4.0, 'PACKABLE', 1, 106, 'BAKERY');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 56,
        'The all-time favorite cabbage. It sets the standard. Firmly packed, with smooth, uniformly green skin. The crisp and fleshy leaves are loaded with tart tanginess and a surprisingly pleasing aroma. Green cabbage is loaded with vitamins and antioxidants.',
        'https://www.freshdirect.com/media/images/product/veg_3/cab_green_j.jpg?lastModify=2021-03-26',
        'Green Cabbage', 1.29, 4.9, 'WEIGHABLE', 1, 78, 'VEGETABLES');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 57,
        'The purple-robed prince among cabbages. It has an intense musky flavor and sweet snappiness. Red cabbage can be stewed, braised, or even curried. It''s crispy when you scoop the raw leaves into dip. Crunchy when you stir-fry small wedges.',
        'https://www.freshdirect.com/media/images/product/veg_3/orgveg_cbbg_red_j.jpg?lastModify=2021-03-26',
        'Organic Red Cabbage', 1.49, 5.0, 'WEIGHABLE', 1, 42, 'VEGETABLES');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 58,
        'There are very few foods as sweet and pretty as bicolor corn. These ears crackle with fresh-picked crispness. Boiled or grilled, on the cob or off, there is no wrong way to prepare this corn. Add butter or salt if you must, but we like it plain. The two-color kernels make lovely salads, succotash, muffins, and chowders.',
        'https://www.freshdirect.com/media/images/product/veg_3/corn_bi_j.jpg?lastModify=2021-03-26',
        'Bicolor Corn', 7.99, 4.1, 'WEIGHABLE', 1, 21, 'VEGETABLES');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 59,
        'Firm but velvety lemon-lime-colored flesh and a mild, meaty flavor. Good for slicing or mashing up for guacamole. It makes a beautiful and delicious garnish.',
        'https://www.freshdirect.com/media/images/product/veg_2/avc_fla_j.jpg?lastModify=2017-11-24',
        'Avocado', 2.49, 3.8, 'WEIGHABLE', 1, 13, 'FRUITS');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 60,
        'Velvet skin, juicy flesh, and a subtle, deeply sweet taste with almond overtones. The skin is tender and tart, with just a little bit of downy fuzz. This fruit is all about flavor and juice. Apricots have a slightly firm texture that holds up well when cooked',
        'https://www.freshdirect.com/media/images/product/fruit_3/apricot_orng_j.jpg?lastModify=2021-03-26',
        'Apricots', 1.29, 4.4, 'WEIGHABLE', 1, 67, 'FRUITS');

INSERT INTO products (id, description, image, name, price, rate, type, size, count_of_sold_products, category) VALUES
        ( 61,
        'Every variety of plumcot — a plum-apricot hybrid — is as diverse in color as it is in flavor. The Amigo, a variety with bright to dark red skin with flaming red/yellow flesh, has rosy plum flavors with a hint of berry.',
        'https://www.freshdirect.com/media/images/product/fruit_2/fru_pid_2210297_j.jpg?lastModify=2017-01-10',
        'Amigo Plumcot', 4.99, 3.9, 'WEIGHABLE', 1, 20, 'FRUITS');
