create table events
(
    id         int auto_increment
        primary key,
    user_id    int          not null,
    event_name varchar(120) not null,
    file_id    int          not null,
    constraint file_id
        foreign key (file_id) references files (id),
    constraint user_id
        foreign key (user_id) references users (id)
);

create index file_id_idx
    on events (file_id);

create index user_id_idx
    on events (user_id);

