create table user (
    user_id bigint(20) not null auto_increment,
    created datetime default now(),
    email varchar(255) not null ,
    enabled bit(1) default 0,
    password varchar(255),
    username varchar(255) not null ,
    constraint primary key (user_id),
    index (email, username)
)

create table post (
    post_id bigint(20) not null auto_increment,
    created_date datetime default now(),
    description longtext,
    post_name varchar(255),
    url varchar(255),
    vote_count int(11),
    subreddit_id bigint(20) ,
    user_id bigint(20),
    constraint primary key (post_id),
    constraint foreign key (user_id) references user(user_id)
)

create table subreddit (
    id bigint(20) not null auto_increment,
    created_date datetime default now(),
    description varchar(255),
    name varchar(255),
    user_id bigint(20),
    primary key (id)
)

create table comment (
    id bigint(20) not null auto_increment,
    created_date datetime default now(),
    text varchar(255),
    post_id bigint(20),
    user_id bigint(20),
    constraint primary key (id),
    constraint foreign key (post_id) references post(post_id),
    constraint foreign key (user_id) references user(user_id)
)
create table vote (
    vote_id bigint(20) not null auto_increment,
    vote_type int(11) not null ,
    post_id bigint(20) not null ,
    user_id bigint(20) not null ,
    constraint primary key (vote_id),
    constraint foreign key (post_id) references post(post_id),
    constraint foreign key (user_id) references user(user_id)
)

create table subreddit_posts (
    subreddit_id bigint(20) not null ,
    post_id bigint(20) not null ,
    index (subreddit_id, post_id),
    constraint foreign key (subreddit_id) references subreddit(id),
    constraint foreign key (post_id) references post(post_id)
)

create table hibernate_sequence(
    next_val bigint(20) not null
)

create table token (
    id bigint(20) not null auto_increment,
    expiry_date datetime default now(),
    token varchar(255),
    user_id bigint(20),
    constraint primary key (id)
)

create table user_subreddit (
    user_id bigint(20),
    subreddit_id bigint(20),
    index (user_id, subreddit_id)
)