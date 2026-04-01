create table if not exists users (
    id serial primary key,
    name varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(10) not null default 'USER',
    constraint chk_role check (role in ('USER', 'ADMIN'))
);

create table if not exists hibernate_sequences (
    sequence_name varchar(255) not null,
    next_val int8,
    primary key (sequence_name)
);

create table if not exists products (
    id serial primary key,
    name varchar(255) not null,
    description text,
    price double precision not null
);

create table if not exists purchases (
    id serial primary key,
    user_id int not null,
    product_id int not null,
    quantity int not null,
    foreign key (user_id) references users(id),
    foreign key (product_id) references products(id)
);

create table if not exists orders (
    id serial primary key,
    data timestamp not null,
    user_id int not null,
    total double precision not null,
    foreign key (user_id) references users(id)
);

create table if not exists reviews (
    id serial primary key,
    user_id int not null,
    product_id int not null,
    review text not null,
    foreign key (user_id) references users(id),
    foreign key (product_id) references products(id)
);

