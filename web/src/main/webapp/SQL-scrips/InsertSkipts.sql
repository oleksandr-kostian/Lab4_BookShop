
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Imaginative literature',null,'Wonderful leisure. Everyone will find their rubric of this section.',2);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Educational literature',null,'Literature for study.',2);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('House and Life',null,'Literature for improving you and your house.',2);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Other',null,'Other interesting book.',2);


INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Detectives',1,'A fascinating story with a lot of secrets.',1);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Novel',1,'A fascinating love/life story.',1);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Fantastic',1,'Supernatural adventure.',1);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Study',2,'Literature for study.',1);

INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Life',3,'Literature for improving you.',1);
INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('House',3,'Literature for improving your house.',1);

INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values('Other',4,'Other interesting book.',1);



INSERT INTO AUTHOR(SURNAME,NAME) values('Stendhal', 'Frederick');
INSERT INTO AUTHOR(SURNAME,NAME) values('Arthur Conan', 'Doyle');
INSERT INTO AUTHOR(SURNAME,NAME) values('von Goethe', 'Johann Wolfgang');
INSERT INTO AUTHOR(SURNAME,NAME) values('Forsch', 'Tatiana');

INSERT INTO AUTHOR(SURNAME,NAME) values('Tokarev', 'Victoria');
INSERT INTO AUTHOR(SURNAME,NAME) values('Sergey', 'Garagulya');
INSERT INTO AUTHOR(SURNAME,NAME) values('Savina', 'Lyudmila');
INSERT INTO AUTHOR(SURNAME,NAME) values('Book Club', '"Family Leisure Club"');
INSERT INTO AUTHOR(SURNAME,NAME) values('Julia', 'Vysotsky');
INSERT INTO AUTHOR(SURNAME,NAME) values('Jojo', 'Moyes');
INSERT INTO AUTHOR(SURNAME,NAME) values('Veronica', 'Roth');





execute ADDBOOK('Red and black','The amazing story of Julien life.',6,1,576,120,10);
execute ADDBOOK('Sherlock Holmes: The Hound of the Baskervilles.','Tireless Sherlock Holmes is still investigating complex cases, and Dr. Watson in this strongly helps him.',5,2,680,150,5);
execute ADDBOOK('Johann Wolfgang von Goethe. Small Works.','Johann Wolfgang von Goethe - a poet, is the eternal pride and glory of Germany.',11,3,300,140,10);

execute ADDBOOK('How to find Phoenix','The amazing story.',7,4,684,60,10);
execute ADDBOOK('This best of all worlds','Almost everyone is in the life of the main love and not a few major non-principal -. Are forgotten and most importantly.',6,5,256,130,7);

execute ADDBOOK('Sherlock Holmes: The Valley of fear.','Tireless Sherlock Holmes is still investigating complex cases, and Dr. Watson in this strongly helps him.',5,2,288,60,6);

execute ADDBOOK('English language for students','Designed for undergraduate and specialty architecture universities.',8,6,368,310,3);

execute ADDBOOK('ABC_BOOK','ABC_BOOK for your litle child.',8,7,112,90,5);
execute ADDBOOK('Exercise book for ABC-book','Exercise book for ABC-book of your litle child.',8,7,128,21,4);
execute ADDBOOK('Beads','Learn to make amazing jewelry from beads.',9,8,48,58,5);
execute ADDBOOK('Cross-stitch.','Scheme for embroidery.',9,8,65,90,4);
execute ADDBOOK('New puzzles Sherlock Holmes','New puzzles of amazing Sherlock Holmes. A lot of new secrets and mysteries.',11,8,256,450,3);
execute ADDBOOK('Interior Design','Even a small apartment can be comfortable! In the book you will find helpful tips that can help you dramatically change your house.',10,8,160,110,7);
execute ADDBOOK('Best recipes','Best recipes of Julia Vysotsky.',9,9,128,370,3);
execute ADDBOOK('Best recipes. Part II','Best recipes of Julia Vysotsky.',9,9,128,370,2);
execute ADDBOOK('Before I met you','Lou Clark do not knows what is about to lose his job and that in the near future, it will need all the strength to overcome her problems.',6,10,210,107,3);
execute ADDBOOK('After you','Now Lou Clark is not just an ordinary girl, that living an ordinary life. Six months spent with Will Traynor, changed her forever.',6,10,210,105,3);

execute ADDBOOK('Divergent','In a world where lives Beatrice Prior, people are divided into five factions. Read continued.',7,11,416,160,9);
execute ADDBOOK('Insurgents','New part of Divergent',7,11,384,120,11);
execute ADDBOOK('Four. History divergents','New part of Divergent',7,11,352,150,5);


INSERT INTO CUSTOMER(LOGIN,PASSWORD,E_MAIL,PHOME_NUBMER,ROLE) values('Admin','root','admin@yandex.ua','0951223335',10);
INSERT INTO CUSTOMER(LOGIN,PASSWORD,E_MAIL,PHOME_NUBMER,ROLE) values('System','system','system@yandex.ua','0951221115',0);







