# Spring-Admin

A plug-gable admin control UI for SpringBoot

This project designed to be used similar as Django-Admin.

Developer can add this project to any existing Spring boot project. And use the front-end SPA UI to browser managed entities.

It supports ALL CRUD operations, and also provides form validation for input data.

Steps:

1. Add this project to your dependency.
2. Register your entity class with a route name. See example below.
3. Default route can be configured in property file 
```
admin:
  prefix:
    uri: admin
```

```
	private final AdminContainer adminContainer;

	@Autowired
	public AdminApplication(AdminContainer adminContainer) {
		this.adminContainer = adminContainer;
		this.adminContainer.register("user", UserInfo.class);
		this.adminContainer.register("role", RoleInfo.class);
	}
```

**Existing routes:**

Get: admin/entity/{entity}/{id} --- (*Retreive by Id*)

Get: admin/entity/{entity} --- (*Retreive all*)

Delete: admin/entity/{entity}/{id} --- (*Delete by Id*)

Create: admin/entity/{entity} --- (*Create*)

Put: admin/entity/{entity} --- (*Create/Modify*)

Get(*Page*): admin/entity/{entity}/page/all?size= (*Return page numbers and content*)

Get(*Page*): admin/entity/{entity}/page?size=&page= (*Retreive items by page and size*)

Get: admin/properties (*Retrieve all entities info*)