package com.demo.common.model;

import com.demo.common.model.base.BaseBlog;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * Blog model.
 * 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
/**
 * @author Created by zhaosy<a href="mailto:zhaosy@chsi.com.cn">Zhao Shiying</a>
 *
 * @version   Created in 2017/10/16 12:53
 * 
 */

@SuppressWarnings("serial")
public class Blog extends BaseBlog<Blog> {
	public static final Blog dao = new Blog().dao();
}
