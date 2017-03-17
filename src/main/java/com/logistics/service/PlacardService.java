package com.logistics.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.persistence.manager.PlacardManager;
import com.logistics.persistence.model.Placard;

import com.logistics.common.utils.ValidateUtils;
import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Service
public class PlacardService {
	@Autowired
	private PlacardManager placardManager;

	/**
	 * 添加通知
	 * @param title
	 * @param content
	 * @return
	 */
	@Transactional
	public Res addPlacard(String title, String content) {
		Res res = new Res();
		try {
			if(ValidateUtils.isNull(content)){
				throw new ServiceException("通知内容不能为空");
			}
			Placard placard = new Placard();
			placard.setTitle(title);
			placard.setContent(content);
			placard.setCreateTime(new Date());
			placardManager.insert(placard);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res getLastedPlacard() {
		Res res = new Res();
		try {
			List<Placard> placardList = placardManager.getPlacardList();
			if(placardList!=null&&placardList.size()>0){
				res.addRespose("lastedPlacard", placardList.get(0));
			}else{
				res.addRespose("lastedPlacard", null);
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}
}
