CREATE TABLE IF NOT EXISTS tasks (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
                                     description TEXT,
                                     user_id BIGINT NOT NULL);

INSERT INTO tasks (title, description, user_id) VALUES
                                                    ('Задача 1', 'добавить информацию', 1),
                                                    ('Задача 2', 'удалить фото', 1),
                                                    ('Задача 3', 'искать ответы', 2),
                                                    ('Задача 4', 'переписать класс', 3);