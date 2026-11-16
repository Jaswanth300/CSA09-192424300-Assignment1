-- ==============================================================================
-- SIMATS Java Programming Assignment 4 - Task 6
-- Campus Event Registration System Database Schema
-- Student: Bala Naga Jaswanth (Reg: 192424300)
-- ==============================================================================

CREATE TABLE IF NOT EXISTS events (
    event_id VARCHAR(10) PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    venue VARCHAR(100) NOT NULL,
    max_capacity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS registrations (
    reg_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(15) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    event_id VARCHAR(10) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events(event_id),
    -- Business Constraint: No duplicate registrations for the same event
    CONSTRAINT uq_student_event UNIQUE (student_id, event_id)
);

-- Seed Initial Events
INSERT INTO events (event_id, event_name, venue, max_capacity) VALUES
('EVT-HACK', 'SIMATS 24-Hour National Hackathon', 'Auditorium 1', 200),
('EVT-ROBO', 'RoboWars Combat Championship', 'Mechanical Arena', 100),
('EVT-COD',  'Algorithmic Coding Showdown', 'CS Lab 3 & 4', 150),
('EVT-CULT', 'AURA Inter-College Cultural Gala', 'Open Air Theatre', 500);
