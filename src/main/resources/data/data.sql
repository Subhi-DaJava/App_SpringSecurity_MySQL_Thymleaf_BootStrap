
INSERT INTO `app_role` (description, role_name) VALUES ('Gestion tout','ADMIN'),
                                                      ('Visiter uniquement certaines sources, sans le droit de rajouter','USER'),
                                                      ('Supprimer/Ajouter AppUser/AppRole','MANAGER'),
                                                      ('Visiter uniquement certaines sources, sans le droit de rajouter, test','Visiteur'),
                                                      ('All roles of admin and user ','SUPER_ADMIN'),
                                                      ('Rajouter les patients ','Assistance'),
                                                      ('Just a test','User_Test'),
                                                      ('Visiter uniquement certaines sources, sans le droit de rajouter, test','Visiteur_4');

INSERT INTO app_user (active, password, username) VALUES (1,'$2a$10$ifwPiDsl.GGmjV.RZqjt4eOs20P56PNe77aT/DePwPP/1rghIns5y','Adil'),
                                                     (1,'$2a$10$UmCOKb9rcI1Bdqr/jDDgpePgkX3IAIYnyYNl3mzLlnHwg9EF3sBli','Emet'),
                                                     (1,'$2a$10$G35LLLyP74KDE2EsbE3GSesjRwLiQBJTJo7tcbbBintw1laNEBE3C','Amine'),
                                                     (1,'$2a$10$m6ecfgrS3drcXuFXs/B0zeGMYTrixFLe19XcYGmdHCRWHLpJZnvNi','Sultan'),
                                                     (1,'$2a$10$q0hyJbNd00oEvHVKJi8itunPyRhiYkpwGQAPN35.oNOLLsCBl4.9K','Osman'),
                                                     (1,'$2a$10$qsDzDTf/lg.AuXdTnxW4A./Nji7MVUW05Qac2sP8f9yF0E2UjDHhy','Muhemmetimin');

INSERT INTO `app_user_roles` VALUES (1,2),(1,1),(2,2),(4,3),(4,1),(4,2),(1,5);

INSERT INTO `patient`(date_naissance, malade, nom, score ) VALUES ('2022-05-10',1,'Maxime_yahhs',300),
                                                                  ('2022-05-20',1,'July_4',106),
                                                                  ('2022-05-20',1,'Jean',200),
                                                                  ('2022-05-20',1,'Maxime',120),
                                                                  ('2022-05-20',1,'July',124),
                                                                  ('2022-05-20',0,'Alain',200),
                                                                  ('2022-05-20',1,'Jean',123),
                                                                  ('2022-05-20',0,'July',145),
                                                                  ('2022-05-20',1,'Alain',150),
                                                                  ('2022-05-20',0,'Jean',165),
                                                                  ('2022-05-20',1,'Maxime',235),
                                                                  ('2022-05-20',1,'July',167),
                                                                  ('2022-05-20',0,'Alain',425),
                                                                  ('2022-05-20',1,'Jean',115),
                                                                  ('2022-05-20',1,'Maxime',335),
                                                                  ('2022-05-20',0,'July',267),
                                                                  ('2022-05-20',1,'Alain',235),
                                                                  ('2022-05-20',0,'Jean',153),
                                                                  ('2022-05-20',1,'Maxime',435),
                                                                  ('2022-05-20',1,'July',767),
                                                                  ('2022-05-20',1,'Alain',185),
                                                                  ('2022-05-20',0,'Jean',105),
                                                                  ('2022-05-20',1,'Maxime',235);


