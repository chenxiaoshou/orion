/**
 * <p>
 * 该包下放置controller层与service层之间传输数据的DTO类
 * </p>
 * <p>
 * 每个类的数据尽量保持扁平化，方便字段值通过反射批量复制
 * </p>
 * <p>
 * 尽量避免复杂类的相互嵌套，方便序列化和反序列化
 * </p>
 * <p>
 * 一定要继承BaseObject类，方便输出
 * </p>
 */
package com.polaris.manage.service.dto;
