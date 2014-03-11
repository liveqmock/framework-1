package com.mopon.dao.master.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mopon.entity.member.Member;
import com.mopon.entity.member.Role;
import com.mopon.exception.DatabaseException;

/**
 * 
 * <p>Description: 用户管理模块数据持久层,主要负责对用户表的CRUD代码实现在memberMapper.xml</p>
 * @date 2013年9月17日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface IMemberDao {
	
	
	/**
	 * 
	 * 方法用途: 根据用户编号查询用户<br>
	 * @param member
	 * @return
	 * @throws DataAccessException
	 */
	public Member queryMemberById(Member member) throws DataAccessException;

	
	/**
	 * 
	 * 方法用途: 查询用户信息<br>
	 * @param member
	 * @return
	 * @throws DataAccessException
	 */
	public Member queryMember(Member member) throws DataAccessException;
	
	/**
	 * 
	 * 方法用途:验证用户是否已注册<br>
	 * @param member
	 * @return
	 * @throws DataAccessException
	 */
	public Member verifyMember(Member member) throws DataAccessException;

	
	/**
	 * 
	 * 方法用途: 保存用户信息<br>
	 * @param entity
	 * @throws DatabaseException
	 */
	public void save(Member entity) throws DataAccessException;
	
	/**
	 * 
	 * 方法用途: 删除用户信息<br>
	 * @param entity
	 * @throws DatabaseException
	 */
	public void remove(@Param("ids")String[] ids) throws DataAccessException;
	
	
	
	/**
	 * 
	 * 方法用途: 更新用户信息<br>
	 * @param entity
	 * @throws DatabaseException
	 */
	public void update(Member entity) throws DataAccessException;

	
	/**
	 * 
	 * 方法用途: 查询所有用户<br>
	 * @return
	 * @throws DatabaseException
	 */
	public List<Member> queryAll() throws DataAccessException;

	/**
	 * 
	 * 方法用途: 用户信息分页查询<br>
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws DatabaseException
	 */
	public List<Member> queryMemberForList(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize,@Param("member")Member member,@Param("roles")List<Role> roles,@Param("groupId") Integer groupId) throws DataAccessException;
	
	/**
	 * 
	 * 方法用途: 查询总条数<br>
	 * @param map
	 * @return
	 * @throws DatabaseException
	 */
	public int queryCount(@Param("member")Member member,@Param("roles")List<Role> roles,@Param("groupId")Integer groupId)throws DataAccessException;
	
	
	/**
	 * 修改用户密码
	 * @param member
	 * @return
	 */
	public int changePwd(Member member)throws DataAccessException;
	
	/**
	 * 更新用户登录次数
	 * @return
	 * @throws DataAccessException
	 */
	public int updateLoginNum(Member member)throws DataAccessException;
	
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public Member findMemberByName(String userName)throws DataAccessException;
}
