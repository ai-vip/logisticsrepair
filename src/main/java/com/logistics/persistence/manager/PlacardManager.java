package com.logistics.persistence.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.logistics.persistence.dao.PlacardDao;
import com.logistics.persistence.model.Placard;
import com.logistics.persistence.model.PlacardExample;
@Component
public class PlacardManager extends BaseManager<PlacardDao> {
	@Autowired
	private PlacardDao placardDao;

	@Override
	public PlacardDao getDao() {
		return placardDao;
	}

	public int insert(Placard placard) {
		return placardDao.getMapper().insert(placard);
	}

	public List<Placard> getPlacardList() {
		PlacardExample example = new PlacardExample();
		example.setOrderByClause("create_time desc");
		return placardDao.getMapper().selectByExample(example);
	}

}
