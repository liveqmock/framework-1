package com.mopon.service.member;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

import com.mopon.entity.member.Member;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.MemberException;

/**
 * 
 * <p>Description:用户管理模块业务逻辑处理 </p>
 * @date 2013年9月17日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IMemberService {
	
	
	/**
	 * 
	 * 方法用途:获取分布式缓存中的用户信息(包括该用户角色信息和组信息) <br>
	 * @param request 用于获取缓存对象
	 * @return
	 */
	public Member findCachedMember(HttpServletRequest request);
	
	/**
	 * 
	 * 方法用途: 根据用户编号查询用户信息<br>
	 * @param member
	 * @return
	 */
	public Member queryMemberById(Member member);
	
	/**
	 * 
	 * 方法用途: 查询用户信息<br>
	 * @param member
	 * @return
	 */
	public Member queryMemberByName(Member member);
	
	/**
	 * 
	 * 方法用途:用来验证用户是否已经注册<br>
	 * @param member
	 * @return
	 */
	public Member verifyMember(Member member);
	

	/**
	 * 
	 * 方法用途: 新增用户<br>
	 * @param member 用户实体
	 * @throws MemberException
	 * @throws DatabaseException
	 */
	public void addMember(Member member) throws MemberException, DataAccessException;
	
	/**
	 * 
	 * 方法用途: 修改用户信息<br>
	 * @param member
	 * @throws MemberException
	 * @throws DatabaseException
	 */
	public void modifiMember(Member member) throws MemberException, DataAccessException;
	
	/**
	 * 
	 * 方法用途: 删除用户信息<br>
	 * @param member
	 * @throws MemberException
	 * @throws DatabaseException
	 */
	public void removeMember(String[] ids) throws MemberException, DataAccessException;
	
	/**
	 * 
	 * 方法用途: 获取用户信息<br>
	 * @return  Member   用户实体
	 * @param   pageNum  第几页
	 * @param   pageSize 每页显示条数
	 * @throws MemberException
	 * @throws DatabaseException
	 */
	public PageBean<Member> queryMemberForList(int pageNum,int pageSize,Member memeber,List<Role> roles) throws MemberException, DataAccessException;
	
	
	/**
	 * 修改用户密码
	 * @param member
	 */
	public void changePwd(Member member)throws MemberException, DataAccessException;
	
	/**
	 * 更新用户登录系统次数
	 */
	public void updateLoginNum(Member member)throws MemberException,DataAccessException;
	
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public Member queryMemberByName(String userName);

}
