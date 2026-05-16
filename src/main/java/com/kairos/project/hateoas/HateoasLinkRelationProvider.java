package com.kairos.project.hateoas;

import com.kairos.core.hateoas.HateoasDef;
import com.kairos.project.assets.web.AssetResponse;
import com.kairos.project.departments.web.DepartmentResponse;
import com.kairos.project.employees.web.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HateoasLinkRelationProvider extends com.kairos.core.hateoas.HateoasLinkRelationProvider {
	@Override
	protected List<HateoasDef> definitions() {
		return List.of(
				new HateoasDef(AssetResponse.class, "data"),
				new HateoasDef(DepartmentResponse.class, "data"),
				new HateoasDef(EmployeeResponse.class, "data")
		);
	}
}
