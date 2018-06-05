package com.mvc.dao;

import com.core.model.RequestModel;
import com.core.model.ResponseModel;
import com.core.repository.BaseDaoSupport;
import com.core.util.ListParamsUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 获取订单信息-1908 根据支付订单号获取支付订单明细 -暂时没做接口
 * @author daihh
 * 2016-12-28
 */
@Repository
public class GetAlipayVoucherInfoDao extends BaseDaoSupport {

	public ResponseModel execute(RequestModel requestModel){
		String sql = "SELECT * FROM permission where userName = ? ";
		List<Object> params = ListParamsUtils.setParams(requestModel.getReqStr("username"));
		return this.getJdbcModelTemplate().queryForListMap(sql, params);
	}
	
}
