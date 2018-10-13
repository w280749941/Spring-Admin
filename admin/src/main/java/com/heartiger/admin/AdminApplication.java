package com.heartiger.admin;

import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.datamodel.RoleInfo;
import com.heartiger.admin.datamodel.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AdminApplication {

	private final AdminContainer adminContainer;

	@Autowired
	public AdminApplication(AdminContainer adminContainer) {
		this.adminContainer = adminContainer;
		this.adminContainer.register("user", UserInfo.class);
		this.adminContainer.register("role", RoleInfo.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
