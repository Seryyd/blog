create table posts(
    id varchar(36) not null,
    time_stamp varchar(255) not null,
    author_id varchar(255) not null,
    title varchar(255)  not null,
    shortText text not null,
    longText text not null,
    primary key(id)
);