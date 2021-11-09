       create table tipo_data (
       id_tipo_data bigint not null auto_increment,
        descricao varchar(255),
        primary key (id_tipo_data)
    ) engine=InnoDB;

    create table calendario (
       id_calendario bigint not null auto_increment,
        data_especial datetime(6),
        descricao varchar(255),
        id_tipo_data bigint,
        primary key (id_calendario)
    ) engine=InnoDB;

    create table categoria_usuario (
       id_cat_usuario bigint not null auto_increment,
        descricao varchar(255),
        primary key (id_cat_usuario)
    ) engine=InnoDB;

    create table empresa (
       id_empresa bigint not null auto_increment,
        bairro varchar(255),
        cidade varchar(255),
        cnpj varchar(255),
        descricao varchar(255),
        endereco varchar(255),
        estado varchar(255),
        telefone varchar(255),
        primary key (id_empresa)
    ) engine=InnoDB;

    create table jornada_trabalho (
       id_jornada_trabalho bigint not null auto_increment,
        descricao varchar(255),
        primary key (id_jornada_trabalho)
    ) engine=InnoDB;

    create table localidade (
       id bigint not null,
        id_nivel_acesso bigint not null,
        descricao varchar(255),
        primary key (id, id_nivel_acesso)
    ) engine=InnoDB;


    create table nivel_acesso (
       id_nivel_acesso bigint not null auto_increment,
        descricao varchar(255),
        primary key (id_nivel_acesso)
    ) engine=InnoDB;

    create table ocorrencia (
       id_ocorrencia bigint not null auto_increment,
        descricao varchar(255),
        nome varchar(255),
        primary key (id_ocorrencia)
    ) engine=InnoDB;

    create table usuario (
       id_usuario bigint not null auto_increment,
        final_jornada datetime(6),
        inicio_jornada datetime(6),
        nome varchar(255),
        tolerancia decimal(19,2),
        id_cat_usuario bigint,
        id_empresa bigint,
        id_jornada_trabalho bigint,
        id_nivel_acesso bigint,
        primary key (id_usuario)
    ) engine=InnoDB;

create table movimentacao (
       id_movimentacao bigint not null,
        id_usuario bigint not null,
        data_entrada datetime(6),
        data_saida datetime(6),
        periodo decimal(19,2),
        id_calendario bigint,
        id_ocorrencia bigint,
        primary key (id_movimentacao, id_usuario)
    ) engine=InnoDB;

    create table banco_horas (
       id_banco_horas bigint not null,
        id_movimentacao bigint not null,
        id_usuario bigint not null,
        data_trabalhada datetime(6),
        quantidade_horas decimal(19,2),
        saldo_horas decimal(19,2),
        primary key (id_banco_horas, id_movimentacao, id_usuario)
    ) engine=InnoDB;


    alter table banco_horas
       add constraint FK_banco_horas_movimentacao
       foreign key (id_movimentacao, id_usuario)
       references movimentacao (id_movimentacao, id_usuario);

    alter table calendario
       add constraint FK_calendario_tipo_data
       foreign key (id_tipo_data)
       references tipo_data (id_tipo_data);

    alter table localidade
       add constraint FK_localidade_nivel_acesso
       foreign key (id_nivel_acesso)
       references nivel_acesso (id_nivel_acesso);

    alter table movimentacao
       add constraint FK_movimentacao_calendario
       foreign key (id_calendario)
       references calendario (id_calendario);

    alter table movimentacao
       add constraint FK_movimentacao_ocorrencia
       foreign key (id_ocorrencia)
       references ocorrencia (id_ocorrencia);

    alter table movimentacao
       add constraint FK_movimentacao_usuario
       foreign key (id_usuario)
       references usuario (id_usuario);

    alter table usuario
       add constraint FK_usuario_categoria_usuario
       foreign key (id_cat_usuario)
       references categoria_usuario (id_cat_usuario);

    alter table usuario
       add constraint FK_usuario_empresa
       foreign key (id_empresa)
       references empresa (id_empresa);

    alter table usuario
       add constraint FK_usuario_jornada_trabalho
       foreign key (id_jornada_trabalho)
       references jornada_trabalho (id_jornada_trabalho);

    alter table usuario
       add constraint FK_usuario_nivel_acesso
       foreign key (id_nivel_acesso)
       references nivel_acesso (id_nivel_acesso);
