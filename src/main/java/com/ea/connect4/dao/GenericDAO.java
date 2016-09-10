package com.ea.connect4.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericDAO  {

	protected EntityManager entityManager;

	@PersistenceContext(unitName="canvasEM")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}