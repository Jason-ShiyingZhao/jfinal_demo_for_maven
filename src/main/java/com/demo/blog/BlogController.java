package com.demo.blog;

import com.demo.common.model.Blog;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.sql.SQLException;
import java.util.List;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
/**
 * @author Created by zhaosy<a href="mailto:zhaosy@chsi.com.cn">Zhao Shiying</a>
 *
 * @version   Created in 2017/10/16 12:52
 *
 */

@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	
	static BlogService service = new BlogService();
	
	public void index() {
		setAttr("blogPage", service.paginate(getParaToInt(0, 1), 10));
		render("blog.html");
	}
	
	public void add() {
		Record blog = new Record().set("","");
		Db.save("blog",blog);
		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				return true;
			}
		});
		renderTemplate("");
		renderJson();
		renderFile("");
		Db.findById("blog", "id", 1);
		Db.getSqlPara();
        Kv cond = Kv.by("age","22").set("weight","18");
        SqlPara para = Db.getSqlPara("",cond);
        List<Record> blogs = Db.find(para);
		CacheKit.get();
		Cache redis = Redis.use();
		redis.setex("", 123, "");
	}

	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void save() {
		getModel(Blog.class).save();
		getModel(Blog.class,"b");
		redirect("/blog");
		Blog.dao.paginate()
	}
	
	public void edit() {
		setAttr("blog", service.findById(getParaToInt()));
	}
	
	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		service.deleteById(getParaToInt());
		redirect("/blog");
	}
}


