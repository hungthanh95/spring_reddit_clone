alter table user modify password varchar(255) not null;
alter table post modify post_name varchar(255) not null;
alter table subreddit modify name varchar(255) not null;
alter table subreddit modify description varchar(255) not null;
alter table comment modify text varchar(255) not null;
alter table post add constraint post_subreddit_subreddit_id_fk foreign key (subreddit_id) references subreddit (subreddit_id);
alter table subreddit add constraint subreddit_user_user_id_fk foreign key (user_id) references user (user_id);
alter table token add constraint token_user_user_id_fk foreign key (user_id) references user (user_id);