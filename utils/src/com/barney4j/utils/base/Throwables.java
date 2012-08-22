package com.barney4j.utils.base;

/*
 * Copyright 2012 Fabian Barney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @author Fabian Barney
 *
 */
public final class Throwables {
	

	public static Throwable mostImportant(Throwable t1, Throwable t2) {
		if(t1 instanceof Error) {
			return t1;
		}
		
		return t1 == null || t2 instanceof Error ? t2 : t1;
	}
	
	

}
