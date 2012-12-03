CREATE TABLE Shifts (
    shiftid INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    day ENUM('SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT') DEFAULT 'SUN' NOT NULL,
    time ENUM('DAY', 'NIGHT') DEFAULT 'DAY' NOT NULL,
    PRIMARY KEY (shiftid),
    UNIQUE (day, time));

CREATE TABLE Sponsors (
    sid INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (sid),
    UNIQUE (name));

CREATE TABLE Juniors (
    jid INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (jid),
    UNIQUE (name));

CREATE TABLE Works (
    sid INT UNSIGNED NOT NULL,
    shiftid INT UNSIGNED NOT NULL,
    PRIMARY KEY (sid, shiftid),
    FOREIGN KEY (sid) REFERENCES Sponsors (sid),
    FOREIGN KEY (shiftid) REFERENCES Shifts(shiftid));
    
CREATE TABLE Sponsorships (
    sponsorshipid INT UNSIGNED NOT NULL AUTO_INCREMENT,
    sid INT UNSIGNED NOT NULL,
    jid INT UNSIGNED NOT NULL,
    shiftid INT UNSIGNED NOT NULL,
    PRIMARY KEY(sponsorshipid),
    FOREIGN KEY (sid) REFERENCES Sponsors (sid),
    FOREIGN KEY (jid) REFERENCES Juniors (jid),
    FOREIGN KEY (shiftid) REFERENCES Shifts(shiftid));
    
CREATE VIEW ShiftAssignments (sid, name, shiftid, shift)
    AS SELECT S.sid, S.name, SH.shiftid, SH.name 
    FROM Sponsors S, Shifts SH, Works W
    WHERE S.sid = W.sid AND SH.shiftid = W.shiftid;
    
CREATE VIEW JuniorAssignments (sid, sponsor, jid, junior, shiftid, shift)
    AS SELECT S.sid, S.name, J.jid, J.name, SH.shiftid, SH.name
    FROM Sponsors S, Shifts SH, Sponsorships SP, Juniors J
    WHERE S.sid = SP.sid AND J.jid = SP.jid AND SH.shiftid = SP.shiftid;
    
CREATE VIEW SponsorAvailabilities (sid, sponsor, shiftid, shift)
    AS SELECT S.sid, S.name, SH.shiftid, SH.name 
    FROM Sponsors S, Shifts SH, Works W
    WHERE S.sid = W.sid AND SH.shiftid = W.shiftid
        AND S.sid NOT IN (SELECT DISTINCT SP.sid FROM Sponsorships SP);


