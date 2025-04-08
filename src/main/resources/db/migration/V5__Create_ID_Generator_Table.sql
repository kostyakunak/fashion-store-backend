CREATE TABLE IF NOT EXISTS id_generator (
    sequence_name VARCHAR(255) NOT NULL,
    next_val BIGINT,
    PRIMARY KEY (sequence_name)
);

INSERT INTO id_generator (sequence_name, next_val) 
VALUES ('order_details_sequence', 1) 
ON DUPLICATE KEY UPDATE next_val = next_val; 