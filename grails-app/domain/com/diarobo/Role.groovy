package com.diarobo

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Role implements Serializable {

	private static final long serialVersionUID = 1

	String authority

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
	enum AvailableRoles {
		SUPER_ADMIN("ROLE_SUPER_ADMIN"), ADMIN("ROLE_ADMIN"), CUSTOMER_CARE("ROLE_CUSTOMER_CARE"), CAREGIVER("ROLE_CAREGIVER"), PATIENT("ROLE_PATIENT"), DOCTOR("ROLE_DOCTOR")

		String roleId

		private AvailableRoles(String id) {
			this.roleId = id
		}

		String value() {
			roleId
		}
	}
}
