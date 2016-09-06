package org.think2framework.context.cache;

import org.think2framework.context.ModelFactory;
import org.think2framework.orm.Query;

/**
 * Created by zhoubin on 16/8/15. 模型缓存
 */
public class ModelCache extends AbstractCache {

	public ModelCache(String name, String entity, Integer valid) {
		super(name, entity, valid);
	}

	@Override
	public synchronized void refreshData() {
		// 如果数据为null表示没有初始化过,需要初始化;如果valid有效期大于0表示需要刷新,如果缓存数据已经失效则重新刷新缓存;如果上次缓存的类不是当前需要获取的类则重新获取缓存
		if ((maps.size() == 0) || (valid > 0 && time + valid < System.currentTimeMillis() / 1000)) {
            Query query = ModelFactory.createQuery(entity);
            maps = query.queryForList();
            if (null != clazz){
                mapsToList(clazz);
            }
            time = System.currentTimeMillis() / 1000;
            logger.debug("Refresh cache " + name + " get " + maps.size() + " data");
        }
	}

}
