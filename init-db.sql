-- Create users table for application
CREATE TABLE IF NOT EXISTS nim_users (
    id VARCHAR(255) PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    nim VARCHAR(20) NOT NULL UNIQUE,
    jenis_kelamin VARCHAR(50)
);

-- Create index on NIM
CREATE INDEX IF NOT EXISTS idx_nim ON nim_users(nim);

-- Insert sample data
INSERT INTO nim_users (id, nama, nim, jenis_kelamin)
VALUES ('1', 'admin', '20230140216', 'Laki-laki')
ON CONFLICT (id) DO NOTHING;