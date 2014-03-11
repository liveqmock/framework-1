package com.mopon.service.impl.member;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.member.IMemberDao;
import com.mopon.entity.member.Group;
import com.mopon.entity.member.Member;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.MemberException;
import com.mopon.service.impl.sys.BaseServiceImpl;
import com.mopon.service.member.IMemberService;
import com.mopon.util.Session;

@Service("memberService")
public class MemberServiceImpl extends BaseServiceImpl implements IMemberService {
	
	@Autowired
	private IMemberDao memberDao;
	

	@Override
	@Transactional
	public void addMember(Member member) throws MemberException, DataAccessException {
		try{
			member.setType(0);//管理员
			this.memberDao.save(member);
		}catch(DataAccessException e){
			throw e;
		}
	}

	@Override
	@Transactional
	public void modifiMember(Member member) throws MemberException, DataAccessException {
		try{
			member.setType(0);//管理员
			this.memberDao.update(member);
		}catch(DataAccessException e){
			throw e;
		}
	}

	@Override
	@Transactional
	public void removeMember(String[] ids) throws MemberException, DataAccessException {
		try{
			this.memberDao.remove(ids);
		}catch(DataAccessException e){
			throw e;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public PageBean<Member> queryMemberForList(int pageNum,int pageSize,Member member,List<Role> roles) throws MemberException, DataAccessException {
		PageBean<Member> pageBean = new PageBean<Member>();
		try{
			Integer groupId=null;
			Group group = member.getGroup();
			if(group!=null){
				groupId= group.getGroupId();
			}
			pageBean.setRecordCount(this.memberDao.queryCount(member,roles,groupId));
			
			int start =(pageNum-1) * pageSize;
			pageBean.setPageSize(pageSize);
			
			pageBean.setDataList(this.memberDao.queryMemberForList(start, pageSize,member,roles,groupId));//设置分页数据
		}catch(DataAccessException e){
			throw e;
		}
		return pageBean;
	}

	@Override
	public Member queryMemberByName(Member member) {
		Member entity = null;
		try{
			entity = this.memberDao.queryMember(member);
		}catch(DataAccessException e){
			throw e;
		}
		return entity;
	}
	
	@Override
	public Member verifyMember(Member member) {
		Member entity = null;
		try{
			entity = this.memberDao.verifyMember(member);
		}catch(DataAccessException e){
			throw e;
		}
		return entity;
	}
	
	

	@Override
	public Member queryMemberById(Member member) {
		Member entity = null;
		try{
			entity = this.memberDao.queryMemberById(member);
		}catch(DataAccessException e){
			throw e;
		}
		return entity;
	}

	@Override
	public Member findCachedMember(HttpServletRequest request) {
		
		Member member = null;
		try {
			Object uid = cookieManager.readCookie("uid", request);
			if(uid ==null || "".equals(uid)){
				Session session  = this.sessionManager.getSession(request.getSession().getId());
				uid = session.getAttribute("userId");
			}
			member = (Member)cachedDao.get(request.getContextPath()+uid);
		} catch (Exception e) {
			loggerUtil.error("获取缓存异常"+e.getMessage());
		}
		return member;
	}

	@Override
	public void changePwd(Member member) {
		try{
			this.memberDao.changePwd(member);
		}catch(Exception e){
			loggerUtil.error("修改密码异常:"+e.getMessage());
		}
	}

	@Override
	public void updateLoginNum(Member member) throws MemberException, DataAccessException {
		try{
			this.memberDao.updateLoginNum(member);
		}catch(Exception e){
			loggerUtil.error("更改用户登录次数异常:"+e.getMessage());
		}
	}

	@Override
	public Member queryMemberByName(String userName) {
		Member member = null;
		try{
			member = this.memberDao.findMemberByName(userName);
		}catch(Exception e){
			loggerUtil.error("获取用户信息异常:"+e.getMessage());
		}
		return member;
	}

	
	
}
