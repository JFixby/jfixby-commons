
package com.jfixby.scarabei.api.collisions;

public interface CollisionRelations {

	void setPolicy (COLLISION_RELATION relation, CollisionCategory category);

	void setValues (CollisionRelations other);

	long getMaskBits ();

}
