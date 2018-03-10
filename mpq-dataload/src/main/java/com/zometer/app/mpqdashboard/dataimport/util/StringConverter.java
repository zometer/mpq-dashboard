package com.zometer.app.mpqdashboard.dataimport.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public final class StringConverter {

	@SuppressWarnings("unchecked")
	public static <T> T convert(String input, Class<T> type) {
		if (type.isAssignableFrom(String.class)) {
		    return (T) input;
		}

		if ( ! Number.class.isAssignableFrom(type) ) {
			throw new IllegalArgumentException(type.getName() + " cannot be converted.");
		}

		Constructor<T> constructor = findStringConstructor(type);
		if (constructor == null) {
			throw new IllegalArgumentException("Could not find single argument String constructor for type: " + type.getName());
		}

		try {
			return constructor.newInstance(input);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> Constructor<T> findStringConstructor(Class<T> type) {
		Optional<Constructor<?>> optional = Arrays.stream(type.getConstructors())
		    .filter( c -> c.getParameterTypes().length == 1 && c.getParameterTypes()[0].equals(String.class))
		    .findFirst()
		;

		@SuppressWarnings("unchecked")
		Constructor<T> constructor = (Constructor<T>) optional.orElse(null);
		return constructor;
	}

}
