package com.sql.project.startup.util;

import java.util.UUID;

/**
 * @ClassName:     UUIDGenerator.java
 * @Description:   UUID生成器
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-06 13:17:37 
 */
public class UUIDGenerator {

	public synchronized static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
