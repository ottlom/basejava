create table if not exists resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table if not exists contact
(
    id           serial
        constraint contact_pk
            primary key,
    typecontact  text     not null,
    contactvalue text     not null,
    resume_uuid  char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

alter table contact
    owner to postgres;

create unique index if not exists contact_uuid_type_index
    on contact (resume_uuid, typecontact);

create table if not exists section
(
    resume_uuid  char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on delete cascade,
    id           serial
        constraint section_pk
            primary key,
    typesection  text     not null,
    sectionvalue text     not null
);

alter table section
    owner to postgres;

create unique index if not exists section_uuid_type_index
    on section (resume_uuid, typesection);
