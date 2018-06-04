package com.mvc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mvc.entity.Permission;
import com.mvc.repository.IPermissionRepository;
import com.mvc.service.IPermissionService;
@Service
public class PermissionService implements IPermissionService{
	@Resource
	private IPermissionRepository permissionRepository;
	@CachePut(value="myCache",key="#userId")// 更新myCache 缓存  
	@Override
	public void savePermission(Permission permission) {
		permissionRepository.savePermission(permission);
	}

	@Override
	public void updatePermission(Permission function) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Permission findPermission(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Permission> findAllPermission(String userid) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 有缓存从缓存中取，没有缓存，取到数据后将，数据加入缓存
	 */
	@Cacheable(value="myCache", key="#userName")  
	@Override
	public List<Permission> findPermissionByName(String userName) {
		return permissionRepository.findPermissionByName(userName);
	}
	@CacheEvict(value="myCache",key="#userName")  
	@Override
	public void deletePermissionCache(String id) {
		System.out.println("清除"+id+"的缓存");
	}

}
