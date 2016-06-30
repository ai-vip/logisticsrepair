package com.logistics.persistence.dao;

/**
 * 
 * @author huhaichao
 *
 * @param <T>实体
 * @param <K>key
 * @param <M>mapper
 * @param <E>Example
 */
public abstract class BaseDAO<T, K, M, E>{
	public abstract M getMapper();
}