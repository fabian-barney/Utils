package com.barney4j.utils.io;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

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
public class MultiOutputStream extends FilterOutputStream {
	
	protected final ImmutableList<OutputStream> branches;

	protected MultiOutputStream(@Nonnull OutputStream main, @Nonnull OutputStream... branches) {
		super(main);
		this.branches = ImmutableList.copyOf(branches);
	}
	
	protected MultiOutputStream(@Nonnull OutputStream main, @Nonnull Collection<? extends OutputStream> branches) {
		super(main);
		this.branches = ImmutableList.copyOf(branches);
	}

	@Nonnull
	@CheckReturnValue
	public static OutputStream create(@Nonnull OutputStream main, @Nullable OutputStream branch) {
		checkNotNull(main);
		return branch == null ? main : new MultiOutputStream(main, branch);
	}
	
	
	@Nonnull
	@CheckReturnValue
	public static OutputStream create(@Nonnull OutputStream main, @Nullable OutputStream branch1, @Nullable OutputStream branch2) {
		checkNotNull(main);
		
		if(branch1 == null || branch2 == null) {
			return branch1 == null ? create(main, branch2) : create(main, branch1);
		}
		
		return new MultiOutputStream(main, branch1, branch2);
	}
	
	/**
	 * Returns a builder to build a {@link MultiOutputStream}.
	 * Use this builder when you need a {@link MultiOutputStream} with more than two branches.
	 * Otherwise {@link #create(OutputStream, OutputStream)} or {@link #create(OutputStream, OutputStream, OutputStream)} are prefered.
	 * 
	 * @return the {@link Builder} to build a {@link MultiOutputStream}
	 */
	@Nonnull
	@CheckReturnValue
	public static Builder builder() {
		return new Builder();
	}
	


    /**
     * Write the bytes to all streams.
     * @param b the bytes to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void write(@Nonnull byte[] b) throws IOException {
    	super.write(b);
    	for(OutputStream branch : this.branches) {
    		branch.write(b);
    	}
    }

    /**
     * Write the specified bytes to all streams.
     * @param b the bytes to write
     * @param off The start offset
     * @param len The number of bytes to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void write(@Nonnull byte[] b, int off, int len) throws IOException {
    	super.write(b, off, len);
    	for(OutputStream branch : this.branches) {
    		branch.write(b, off, len);
    	}
    }

    /**
     * Write a byte to all streams.
     * @param b the byte to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void write(int b) throws IOException {
    	super.write(b);
    	for(OutputStream branch : this.branches) {
    		branch.write(b);
    	}
    }

    /**
     * Flushes all streams.
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void flush() throws IOException {
    	super.flush();
    	for(OutputStream branch : this.branches) {
    		branch.flush();
    	}
    }

    /**
     * Closes all streams.
     * If a {@link Throwable} occurs then the method catches it and goes on closing the other streams.
     * At the end it throws the most important Throwable occurred, if any.
     * A Throwable occurred earlier is more important than one occurred later except the newer is an {@link Error} and the older is not.
     * 
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
    	Throwable throwable = null;
    	
    	try {
    		super.close();
    	}
    	catch (Throwable t) {
    		throwable = IOs.mostImportant(throwable, t);
    	}
    	
    	try {
    		IOs.close(this.branches);
    	}
    	catch (Throwable t) {
    		throwable = IOs.mostImportant(throwable, t);
    	}
    	
    	if(throwable != null) {
    		Throwables.propagateIfPossible(throwable, IOException.class);
			throw new RuntimeException("This Exception was unexpected. It occurred during closing resources.", throwable);
    	}
    }
	
	
	
	public static final class Builder {
		
		private final List<OutputStream> branches = new ArrayList<>();
				
		
		Builder() {
			// restricted access
		}
		
		public void addBranch(@Nullable OutputStream branch) {
			if(branch != null) {
				this.branches.add(branch);
			}
		}
		
		@Nonnull
		@CheckReturnValue
		public OutputStream build(@Nonnull OutputStream main) {
			checkNotNull(main);
			return this.branches.isEmpty() ? main : new MultiOutputStream(main, this.branches);
		}
		
	}
	

}
