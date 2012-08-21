package com.barney4j.utils.base;

public final class Throwables {
	

	public static Throwable mostImportant(Throwable t1, Throwable t2) {
		if(t1 instanceof Error) {
			return t1;
		}
		
		return t1 == null || t2 instanceof Error ? t2 : t1;
	}
	
	

}
