INSERT INTO author (id, name, biography, nationality) VALUES
(1,'James Joyce','Irish novelist and poet.','Ireland'),
(2,'Virginia Woolf','English writer and avant-garde feminist.','United Kingdom'),
(3,'Gabriel Garcia Marquez','Colombian journalist and novelist.','Colombia'),
(4,'Toni Morrison','American novelist and essayist.','United States'),
(5,'Chinua Achebe','Nigerian novelist and critic.','Nigeria'),
(6,'Jane Austen','English novelist known for realism.','United Kingdom'),
(7,'Yuval Noah Harari','Israeli historian and author.','Israel'),
(8,'Marjane Satrapi','Graphic novelist from Iran and France.','France'),
(9,'Ada Lovelace','Mathematician and writer on analytical engines.','United Kingdom'),
(10,'Ada Limon','Poet laureate focusing on lyrical narrative.','United States');

INSERT INTO book (id, title, publication_year, genre, pages, author_id) VALUES
(1,'Ulysses',1922,'Fiction',730,1),
(2,'Dubliners',1914,'Short Stories',207,1),
(3,'Mrs Dalloway',1925,'Fiction',263,2),
(4,'To The Lighthouse',1927,'Fiction',324,2),
(5,'One Hundred Years Of Solitude',1967,'Magical Realism',417,3),
(6,'Love In The Time Of Cholera',1985,'Fiction',368,3),
(7,'Song Of Solomon',1977,'Fiction',348,4),
(8,'Beloved',1987,'Historical Fiction',324,4),
(9,'Things Fall Apart',1958,'Fiction',209,5),
(10,'No Longer At Ease',1960,'Fiction',192,5);
