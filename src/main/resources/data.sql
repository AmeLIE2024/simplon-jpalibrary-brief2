DROP TABLE IF EXISTS book;

CREATE TABLE book (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    available BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO book (title, description, available)
VALUES
    ('Bilbo le hobbit', 'Bilbo, comme tous les hobbits, est un petit être paisible et sans histoire. Son quotidien est ' ||
    'bouleversé un beau jour, lorsque Gandalf le magicien et treize nains barbus l’entraînent dans un voyage périlleux. ' ||
    'C’est le début d’une grande aventure, d’une fantastique quête au trésor semée d’embûches et d’épreuves, qui mènera Bilbo ' ||
    'jusqu’à la Montagne Solitaire gardée par le dragon Smaug…', true),
    ('Vertige', 'Certains secrets sont inavouables mais serions-nous prêts à mourir pour les cacher ? Un homme se réveille ' ||
    'au fond d''un gouffre, deux inconnus et son fidèle chien comme seuls compagnons d''infortune. Il est enchaîné au poignet, ' ||
    'l''un des deux hommes à la cheville et le troisième est libre, mais sa tête est recouverte d''un masque effroyable, ' ||
    'qui explosera s''il s''éloigne des deux autres. Qui les a emmenés là ? Pourquoi ?', true),
    ('In tenebris', 'Des ténèbres, nul ne sort indemne. Les propos de Julia, retrouvée scalpée, errant dans les rues de Brooklyn, ' ||
    'n''ont de sens que pour elle. Elle affirme sortir de l''Enfer, avoir échappé au Diable lui-même. Et n''être pas la seule...' ||
    'Sous la neige new-yorkaise, couve un feu de tourments – un bûcher d''innocents. Le profileur Joshua Brolin sait qu''il lui ' ||
    'faudra y plonger.Sans espoir de salut...', false);

