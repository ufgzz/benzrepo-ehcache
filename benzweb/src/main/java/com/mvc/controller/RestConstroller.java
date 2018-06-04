package com.mvc.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mvc.entity.Permission;
import com.mvc.service.IPermissionService;
import com.mvc.vo.UserVo;

@Controller
public class RestConstroller {
	@Resource
	private IPermissionService permissionService;

	@RequestMapping(value = "/index")
	public String tologin() {
		return "/welcome";
	}

	@ResponseBody
	@RequestMapping(value = "/getUserPermission")
	public UserVo authorize() {
		List<Permission> list=permissionService.findPermissionByName("15700000000");
		UserVo userVo = new UserVo();
		userVo.setCode("1");
		userVo.setMsg("成功");
		userVo.setData(list);
		return userVo;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteUserPermissionCache")
	public UserVo deleteUserPermissionCache() {
		permissionService.deletePermissionCache("15700000000");
		UserVo userVo = new UserVo();
		userVo.setCode("1");
		userVo.setMsg("清除15700000000的缓存成功");
		return userVo;
	}

}
