insert into werknemers(voornaam, familienaam, filiaalid)
values ('Joe', 'Dalton', (select id from filialen where naam='Alfa'));
insert into werknemers(voornaam, familienaam, filiaalid)
values ('Jack', 'Dalton', (select id from filialen where naam='Bravo'));
insert into werknemers(voornaam, familienaam, filiaalid)
values ('Lucky', 'Luke', (select id from filialen where naam='Charly'));