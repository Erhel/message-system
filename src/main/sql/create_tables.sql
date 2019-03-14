\c message-system;

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY, 
    username VARCHAR (255) UNIQUE NOT NULL,
    password VARCHAR (255) NOT NULL,
    display_name VARCHAR (255) NOT NULL);
    
CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    subject VARCHAR (255),
    message VARCHAR (255),
    sender_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    CONSTRAINT fk_message_sender FOREIGN KEY (sender_id) REFERENCES "user" (id),
    CONSTRAINT fk_message_recipient FOREIGN KEY (recipient_id) REFERENCES "user" (id));