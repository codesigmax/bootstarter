-- 添加审计字段触发器
create or replace function update_table_update_time()
    returns trigger as
$$
begin
    new.update_time = current_timestamp;
    return new;
end;
$$ language plpgsql;

-- 系统用户表
create table if not exists sys_users
(
    id          int8         not null,
    username    varchar(16)  not null unique,
    email       varchar(255) not null unique,
    phone       varchar(20)  not null unique,
    password    varchar(255) not null,
    create_time timestamp    not null default current_timestamp,
    update_time timestamp    not null default current_timestamp,
    status      int2         not null default 0,
    constraint pk_sys_users primary key (id)
);

comment on table sys_users is '系统用户表';

comment on column sys_users.id is '用户ID，使用雪花算法生成';
comment on column sys_users.username is '用户名，最长为16位字符';
comment on column sys_users.email is '用户电子邮箱地址';
comment on column sys_users.phone is '用户电话号码';
comment on column sys_users.password is '用户登陆密码';
comment on column sys_users.create_time is '用户创建时间（或注册时间）';
comment on column sys_users.update_time is '用户信息最后修改时间';
comment on column sys_users.status is '用户激活状态（0-未激活，1-激活，3-主动锁定，4-平台封禁）';

create or replace trigger update_sys_users_update_time
    before update
    on sys_users
    for each row
execute function update_table_update_time();

-- 角色表
create table if not exists roles
(
    id          int8         not null,
    name        varchar(32)  not null unique,
    code        varchar(32)  not null unique,
    description varchar(255) null,
    constraint pk_roles primary key (id)

);

comment on table roles is '角色表';

comment on column roles.id is '角色ID，使用雪花算法生成';
comment on column roles.name is '角色名';
comment on column roles.code is '角色代码，命名格式为 ROLE_{角色全大写英文名}，如 ROLE_ADMIN';
comment on column roles.description is '角色的详细描述';

-- 用户角色记录表
create table if not exists user_role_relations
(
    user_id int8 not null,
    role_id int2 not null,
    constraint fk_user_role_relations_user_id foreign key (user_id) references sys_users (id),
    constraint fk_user_role_relations_role_id foreign key (role_id) references roles (id),
    constraint pk_user_role primary key (user_id, role_id)
);

comment on table user_role_relations is '用户-角色关联表';

comment on column user_role_relations.user_id is '用户ID';
comment on column user_role_relations.role_id is '角色ID';

-- 权限表
create table if not exists permissions
(
    id          int8         not null,
    name        varchar(32)  not null unique,
    code        varchar(32)  not null unique,
    description varchar(255) null,
    constraint pk_permissions primary key (id)
);

comment on table permissions is '权限表';

comment on column permissions.id is '权限ID，使用雪花算法生成';
comment on column permissions.name is '权限名';
comment on column permissions.code is '权限代码，命名格式为 {业务对象类型}:{权限类型}，如 user:edit';
comment on column permissions.description is '权限的详细描述';

-- 角色权限表
create table if not exists role_permission_relations
(
    role_id       int8 not null,
    permission_id int8 not null,
    constraint fk_role_permission_relations_role_id foreign key (role_id) references roles (id),
    constraint fk_role_permission_relations_permission_id foreign key (permission_id) references permissions (id),
    constraint pk_role_permission primary key (role_id, permission_id)
);

comment on table role_permission_relations is '角色-权限关联表';

comment on column role_permission_relations.role_id is '角色ID';
comment on column role_permission_relations.permission_id is '权限ID';
