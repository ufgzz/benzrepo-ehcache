package com.mvc.service.impl;

import com.core.model.RequestModel;
import com.core.model.ResponseModel;
import com.mvc.dao.GetAlipayVoucherInfoDao;
import com.mvc.entity.Permission;
import com.mvc.repository.IPermissionRepository;
import com.mvc.service.IPermissionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService implements IPermissionService{
	@Resource
	private IPermissionRepository permissionRepository;
	@Resource
	private GetAlipayVoucherInfoDao getAlipayVoucherInfoDao;

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
		List<Permission> resultList = new ArrayList<>();
		RequestModel requestModel = new RequestModel();
		requestModel.putReq("username", userName);
		ResponseModel responseModel =  getAlipayVoucherInfoDao.execute(requestModel);
		List<Map<String, Object>> responseParamsList = responseModel.getResponseParamsList();
		for (Map<String, Object> map : responseParamsList) {
			Permission p = new Permission();
			p.setId(String.valueOf(map.get("id")));
			p.setUserName(String.valueOf(map.get("username")));
			p.setUrl(String.valueOf(map.get("url")));
			resultList.add(p);
		}
		return resultList;
		//return permissionRepository.findPermissionByName(userName);
	}
	@CacheEvict(value="myCache",key="#userName")
	@Override
	public void deletePermissionCache(String userName) {
		System.out.println("清除"+userName+"的缓存");
	}

}
