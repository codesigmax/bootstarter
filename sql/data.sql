insert into roles(id, name, code, description)
values (1, 'admin', 'ROLE_ADMIN', '管理员'),
       (2, 'user', 'ROLE_USER', '用户');

insert into permissions (id, name, code)
values (1, '查看角色列表', 'role:list'),
       (2, '查看角色', 'role:check'),
       (3, '编辑角色', 'role:edit'),
       (4, '删除角色', 'role:delete');

insert into role_permission_relations(role_id, permission_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 2);