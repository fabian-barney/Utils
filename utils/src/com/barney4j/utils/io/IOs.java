package com.barney4j.utils.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Nullable;
import javax.annotation.WillClose;

import com.google.common.annotations.Beta;
import com.google.common.base.Throwables;

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
@Beta
public final class IOs {
	
	public static void close(@Nullable @WillClose Closeable closeable) throws IOException {
		if(closeable != null) {
			closeable.close();
		}
	}
	
	public static void closeQuietly(@Nullable @WillClose Closeable closeable) {
		try {
			close(closeable);
		}
		catch (IOException e) {
			// ignore
		}
	}
	
	
	public static void closeQuietly(@Nullable @WillClose Closeable closeable, @Nullable @WillClose Closeable... otherCloseables) {
		try {
			close(closeable, Arrays.asList(otherCloseables));
		}
		catch (IOException e) {
			// ignore
		}
	}
	
	
	static void close(@Nullable @WillClose Iterable<? extends Closeable> closeables) throws IOException {
		if(closeables == null) {
			return;
		}
		
		Throwable throwable = null;
		
		try {
			for(Closeable c : closeables) {
				try {
					close(c);
				}
				catch (Throwable t) {
					throwable = mostImportant(throwable, t);
				}
			}
		}
		finally {
			if(throwable != null) {
				Throwables.propagateIfPossible(throwable, IOException.class);
				throw new RuntimeException("This Exception was unexpected. It occurred during closing resources.", throwable);
			}
		}
	}
	
	
	static void close(@Nullable @WillClose Closeable closeable, @Nullable @WillClose Iterable<? extends Closeable> closeables) throws IOException {
		Throwable throwable = null;
		
		try {
			try {
				close(closeable);
			}
			catch (Throwable t) {
				throwable = mostImportant(throwable, t);
			}
			
			try {
				close(closeables);
			}
			catch (Throwable t) {
				throwable = mostImportant(throwable, t);
			}
		}
		finally {
			if(throwable != null) {
				Throwables.propagateIfPossible(throwable, IOException.class);
				throw new RuntimeException("This Exception was unexpected. It occurred during closing resources.", throwable);
			}
		}
	}
	
	static Throwable mostImportant(Throwable t1, Throwable t2) {
		if(t1 instanceof Error) {
			return t1;
		}
		
		return t1 == null || t2 instanceof Error ? t2 : t1;
	}

}
