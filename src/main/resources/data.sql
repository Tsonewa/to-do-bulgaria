INSERT INTO  towns (`name`, region)
values ('София', 'Област София'),
       ('Луковит', 'Област Софийска'),
       ('Трън', 'Област Софийска'),
       ('Ботевград', 'Област Софийска'),
       ('Пирдоп', 'Област Софийска'),
       ('Приморско', 'Област Бургас'),
       ('Созопол', 'Област Бургас'),
       ('Каварна', 'Област Варна'),
       ('Габрово', 'Област Плевен');

INSERT INTO  details (equipment, festivals, foto_tips)
values ('Екипировка за планина', 'Фестивал на розата', 'Парк-музей „Врана”'),
       ('Екипировка за море', 'Фестивал на водата', 'Морска градина'),
       ('Екипировка за дъжд', 'Фестивал на лютата чушка', 'Анфитеатър София'),
       ('Екипировка за планина', 'Фестивал "Морето и аз"', 'Дворец Равадиново'),
       ('Екипировка за планина', 'Фестивал "Морето и аз"', 'Дворец Равадиново'),
       ('Екипировка за планина', 'Фестивал "Морето и аз"', 'Дворец Равадиново'),
       ('Екипировка за планина', 'Фестивал "Морето и аз"', 'Дворец Равадиново'),
       ('Екипировка за вятър', 'Фестивал на народната носия', 'Крайморска алея');

INSERT INTO  attractions (description, location, `name`)
values ('Парк Врана е създаден под влиянието на европейските пейзажни паркове.В продължение на повече от 40 години създателите на парка успяват да изградят един великолепен парков ансамбъл. За целта като „строителен материал“ използват декоративната растителност.Впечатляващо е голямото разнообразие от 821 дървесни, храстови и тревисти вида от 118 семейства и 435 рода на територия по-малка от 100 ха.', 'Boulevard "Tsarigradsko shose" 381, 1138 Sofia', 'Парк-музей „Врана”'),
       ('Тя е сред символите на града. Обявена е за произведение на парковото изкуство. На север Морската градина граничи с парка на държавно-правителствената резиденция Евксиноград.[1] Тези два парка имат сравнително еднаква площ от около 820 дка. Разликата между тях е, че Морската градина се посещава от около половин милион души, а паркът на Евксиноград е за ползване само от президентството и правителството и се поддържа много по-добре.', 'Варна 9000, “Осми приморски полк” № 43', 'Морска градина Варна”'),
       ('Тя е сред символите на града. Обявена е за произведение на парковото изкуство. На север Морската градина граничи с парка на държавно-правителствената резиденция Евксиноград.[1] Тези два парка имат сравнително еднаква площ от около 820 дка. Разликата между тях е, че Морската градина се посещава от около половин милион души, а паркът на Евксиноград е за ползване само от президентството и правителството и се поддържа много по-добре.', 'Созопол 9000, “Осми приморски полк” № 43', 'Амфитеатър Созопол”'),
       ('Тя е сред символите на града. Обявена е за произведение на парковото изкуство. На север Морската градина граничи с парка на държавно-правителствената резиденция Евксиноград.[1] Тези два парка имат сравнително еднаква площ от около 820 дка. Разликата между тях е, че Морската градина се посещава от около половин милион души, а паркът на Евксиноград е за ползване само от президентството и правителството и се поддържа много по-добре.', 'София 9000, “Осми приморски полк” № 46', 'Национален Дворец на Културата”'),
       ('Тя е сред символите на града. Обявена е за произведение на парковото изкуство. На север Морската градина граничи с парка на държавно-правителствената резиденция Евксиноград.[1] Тези два парка имат сравнително еднаква площ от около 820 дка. Разликата между тях е, че Морската градина се посещава от около половин милион души, а паркът на Евксиноград е за ползване само от президентството и правителството и се поддържа много по-добре.', 'София 9000, “Осми приморски полк” № 49', 'Руски Паметник”');



INSERT INTO pictures (url)
VALUES ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg'),
       ('https://res.cloudinary.com/dabxnbbrp/image/upload/v1636450930/sample.jpg');


INSERT INTO trips (description, duration, rating, region, category_entity_id, details_id, picture_id, user_id)
VALUES ('2004 UE е част от групата Aи толкова близо до Земята, ще е през 2051 г.', 1, 2, 'София 0бл', 2, 1, 1, 2),
        ('ечен Тракийско-Роо на жизнеда се окаже неблагоприи', 1, 1, 'Созопол', 4, 2, 2, 2),
       ('очниечеезионно финансиране за райони в преход при новата Политика на сближаване през периода 2020 – 2027', 1, 3, 'Бургас 0бл', 1, 3, 3, 2),
       ('з Облез промени Югозападнизаа Политика на сближаване през периода 2020 – 2027', 1, 4, 'Варна 0бл', 2, 4, 4, 2),
       ('нава към Юреход), и въпреки по-високото ниво на жизнен стандарт в столицата, тя ще продъчжи да усво', 1, 3, 'Велико Търново', 2, 5, 5, 2),
       ('Запазвриода 2020– 2027', 1, 5, 'Сломян област', 4, 6, 6, 2),
       ('столицатостта за спз периода 2020 – 2027', 2, 1, 'Централен Балкан', 3, 7, 7, 2),
        ('о финансиране за райони в преход при новата Политика на сближаване през периода 2020 – 2027', 2, 1, 'Централен Балкан', 3, 8, 8, 2);

INSERT INTO  itineraries (breakfast_place, coffee_place, created_on, `day`, dinner_place, hotel, town_id, trip_id)
values ('Mekitsa%Coffee', 'CoffeeShop', '2021-11-11', 1, 'Blue House', 'Grand Hotel Sofia', 1, 1),
       ('Nedelq', 'Starbucks', '2021-11-13', 1, 'Happy Bar and Grill', 'Marinella', 2, 2),
       ('Breakfast Paradise', 'Costa', '2021-11-13', 1, 'Hrima', 'Sheraton', 3, 3),
       ('Eggs House', 'Coffee Place', '2021-11-14', 1, 'Milenium', 'Grand Hotel Plovdiv', 4, 4),
       ('Eggs House', 'Coffee Place', '2021-11-14', 1, 'Milenium', 'Grand Hotel Plovdiv', 5, 5),
       ('Eggs House', 'Coffee Place', '2021-11-14', 1, 'Milenium', 'Grand Hotel Plovdiv', 6, 6),
       ('Eggs House', 'Coffee Place', '2021-11-14', 1, 'Milenium', 'Grand Hotel Plovdiv', 7, 7),
       ('Eggs House', 'Coffee Place', '2021-11-14', 1, 'Milenium', 'Grand Hotel Plovdiv', 8, 8),
       ('Pancakes House', 'Italian Taste', '2021-11-15', 1, 'McDonalds', 'Ramada Resort', 2, 8),
        ('Pancakes House', 'Italian Taste', '2021-11-15', 1, 'McDonalds', 'Ramada Resort', 2,7);

INSERT INTO  itineraries_attractions (itineraries_id, attraction_id)
VALUES (1, 2),
       (2, 3),
       (3, 1),
       (4, 4),
       (5, 5),
       (6, 3),
       (7, 2),
       (8, 5),
       (9, 4),
       (8, 1),
       (7, 2);

