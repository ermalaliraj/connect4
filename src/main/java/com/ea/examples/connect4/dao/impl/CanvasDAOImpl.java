package com.ea.examples.connect4.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ea.examples.canvas.exception.CanvasException;
import com.ea.examples.connect4.api.CanvasDTO;
import com.ea.examples.connect4.dao.CanvasDAO;
import com.ea.examples.connect4.dao.GenericDAO;
import com.ea.examples.connect4.entity.CanvasEntity;

@Repository
public class CanvasDAOImpl extends GenericDAO implements CanvasDAO {
	
	protected static final transient Log logger = LogFactory.getLog(CanvasDAOImpl.class);
	
	public void newCanvas(CanvasDTO dto) throws CanvasException {
		try {
			CanvasEntity e = new CanvasEntity();
			e.setId(dto.getId());
			e.setUserId(dto.getUserId());
			e.setWidth(dto.getWidth());
			e.setHeight(dto.getHeight());
			e.setMatrix(dto.getMatrix());
			entityManager.persist(e);
			dto.setId(e.getId()); //get the id of the new record
		} catch(Exception e){
			logger.error("General exception while inserting new canvas: ", e);
			throw new CanvasException("General Exception in newCanvas", e);
		}
	}
	
	public void updateCanvas(CanvasDTO dto) throws CanvasException {
		try{
			CanvasEntity e = new CanvasEntity();
			e.setId(dto.getId());
			e.setUserId(dto.getUserId());
			e.setWidth(dto.getWidth());
			e.setHeight(dto.getHeight());
			e.setMatrix(dto.getMatrix());
			entityManager.merge(e);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new CanvasException("Exception in udateCanvas", e);
		}
	}
	
	public void deleteCanvasByUserId(String userId) throws CanvasException {
		try {
			String sql = QueryCatalog.getQuery_deleteCanvasByUserId(userId);
			Query q = entityManager.createQuery(sql);
			q = q.setParameter("userId", userId);
			q.executeUpdate();
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new CanvasException("Exception in deleteCanvasByUserId", e);
		}
	}
	
	public CanvasDTO getCanvasById(Integer id) throws CanvasException {
		try{
			CanvasEntity e = new CanvasEntity();
			e.setId(id);		
			CanvasEntity eDB = entityManager.find(CanvasEntity.class, e.getId());	
			
			CanvasDTO dto = new CanvasDTO();
			if(eDB != null){
				dto.setId(eDB.getId());
				dto.setUserId(eDB.getUserId());
				dto.setWidth(eDB.getWidth());
				dto.setHeight(eDB.getHeight());
				dto.setMatrix(eDB.getMatrix());
			}
			return dto;
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new CanvasException("Exception in getCanvasById", e);
		}
	}
	
	public CanvasDTO getCanvasByUserId(String userId) throws CanvasException {
		CanvasDTO dto = null;
		
		try {
			String sql = QueryCatalog.getQuery_getCanvasByUserId(userId);
			Query q = entityManager.createQuery(sql);
			q = q.setParameter("userId", userId);
			CanvasEntity eDB = (CanvasEntity) q.getSingleResult();
			
			dto = new CanvasDTO();
			dto.setId(eDB.getId());
			dto.setUserId(eDB.getUserId());
			dto.setWidth(eDB.getWidth());
			dto.setHeight(eDB.getHeight());
			dto.setMatrix(eDB.getMatrix());
		} catch (NoResultException e) {
			logger.warn("No Canvas found for userId:"+ userId);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new CanvasException("Exception in getCanvasByUserId", e);
		}
		
		return dto;
	}

	public List<CanvasDTO> getAllCanvas() throws CanvasException {
		try{ 
			String sql = QueryCatalog.getQuery_getAllCanvas();
			@SuppressWarnings("unchecked")
			List<CanvasEntity> list = entityManager.createQuery(sql).getResultList();
			
			List<CanvasDTO> res = new ArrayList<CanvasDTO>();
			CanvasDTO dto = null;
			for (CanvasEntity e : list) {
				dto = new CanvasDTO();
				dto.setId(e.getId());
				dto.setUserId(e.getUserId());
				dto.setWidth(e.getWidth());
				dto.setHeight(e.getHeight());
				dto.setMatrix(e.getMatrix());
				res.add(dto);
			}
				
			return res;
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new CanvasException("Exception in getAllCanvas", e);
		}
	}
	
}
