package com.kairos.core.web;

import lombok.experimental.UtilityClass;
import org.springframework.web.bind.annotation.RequestMethod;
import com.kairos.core.security.AllowedMethods;

@UtilityClass
public class ApiConstants {
	public static final String SERVICE = "/api/v1";

	public static final String PARAM_ID = "/{id}";

	public static final String HATEOAS = "/hateoas";

	public static class Admin {

		public static class Perms {
			@AllowedMethods({RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
			public static final String ADMIN = "admin_only";
		}

		public static final String ADMIN_API = SERVICE + "/admin";
		public static final String ME = "/me";
	}

	public static class Assets {

		public static class Perms {
			@AllowedMethods(RequestMethod.GET)
			public static final String ASSETS_READ = "assets_read";
			@AllowedMethods(RequestMethod.POST)
			public static final String ASSETS_UPDATE = "assets_update";
			@AllowedMethods(RequestMethod.DELETE)
			public static final String ASSETS_DELETE = "assets_delete";
		}

		public static final String ASSETS_API = SERVICE + "/assets";
		public static final String ASSET_ID = "/{assetId}";
	}

	public static class Departments {

		public static class Perms {
			@AllowedMethods(RequestMethod.GET)
			public static final String DEPARTMENTS_READ = "departments_read";
			@AllowedMethods(RequestMethod.POST)
			public static final String DEPARTMENTS_UPDATE = "departments_update";
			@AllowedMethods(RequestMethod.DELETE)
			public static final String DEPARTMENTS_DELETE = "departments_delete";
		}

		public static final String DEPARTMENTS_API = SERVICE + "/departments";
	}

	public static class Employees {

		public static class Perms {

			@AllowedMethods(RequestMethod.GET)
			public static final String EMPLOYEES_READ = "employees_read";
			@AllowedMethods(RequestMethod.POST)
			public static final String EMPLOYEES_UPDATE = "employees_update";
			@AllowedMethods(RequestMethod.DELETE)
			public static final String EMPLOYEES_DELETE = "employees_delete";
		}

		public static final String EMPLOYEES_API = SERVICE + "/employees";

	}

	public static class Hateoas {
		public static final String HATEOAS_API = SERVICE + HATEOAS;
	}
}
