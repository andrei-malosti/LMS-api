package com.lms.api.infra.multitenancy;

import java.util.UUID;

public class OrganizationContext {
	
	private static final ThreadLocal<UUID> currentOrganization = new ThreadLocal<>();
	
	public static void setOrganizationId(UUID tenantId) {
		currentOrganization.set(tenantId);
	}
	
	public static UUID getOrganizationId() {
		return currentOrganization.get();
	}
	
	public static void clear() {
		currentOrganization.remove();
	}

}
