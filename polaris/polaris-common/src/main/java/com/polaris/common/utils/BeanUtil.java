package com.polaris.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.polaris.common.exception.PolarisException;

public final class BeanUtil {

	private final static Logger LOGGER = LogManager.getLogger(BeanUtil.class);

	private BeanUtil() {

	}

	/**
	 * 类字段值复制
	 * 
	 * @throws BeanCopyException
	 * @throws ReflectiveOperationException
	 */
	public static void copyProperties(final Object source, final Object dest) throws PolarisException {
		try {
			BeanUtils.copyProperties(dest, source);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error(e.getMessage());
			throw new PolarisException(e.getMessage(), e);
		}
	}
	
	/**
	 * 深度复制，实参类必须实现Serializable接口
	 * 
	 * @param source
	 * @return
	 */
	public static Object deepClone(Object source) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(source);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
