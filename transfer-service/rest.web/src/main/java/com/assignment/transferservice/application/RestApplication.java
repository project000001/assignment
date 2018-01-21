package com.assignment.transferservice.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.assignment.transferservice.controller.TransferServiceEndpoint;

@ApplicationPath("rest")
public class RestApplication extends Application {

	private Set<Object> singletons = new HashSet<>();
	private Set<Class<?>> classes = new HashSet<>();

	@Override
	public Set<Class<?>> getClasses() {
		classes.add(TransferServiceEndpoint.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
