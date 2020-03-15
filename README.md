# ui-admin

## 权限注解案例

```
@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
@PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
@PreAuthorize("hasRole('ADMIN') and hasPermission('/admin/adminRoleList','sys:role:info')")
@PreAuthorize("hasRole('USER') and hasPermission('/user/menuList','sys:user:info')")
```

























