INSERT IGNORE INTO `user` (`id`, `password`, `role`, `username`, `valid`) 
VALUES ('1', '$2a$12$JPHeIlsyc02lgn8BynWVXOlO3Mh9XXVbtTy1ST/n5VycQH.CrhgZ.', 'ADMIN', 'admin', b'1');

INSERT IGNORE INTO `patient` (`id`, `address`, `birthdate`, `genre`, `lastname`, `name`, `phone_number`) 
VALUES 
(1, '1 Brookside St', '1966-12-29 00:00:00.000000', 'F', 'TestNone', 'Test', '100-222-3339'),
(2, '2 High St', '1945-06-24 00:00:00.000000', 'M', 'TestBorderline', 'Test', '200-333-4444'),
(3, '3 Club Road', '2004-06-18 00:00:00.000000', 'M', 'TestInDanger', 'Test', '300-444-5555'),
(4, '4 Valley Dr', '2002-06-28 00:00:00.000000', 'F', 'TestEarlyOnset', 'Test', '400-555-6666');