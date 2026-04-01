insert into users (name, surname, email, password, role) values
('Mario', 'Rossi', 'user@a.it', '1234', 'USER'),
('Luigi', 'Verdi', 'u2@a.it', '1235', 'USER'),
('Francesco', 'Bianchi', 'u3@a.it', '1236', 'USER');

insert into products (name, description, price) values
('Fifa 2024', 'Il miglior gioco di calcio', 59.99),
('Call of Duty: Modern Warfare II', 'Il miglior gioco di guerra', 69.99),
('The Legend of Zelda: Tears of the Kingdom', 'Il miglior gioco di avventura', 79.99),
('Minecraft', 'Il miglior gioco di costruzione', 29.99),
('Grand Theft Auto V', 'Il miglior gioco di azione', 49.99),
('Cyberpunk 2077', 'Il miglior gioco di fantascienza', 59.99),
('Red Dead Redemption 2', 'Il miglior gioco di western', 69.99),
('The Witcher 3: Wild Hunt', 'Il miglior gioco di ruolo', 39.99),
('Among Us', 'Il miglior gioco di social deduction', 4.99),
('Hades', 'Il miglior gioco di roguelike', 24.99);

insert into purchases (user_id, product_id, quantity) values
(1, 1, 1),
(1, 2, 1),
(2, 3, 1),
(2, 4, 1),
(3, 5, 1),
(3, 6, 1);

insert into orders (data, user_id, total) values
('2024-06-01 10:00:00', 1, 129.98),
('2024-06-02 11:00:00', 2, 109.98),
('2024-06-03 12:00:00', 3, 109.98);

insert into reviews (user_id, product_id, review) values
(1, 1, 'Ottimo gioco di calcio!'),
(1, 2, 'Il miglior gioco di guerra che abbia mai giocato!'),
(2, 3, 'Avventura epica!'),
(2, 4, 'Divertente e creativo!'),
(3, 5, 'Azione pura!'),
(3, 6, 'Fantascienza al top!');