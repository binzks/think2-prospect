package org.think2framework.context.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import org.think2framework.common.JsonUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/8/15. json文件缓存
 */
public class JsonFileCache extends AbstractCache {

	public JsonFileCache(String name, String entity, Integer valid) {
		super(name, entity,valid);
	}

	@Override
	public synchronized void refreshData() {
		// 如果数据为null表示没有初始化过,需要初始化;如果valid有效期大于0表示需要刷新,如果缓存数据已经失效则重新刷新缓存
		if ((maps.size() == 0) || (valid > 0 && time + valid < System.currentTimeMillis() / 1000)) {
            maps = JsonUtils.readFile(entity, new TypeReference<List<Map<String, Object>>>() {
            });
            if (null != clazz){
                mapsToList(clazz);
            }
            time = System.currentTimeMillis() / 1000;
            logger.debug("Refresh cache " + name + " get " + maps.size() + " data");
        }
	}

}
