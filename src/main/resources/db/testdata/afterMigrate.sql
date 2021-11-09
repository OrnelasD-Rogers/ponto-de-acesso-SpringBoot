set foreign_key_checks = 0;

delete from banco_horas;
delete from calendario;
delete from categoria_usuario;
delete from empresa;
delete from jornada_trabalho;
delete from localidade;
delete from movimentacao;
delete from nivel_acesso;
delete from ocorrencia;
delete from tipo_data;
delete from usuario;

set foreign_key_checks = 1;


alter table calendario auto_increment = 1;
alter table categoria_usuario auto_increment = 1;
alter table empresa auto_increment = 1;
alter table jornada_trabalho auto_increment = 1;
alter table nivel_acesso auto_increment = 1;
alter table ocorrencia auto_increment = 1;
alter table tipo_data auto_increment = 1;
alter table usuario auto_increment = 1;

insert into tipo_data values(1, 'Dia comum');
insert into tipo_data values(2, 'Feriado');
insert into tipo_data values(3, 'Final de semana');

insert into calendario values(1, utc_timestamp, 'Calendario gregoriado',1);
insert into calendario values(2, utc_timestamp, 'Calendario juliano',2);
insert into calendario values(3, utc_timestamp, 'Calendario chinês',3);

insert into categoria_usuario values(1, 'CLT');
insert into categoria_usuario values(2, 'PJ');

insert into empresa values(1, 'Limão', 'São Paulo', '85471458798', 'TI S.A', 'rua roque de morais', 'SP', '41988552214');
insert into empresa values(2, 'Vila Marumby', 'Maringá', '41258889635', 'Churrasquero Boutique de Carnes e Temperos', 'Av. Arquiteto Nildo Ribeiro da Rocha, 1704', 'PR', '4433054000');
insert into empresa values(3, 'Zona 01', 'Maringá', '77412554123', 'Lojão Do Real', 'R. Néo Alves Martins, 2900', 'PR', '4430339026');

insert into jornada_trabalho values(1, 'Jornada Comum');
insert into jornada_trabalho values(2, 'Hora extra');
insert into jornada_trabalho values(3, 'Feriados');

insert into nivel_acesso values(1, 'su');
insert into nivel_acesso values(2, 'admin');
insert into nivel_acesso values(3, 'comum');

insert into localidade values(1, 1, 'Acesso irrestrito');
insert into localidade values(2, 2, 'Acesso administrativo');
insert into localidade values(3, 1, 'Acesso simples');

insert into ocorrencia values(1, 'Nova ocorrencia', 'nova');
insert into ocorrencia values(2, 'Nova entrada', 'entrada');
insert into ocorrencia values(3, 'Nova saida', 'saida');

insert into usuario values(1, utc_timestamp, utc_timestamp, 'Rodrigo', 10, 1, 1, 1, 3);
insert into usuario values(2, utc_timestamp, utc_timestamp, 'Marcos', 5, 2, 2, 2, 2);
insert into usuario values(3, utc_timestamp, utc_timestamp, 'Lucas', 10, 1, 3, 3, 3);

insert into movimentacao values(1, 1, utc_timestamp, utc_timestamp, 15, 1, 2);
insert into movimentacao values(2, 1, utc_timestamp, utc_timestamp, 15, 1, 3);
insert into movimentacao values(3, 2, utc_timestamp, utc_timestamp, 5, 2, 1);

insert into banco_horas values(1, 1, 1, utc_timestamp, 15, 10);
insert into banco_horas values(2, 2, 1, utc_timestamp, 10, 5);
insert into banco_horas values(3, 3, 2, utc_timestamp, 20, 3);