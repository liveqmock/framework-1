package com.mopon.service.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.service.impl.sys.BaseServiceImpl;
import com.mopon.service.member.IGroupService;
import com.mopon.dao.master.member.IGroupDao;
import com.mopon.entity.member.Group;
import com.mopon.entity.member.GroupRole;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.GroupException;


@Service("groupService")
public class GroupServiceImpl extends BaseServiceImpl implements IGroupService {

	@Autowired
	private IGroupDao groupDao;

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional
	public void addGroup(Group group, int[] roids) throws SQLException, GroupException {
		groupDao.save(group);
		List<GroupRole> groupRoles = new ArrayList<GroupRole>(roids.length);
		for(int roid : roids) {
			GroupRole groupRole = new GroupRole();
			groupRole.setGroupId(group.getGroupId());
			groupRole.setRoleId(roid);
			groupRoles.add(groupRole);
		}
		groupDao.saveGroupRole(groupRoles);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional
	public void removeGroup(Group group) throws Exception {
		//查询所有子分组
		List<Group> subList = queryGroupByParentGroupId(group.getGroupId());
		subList.add(group);//把自己加上	
		for(Group g:subList){
			groupDao.deleteGroupRoleByGroupId(g.getGroupId());
			groupDao.updateMemberGroup(g.getGroupId());
			groupDao.remove(g);
		}
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional
	public void editGroup(Group group, int[] roleIds,int[] delRoleIds) throws Exception {
/*		//先删除所有子分组中 该分组不拥有的角色
		List<Group> subList =queryGroupByParentGroupId(group.getGroupId());
		if(subList!=null && subList.size()>0){
			for(Group g : subList){
				if(delRoleIds!=null && delRoleIds.length>0){
					for(int i=0;i<delRoleIds.length;i++){
						groupDao.deleteGroupRole(g.getGroupId(),delRoleIds[i]);
					}
				}
			}
		}*/
		//删除分组下该角色拥有的所有角色，不能删除该组中该用户不拥有的角色
		//1)先删除不要添加的角色
		if(delRoleIds!=null && delRoleIds.length>0){
			for(int i=0;i<delRoleIds.length;i++){
				groupDao.deleteGroupRole(group.getGroupId(), delRoleIds[i]);
			}
		}
		//2)再删除要添加的角色，因为不清楚要添加的角色，数据库中是否存在，所以先删除，再添加
		if(roleIds!=null && roleIds.length>0){
			for(int i=0;i<roleIds.length;i++){
				groupDao.deleteGroupRole(group.getGroupId(), roleIds[i]);
			}
		}
		
		//保存分组角色
		if(roleIds !=null && roleIds.length > 0) {
			List<GroupRole> groupRoles = new ArrayList<GroupRole>(roleIds.length);
			for(int roleId : roleIds) {
				GroupRole groupRole = new GroupRole();
				groupRole.setGroupId(group.getGroupId());
				groupRole.setRoleId(roleId);
				groupRoles.add(groupRole);
			}
			groupDao.saveGroupRole(groupRoles);
		}
		//更新分组
		groupDao.update(group);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public Group getGroupDetail(Group group) throws SQLException, GroupException {
		group = groupDao.getGroupDetailById(group);
		return group;
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public PageBean<Group> getGroupForList(Group group, int pageNo, int pageSize, int sort,List<Group> supGroupList) throws SQLException, GroupException {
		//创建分页对象
		PageBean<Group> pageBean = new PageBean<Group>();
		//设置当前页
		pageBean.setCurrentPage(pageNo);
		if(pageNo > 0) {
			pageNo = pageNo - 1;
		}
		//得到结果总数
		int count = groupDao.queryGroupForCount(group,supGroupList);
		//设置开始位置
		int start = pageNo * pageSize;
		//返回列表
		List<Group> groupList = groupDao.queryGroupForPages(group, start, pageSize, sort,supGroupList);
		//放入分页容器
		pageBean.setDataList(groupList);
		//设置页大小
		pageBean.setPageSize(pageSize);
		//总页数
		pageBean.setRecordCount(count);
		
		return pageBean;
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public Group queryGroupForObject(Group group) throws SQLException, GroupException {
		return groupDao.queryGroupForObject(group);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param groupRole
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional
	public void addGroupRole(List<GroupRole> groupRoles) throws SQLException, GroupException {
		groupDao.saveGroupRole(groupRoles);
	}
	
	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public Group queryGroupMemberForList(Group group) throws SQLException, GroupException {
		return groupDao.queryGroupMemberForList(group);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param group
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public Group queryGroupRoleForList(Group group) throws SQLException, GroupException {
		return groupDao.queryGroupRoleForList(group);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Group> queryGroupForList(Group group) throws SQLException, GroupException {
		return groupDao.queryGroupForList(group);
	}
	
	
	/** 
	 * 方法用途: 返回分组包含的子角色列表<br>
	 * 实现步骤: <br>
	 * @param group
	 * @return
	 * @throws SQLException
	 * @throws GroupException   
	 */
	@Override
	@Transactional(readOnly = true)
	public Group queryGroupSubordinateRole(Group group,Integer roleId) throws DataAccessException {
		return groupDao.queryGroupSubordinateRole(group, roleId);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Group> queryGroupByParentGroupId(int parentGroupId)throws Exception {
		Group query = new Group();
		query.setParentGroupId(parentGroupId);
		//查询子分组
		List<Group> groups = groupDao.queryGroupForList(query);
		List<Group> allList = new ArrayList<Group>();
		allList.addAll(groups);
		return getAllSubGroup(groups, allList);
	}

	/**
	 * 递归方法，查询所有子分组
	 * @param parentList
	 * @param allList
	 * @return
	 */
	public List<Group> getAllSubGroup(List<Group> subList,List<Group> allList)throws Exception{
		if(subList==null || subList.size()==0)return allList;
		//子类分组
		List<Group> newSubList = new ArrayList<Group>();
		for(int i=0;i<subList.size();i++){
			Group query = new Group();
			query.setParentGroupId(subList.get(i).getGroupId());
			List<Group> tempList=groupDao.queryGroupForList(query);
			newSubList.addAll(tempList);//添加子类分组
			allList.addAll(tempList);
		}
		if(newSubList.size()>0)
			return getAllSubGroup(newSubList, allList);
		
		return allList;
	}

	@Override
	public Map<String, Object> getGroupMap(List<Group> list,int groupId) throws Exception {
		Map<String,Object> groupMap = new HashMap<String,Object>();
		if(groupId==0){//查询所有分组，默认加个根节点
			groupMap.put("text", "所有分组");
			List<Map<String, Object>> childMap =getSubGroupMap(list,groupId);
			groupMap.put("children",childMap);
		}else{//否则，跟节点为自己拥有的分组
			for(int i=0;i<list.size();i++){
				if(list.get(i).getGroupId().equals(groupId)){ 
					groupMap.put("text", list.get(i).getGroupName());
					groupMap.put("id", list.get(i).getGroupId());
					groupMap.put("inputValue",  list.get(i).getGroupId());
					List<Map<String, Object>> childMap =getSubGroupMap(list,list.get(i).getGroupId());
					groupMap.put("children",childMap);
					groupMap.put("expanded", false);
				}
			}
		}
		groupMap.put("expanded", false);
/*		groupMap.put("text", "顶级分组");
		groupMap.put("id", 0);
		groupMap.put("inputValue", 0);
		List<Map<String, Object>> childMap =getSubGroupMap(list,parentGroupId);
		groupMap.put("children",childMap);
		groupMap.put("expanded", false);*/
		return groupMap;
	}
	
	public List<Map<String,Object>> getSubGroupMap(List<Group> list,int parentGroupId) throws Exception{
		List<Map<String,Object>> subGroupList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			if(list.get(i).getParentGroupId().equals(parentGroupId)){
				Map<String,Object> groupMap = new HashMap<String,Object>();
				groupMap.put("id", list.get(i).getGroupId());
				groupMap.put("inputValue", list.get(i).getGroupId());
				groupMap.put("text", list.get(i).getGroupName());
				List<Map<String,Object>> childGroupMap =getSubGroupMap(list,list.get(i).getGroupId());
				groupMap.put("children",childGroupMap );
				if(childGroupMap==null || childGroupMap.size()==0){ 
					groupMap.put("leaf", true);
				}else{
					groupMap.put("leaf", false);
					groupMap.put("cls", "folder");
				}
				subGroupList.add(groupMap);
			}
		}
		return subGroupList;		
	}
}
