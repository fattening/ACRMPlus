package cn.hlj.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.hlj.crm.entity.SalesChance;

public interface SalesChanceMapper {

	// ------------冗余方法，用getTotalElementsWithConditions,getContentWithConditions替代---------------------
	// 获取总的记录数
	@Select("SELECT count(id) FROM sales_chances")
	long getTotalElements();

	// 获取当前页面的内容
	@Select("SELECT * FROM "
			+ "(SELECT rownum rn,id,contact,contact_tel,create_date,cust_name,title FROM sales_chances) t "
			+ "WHERE t.rn>=#{firstIndex} AND t.rn<#{endIndex}")
	List<SalesChance> getContent(@Param("firstIndex") int fromIndex, @Param("endIndex") int endIndex);
	// ------------冗余方法，用getTotalElementsWithConditions,getContentWithConditions替代----------------------

	@Insert("INSERT INTO sales_chances(id,source,cust_name,rate,title,contact,contact_tel,description,created_user_id,create_date,status) "
			+ "VALUES(crm_seq.nextval,#{source},#{custName},#{rate},#{title},#{contact},#{contactTel},#{description},#{createBy.id},#{createDate},1)")
	void save(SalesChance salesChance);

	@Delete("DELETE FROM sales_chances WHERE id=#{id}")
	void delete(@Param("id") Long id);

	@Select("SELECT s.id,source,cust_name,rate,title,contact,contact_tel,s.description,"
			+ "u.name as \"createBy.name\",r.name as \"createBy.role.name\",create_date,designee_id as \"designee.id\" "
			+ "FROM sales_chances s LEFT OUTER JOIN users u ON s.created_user_id=u.id "
			+ "LEFT OUTER JOIN roles r ON u.role_id=r.id " + "WHERE s.id=#{id}")
	SalesChance get(@Param("id") Long id);

	// @Update("UPDATE sales_chances "
	// + "SET
	// source=#{source},cust_name=#{custName},rate=#{rate},title=#{title},contact=#{contact},contact_tel=#{contactTel},description=#{description}
	// "
	// + "WHERE id=#{id}")
	void update(SalesChance salesChance);

	// ------------------------------------------带查询条件------------------------------------------------
	long getTotalElementsWithConditions(Map<String, Object> params);

	List<SalesChance> getContentWithConditions(Map<String, Object> params);
	// ------------------------------------------带查询条件------------------------------------------------

	SalesChance getWithPlans(@Param("id") Long id);
}
