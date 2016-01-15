package com.jfixby.cmns.api.math;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.collections.EditableCollection;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.Triangle;

public class SimpleTriangulator {

	static private ComponentInstaller<SimpleTriangulatorComponent> componentInstaller = new ComponentInstaller<SimpleTriangulatorComponent>(
			"SimpleTriangulator");

	public static final void installComponent(SimpleTriangulatorComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final SimpleTriangulatorComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final SimpleTriangulatorComponent component() {
		return componentInstaller.getComponent();
	}

	public static List<Triangle> triangulate(EditableCollection<Float2> vertices_list) {
		return invoke().triangulate(vertices_list);
	}

}
