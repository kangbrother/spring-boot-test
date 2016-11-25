package com.lk.util;

import com.lk.mybatis.DatabaseContextHolder;
import com.lk.mybatis.DatabaseType;


public class ChangeDatabase {
	
	public static void valueOf(Integer value) {    
        switch (value) {
	        case 604:
	            DatabaseContextHolder.setDatabaseType(DatabaseType.beijing);
	            break;
	        case 605:
	            DatabaseContextHolder.setDatabaseType(DatabaseType.shanghai);
	            break;
	        case 606:
	            DatabaseContextHolder.setDatabaseType(DatabaseType.guangzhou);
	            break;
	        case 607:
	            DatabaseContextHolder.setDatabaseType(DatabaseType.shenzhen);
	            break;
	        case 626:
	            DatabaseContextHolder.setDatabaseType(DatabaseType.hangzhou);
	            break;
	        default:
	            DatabaseContextHolder.setDatabaseType(DatabaseType.main);
        }
	}
	
	
}
