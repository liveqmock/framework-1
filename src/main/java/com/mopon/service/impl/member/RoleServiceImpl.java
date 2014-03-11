package com.mopon.service.impl.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.member.IOperateDao;
import com.mopon.dao.master.member.IRoleDao;
import com.mopon.entity.member.Group;
import com.mopon.entity.member.GroupRole;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.member.Operate;
import com.mopon.entity.member.Role;
import com.mopon.entity.member.RoleMenu;
import com.mopon.entity.member.RoleOperate;
import com.mopon.entity.sys.PageBean;
import com.mopon.service.impl.sys.BaseServiceImpl;
import com.mopon.service.member.IRoleService;
@SuppressWarnings("unchecked")
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements IRoleService {

	
	@Autowired
	private IRoleDao roleDao ;
	
	@Autowired
	private IOperateDao operateDao;

	
	@Override
	@Transactional
	public int save(Role entity,List<Integer> menuList,List<Integer> operateList,Integer groupId) throws Exception {
		int result =  1;
		//先检测是否存在角色
		Role r =roleDao.queryRoleByRoleName(entity.getRoleName());
		if(r!=null){//存在相同角色
			return  2;
		}
		roleDao.save(entity);
		if(menuList!=null && menuList.size()>0){//添加角色菜单
			for(int i=0;i<menuList.size();i++){
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(entity.getRoleId());
				roleMenu.setMenuId(menuList.get(i));
				roleDao.saveRoleMenu(roleMenu);
			}
		}
		if(operateList!=null && operateList.size()>0){ //添加角色操作
			for(int i=0;i<operateList.size();i++){
				RoleOperate ro = new RoleOperate();
				ro.setRoleId(entity.getRoleId());
				ro.setOperateId(operateList.get(i));
				roleDao.saveRoleOperate(ro);
			}
		}
		//保存角色组
		if(groupId !=null && groupId!=0){
			GroupRole rg = new GroupRole();
			rg.setRoleId(entity.getRoleId());
			rg.setGroupId(groupId);
			roleDao.saveRoleGroup(rg);
		}

		return result;
	}

	@Override
	@Transactional
	public void remove(int roleId) throws Exception{
		List<Role> roles = queryRoleIdByParentRoleId(roleId);
		Role role = new Role();
		role.setRoleId(roleId);
		roles.add(role); //将本身加入删除队列中
		for(int i=0;i<roles.size();i++){//开始删除
			int rId =roles.get(i).getRoleId();
			System.out.println("start delete role:"+rId);
			//删除角色菜单
			roleDao.removeRoleMenuByRoleId(rId);
			//删除角色操作
			roleDao.removeRoleOperateByRoleId(rId);
			//删除角色组
			roleDao.removeRoleGroupByRoleId(rId);
			//取消用户的角色
			roleDao.updateMemberRole(roleId);
			//删除角色
			roleDao.remove(rId);
		}
	}


	@Override
	@Transactional
	public void remove(int[] roleIds) throws Exception {
		for(int i=0;i<roleIds.length;i++){
			remove(roleIds[i]);
		}
	}

	@Override
	@Transactional
	public void update(Role entity,List<Integer> addMenuIdList,List<Integer> delMenuIdList,List<Integer> addOperateIdList,List<Integer> delOperateIdList) throws Exception{

		//先删除所有子角色中该角色不拥有的菜单和操作
		List<Role> subList = queryRoleIdByParentRoleId(entity.getRoleId());
		if(subList!=null && subList.size()>0){
			for(Role r : subList){
				if(delMenuIdList!=null && delMenuIdList.size()>0){
					for(int i=0;i<delMenuIdList.size();i++){
						roleDao.removeRoleMenu(r.getRoleId(),delMenuIdList.get(i));
					}
				}
				if(delOperateIdList!=null && delOperateIdList.size()>0){
					for(int i=0;i<delOperateIdList.size();i++){
						roleDao.removeRoleOperate(r.getRoleId(),delOperateIdList.get(i));
					}
				}
			}
		}
		
		
		//执行删除本角色所有菜单和操作
		// addX为要添加的菜单和操作   delX为不添加的菜单和操作，两者相加为所有菜单和操作
		delOperateIdList.addAll(addOperateIdList);
		delMenuIdList.addAll(addMenuIdList);
		if(delMenuIdList!=null && delMenuIdList.size()>0){
			for(int i=0;i<delMenuIdList.size();i++){
				roleDao.removeRoleMenu(entity.getRoleId(),delMenuIdList.get(i));
			}
		}
		if(delOperateIdList!=null && delOperateIdList.size()>0){
			for(int i=0;i<delOperateIdList.size();i++){
				roleDao.removeRoleOperate(entity.getRoleId(),delOperateIdList.get(i));
			}
		}
		//////////////进行添加菜单和操作
		if(addMenuIdList!=null && addMenuIdList.size()>0){//添加角色菜单
			for(int i=0;i<addMenuIdList.size();i++){
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(entity.getRoleId());
				roleMenu.setMenuId(addMenuIdList.get(i));
				roleDao.saveRoleMenu(roleMenu);
			}
		}
		if(addOperateIdList!=null && addOperateIdList.size()>0){ //添加角色操作
			for(int i=0;i<addOperateIdList.size();i++){
				RoleOperate ro = new RoleOperate();
				ro.setRoleId(entity.getRoleId());
				ro.setOperateId(addOperateIdList.get(i));
				roleDao.saveRoleOperate(ro);
			}
		}
		//更新角色信息
		roleDao.update(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public PageBean<Role> queryRoles(Role role,int pageNo, int pageSize)throws Exception {	
		PageBean<Role> pageBean = new PageBean<Role>();
		
		pageBean.setRecordCount(roleDao.queryCount(role));
		pageBean.setPageSize(pageSize);
		pageBean.setPageCount(pageBean.getRecordCount());
		//设置当前页
		pageBean.setCurrentPage(pageNo>pageBean.getPageCount()?(int)pageBean.getPageCount():pageNo);
		pageBean.setCurrentPage(pageNo>0?pageNo:1);
		pageBean.setDataList(roleDao.queryRoles(role,(pageBean.getCurrentPage()-1)*pageSize,pageSize));
		return pageBean;
	}

	@Override
	@Transactional(readOnly = true)
	public int queryCount(Role role)throws Exception {
		return roleDao.queryCount(role);
	}

	@Override
	@Transactional(readOnly = true)
	public Role queryRoleByRoleID(int roleId) throws Exception {
		return roleDao.queryRoleByRoleID(roleId);
	}

	@Override
	@Transactional
	public void saveRoleMenu(RoleMenu roleMenu) throws Exception {
		roleDao.saveRoleMenu(roleMenu);
	}

	@Override
	@Transactional
	public void saveRoleOperate(RoleOperate roleOperate)throws Exception {
		roleDao.saveRoleOperate(roleOperate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryRoleIdByParentRoleId(int parentRoleId)throws Exception {
		List<Role> roles = roleDao.queryRoleIdByParentRoleId(parentRoleId);
		List<Role> allList = new ArrayList<Role>();
		allList.addAll(roles);
		return getAllSubRole(roles, allList);
	}

	/**
	 * 递归方法，查询所有子role
	 * @param parentList
	 * @param allList
	 * @return
	 */
	public List<Role> getAllSubRole(List<Role> subList,List<Role> allList)throws Exception{
		if(subList==null || subList.size()==0)return allList;
		//子类角色
		List<Role> newSubList = new ArrayList<Role>();
		for(int i=0;i<subList.size();i++){
			List<Role> tempList=roleDao.queryRoleIdByParentRoleId(subList.get(i).getRoleId());
			newSubList.addAll(tempList);//添加子类角色
			allList.addAll(tempList);
		}
		if(newSubList.size()>0)
			return getAllSubRole(newSubList, allList);
		
		return allList;
	}
	
	/**
	 * 递归方法查询所有角色的父角色
	 * @param subList
	 * @param allList
	 * @return
	 * @throws Exception
	 */
	public List<Role> getAllParentRole(List<Role> subList,List<Role> allList)throws Exception{
		if(subList==null || subList.size()==0)return allList;
		//父类角色
		List<Role> newSubList = new ArrayList<Role>();
		for(int i=0;i<subList.size();i++){
			Role tempList=roleDao.queryRoleByRoleID(subList.get(i).getParentRoleId());
			if(tempList==null)continue;
			newSubList.add(tempList);//添加父类角色
			allList.add(tempList);
		}
		if(newSubList.size()>0)
			return getAllParentRole(newSubList, allList);
		
		return allList;
	}
	@Override
	public List<Integer> queryParentRoleByRoleId(int roleId) throws Exception {
		Role role = roleDao.queryRoleByRoleID(roleId);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		List<Role> allList = new ArrayList<Role>();
		allList.add(role);
		List<Role> list = getAllParentRole(roles, allList);
		List<Integer> parentRoleIds = new ArrayList<Integer>();
		if(list!=null && list.size()>0){
			for(Role r : list){
				parentRoleIds.add(r.getRoleId());
			}
		}
		
		return parentRoleIds;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MenuItem> queryMenuByRoleID(int roleId)throws Exception{
		return roleDao.queryMenuByRoleID(roleId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Operate> queryOperateByRoleID(int roleId)throws Exception{
		return roleDao.queryOperateByRoleID(roleId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Group> queryGroupByRoleID(int roleId)throws Exception {
		return roleDao.queryGroupByRoleID(roleId);
	}

	@Override
	@Transactional
	public void removeRoleMenuById(int id) throws Exception {
		roleDao.removeRoleMenuById(id);
	}

	@Override
	@Transactional
	public void removeRoleOperateById(int id) throws Exception {
		roleDao.removeRoleOperateById(id);
	}

	@Override
	@Transactional
	public void removeRoleGroupById(int id) throws Exception {
		roleDao.removeRoleGroupById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryAllRoles() throws Exception {
		return roleDao.queryAllRoles();
	}

	public Map<String,Object> getRoleMap(List<Role> list,int roleID,List<Role> myRoleGroup,List<Role> myRoleMenu)throws Exception{
		Map<String,Object> roleMap = new HashMap<String,Object>();
		if(roleID==0){//查询所有角色，默认加个根节点
			roleMap.put("text", "所有角色");
			List<Map<String, Object>> childMap =getSubRoleMap(list,roleID,myRoleGroup,myRoleMenu);
			roleMap.put("children",childMap);
		}else{ //否则，跟节点为自己拥有的角色
			for(int i=0;i<list.size();i++){
				if(list.get(i).getRoleId().equals(roleID)){ //
					roleMap.put("text", list.get(i).getRoleName());
					roleMap.put("id", list.get(i).getRoleId());
					roleMap.put("inputValue", list.get(i).getRoleId());
					List<Map<String, Object>> childMap =getSubRoleMap(list,list.get(i).getRoleId(),myRoleGroup,myRoleMenu);
					roleMap.put("children",childMap);
				}
			}
		}
		if(myRoleGroup!=null && myRoleGroup.size()>0){
			roleMap.put("checked", true);
		}else if(myRoleMenu!=null && myRoleMenu.size()>0){
			roleMap.put("checked", true);
		}else{
			roleMap.put("checked", false);
		}
		roleMap.put("expanded", false);
/*		roleMap.put("text", "角色管理");
		List<Map<String, Object>> childMap =getSubRoleMap(list,roleID,myRoleGroup,myRoleMenu);
		roleMap.put("children",childMap);
		if(myRoleGroup!=null && myRoleGroup.size()>0){
			roleMap.put("checked", true);
		}else if(myRoleMenu!=null && myRoleMenu.size()>0){
			roleMap.put("checked", true);
		}else{
			roleMap.put("checked", false);
		}
				
		roleMap.put("expanded", false);*/
		return roleMap;
	}
	
	/**
	 * 获取指定角色的所有子角色
	 * @param list 所有角色
	 * @param parentRoleId 父类角色Id
	 * @param myRoleGroup 具体某个组拥有的角色列
	 * @param myRoleMenu  具体某个菜单拥有的角色列
	 * @return 所有子角色的list
	 */
	public List<Map<String,Object>> getSubRoleMap(List<Role> list,int parentRoleId,List<Role> myRoleGroup,List<Role> myRoleMenu)throws Exception{
		List<Map<String,Object>> subRoleList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			if(list.get(i).getParentRoleId().equals(parentRoleId)){
				Map<String,Object> roleMap = new HashMap<String,Object>();
				roleMap.put("id", list.get(i).getRoleId());
				// 不能同时查询分组拥有的角色和菜单拥有的角色，所以 使用  else if  ,而不是 if
				if(myRoleGroup!=null && myRoleGroup.size()>0){
					roleMap.put("checked",existRole(list.get(i).getRoleId(),myRoleGroup));
				}else if(myRoleMenu!=null && myRoleMenu.size()>0){
					roleMap.put("checked",existRole(list.get(i).getRoleId(),myRoleMenu));
				}else{
					roleMap.put("checked", false);
				}
				roleMap.put("inputValue", list.get(i).getRoleId());
				roleMap.put("text", list.get(i).getRoleName()+" - "+list.get(i).getRoleDsc());
				List<Map<String,Object>> childRoleMap =getSubRoleMap(list, list.get(i).getRoleId(),myRoleGroup,myRoleMenu);
				roleMap.put("children",childRoleMap );
				if(childRoleMap==null || childRoleMap.size()==0){ 
					roleMap.put("leaf", true);
				}else{
					roleMap.put("leaf", false);
					roleMap.put("cls", "folder");
				}
				subRoleList.add(roleMap);
			}
		}
		return subRoleList;
	}
	
	/**
	 * 查询角色列表中否含有指定角色id
	 * @param roleId  角色id
	 * @param list  拥有的所有角色列表
	 * @return
	 */
	public boolean existRole(int roleId,List<Role> list){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getRoleId().equals(roleId))
				return true;
		}
		return false;
	}
	
	/**
	 * 查询菜单列表中是否含有指定菜单id
	 * @param menuId 菜单ID
	 * @param list 拥有的所有菜单列表
	 * @return
	 */
	public boolean existMenu(int menuId,List<MenuItem> list){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getMenuId().equals(menuId))
				return true;
		}
		return false;
	}
	
	/**
	 * 查询是否存在操作
	 * @param menuId
	 * @param list
	 * @return
	 */
	public boolean existOperate(int operateId,List<Operate> list){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getOpId().equals(operateId))
				return true;
		}
		return false;
	}
	
	@Override
	public Map<String, Object> getMenuMap(List<MenuItem> list,List<MenuItem> myMenu,List<Operate> myOperate,boolean flag,List<Operate> operateList,boolean isShowChecked,boolean loadsubMenu,boolean loadOperate) throws Exception {
		Map<String,Object> roleMap = new HashMap<String,Object>();
		List<Map<String, Object>> children = getSubMenuMap(list,1,myMenu,myOperate,flag,operateList,isShowChecked,loadsubMenu,loadOperate);
		roleMap.put("children", children);
		roleMap.put("text", "根菜单");
		roleMap.put("id", "1");
		roleMap.put("expanded", "true");
		//显示勾选状态
		if(isShowChecked){
			//如果存在拥有的菜单，则勾选状态
			if(myMenu !=null && myMenu.size()>0)
				roleMap.put("checked", true);
			else{
				roleMap.put("checked", false);
			}
		}
		return roleMap;
	}
	
	/**
	 * 获取指定菜单的所有子菜单
	 * @param list 所有菜单
	 * @param parentMenuId 父类菜单Id
	 * @param myMenu 指定角色拥有的菜单
	 * @param myOperate 指定角色拥有的操作
	 * @param flag 是否加载不拥有的菜单项 true 加载
	 * @return 所有子菜单的list
	 */
	public List<Map<String,Object>> getSubMenuMap(List<MenuItem> list,Integer parentMenuId,List<MenuItem> myMenu,List<Operate> myOperate,boolean flag,List<Operate> operateList,boolean isShowChecked,boolean loadsubMenu,boolean loadOperate)throws Exception{
		List<Map<String,Object>> subMenuList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			if(list.get(i).getMainMenuId() !=null && list.get(i).getMainMenuId().equals(parentMenuId)){
				Map<String,Object> menuMap = new HashMap<String,Object>();
				Integer menuId = list.get(i).getMenuId();
				menuMap.put("id", menuId);
				menuMap.put("text", list.get(i).getMenuName());
				menuMap.put("name","listCheckbox");
				menuMap.put("inputValue", menuId);
				menuMap.put("expanded", false);
				if(menuId==59){
					System.out.println("testttttt");
				}
				List<Map<String,Object>> childMenuMap = getSubMenuMap(list, menuId,myMenu,myOperate,flag,operateList,isShowChecked,loadsubMenu,loadOperate);
				menuMap.put("children",childMenuMap );
				boolean hasOperate = false; //是存在子操作
				if(childMenuMap==null || childMenuMap.size()==0){ //如果不存在子菜单,则查询该菜单的操作
					if(menuId!=null && loadOperate){
						//某菜单下所有操作
						List<Operate> allOperateList = operateDao.queryOperateByMenuId(menuId);
						//operateList 加载父角色的操作，myOperate 该角色拥有的操作
						List<Map<String,Object>> childMenuOperate = getOperateByMenuId(getOwnerOperate(allOperateList,operateList) ,myOperate,flag,isShowChecked);
						menuMap.put("children",childMenuOperate);
						if(childMenuOperate==null || childMenuOperate.size()==0){ //没子类，最后一个节点
							menuMap.put("leaf", true);					
						}else{    //有子类
							menuMap.put("leaf", false);
							menuMap.put("cls", "folder");
							hasOperate = true;
						}
					}else{
		                //如果加载节点菜单，但是不加载操作
		                if(loadsubMenu && !loadOperate)
		                    menuMap.put("leaf", true);
					}
				}		
				
				if(myMenu !=null && myMenu.size()>0){
					boolean exist =existMenu(menuId, myMenu);
					if(isShowChecked){
						menuMap.put("checked",exist);
					}
					

					//不拥有操作，或者 拥有操作，但是加载拥有操作的该节点菜单
					if((hasOperate && loadsubMenu) || !hasOperate){
						if(exist ||(!exist && flag))
							subMenuList.add(menuMap);
					}
				}else if(flag){
					if(isShowChecked){
						menuMap.put("checked",false);
					}
					//0 或者 1 加载 节点菜单(拥有操作的节点菜单)
					//不拥有操作，或者 拥有操作，但是加载拥有操作的该节点菜单
					if((hasOperate && loadsubMenu) || !hasOperate){
						subMenuList.add(menuMap);
					}
				}
			}
		}
		return subMenuList;
	}
	
	/**
	 * 过滤掉父类角色没有拥有的操作
	 * @param list 具体某菜单拥有的操作
	 * @param operateList 父类角色拥有的操作
	 * @return
	 */
	public List<Operate> getOwnerOperate(List<Operate>  list,List<Operate> operateList){
		if(operateList==null || operateList.size()==0)
			return operateList;
		else{
			List<Operate> ownerList = new ArrayList<Operate>();
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					for(Operate o : operateList){
						if(o.getOpId().equals(list.get(i).getOpId())){
							ownerList.add(o);
							continue;
						}			
					}
				}
			}
			return ownerList;
		}
	}
	
	/**
	 * 特殊处理下获取操作list ：将操作ID 前面附加“OP” 否则和菜单一起生成js tree的时候，会因为相同ID,导致不会生成节点
	 * @return
	 */
	public List<Map<String,Object>> getOperateByMenuId(List<Operate> operateList,List<Operate> myOperate,boolean flag,boolean isShowChecked){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(operateList!=null && operateList.size()>0){
			for(int i=0;i<operateList.size();i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", "OP"+operateList.get(i).getOpId());
				map.put("text", "操作-"+operateList.get(i).getOpName());
				map.put("name","listCheckbox");
				map.put("inputValue", "OP"+operateList.get(i).getOpId());
				map.put("leaf", true);
				if(myOperate !=null && myOperate.size()>0){
					boolean exist = existOperate(operateList.get(i).getOpId(), myOperate);
					if(isShowChecked){
						map.put("checked",exist );
					}
					if(exist ||(!exist && flag))
						list.add(map);
				}else if(flag){ 
					if(isShowChecked){
						map.put("checked", false);
					}
					list.add(map);
				}
			}
		}
		
		return list;
	}
	
	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @return
	 * @throws Exception   
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> getParentRole() throws Exception {
		return roleDao.queryRoleByParent();
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @return
	 * @throws Exception   
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> getSubRole() throws Exception {
		return roleDao.queryRoleBySub();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryRoleByGroupID(int groupId) throws Exception {
		return roleDao.queryRoleByGroupID(groupId);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Role> queryRoleByMenuID(int menuId) throws Exception {
		return roleDao.queryRoleByMenuID(menuId);
	}

	
	@Override
	@Transactional
	public void initMemberRolebyOperate(int uid, Role role) throws Exception {
		//权限容器
		List<String> actionList = (List<String>) cachedDao.get(uid + "RIGHT");
		//权限MAP
		if(actionList == null) {
			actionList = new ArrayList<String>();
		}
		//操作列表
		List<Operate> operateList = new ArrayList<Operate>();
		//角色ID列表
		List<Integer> roleIDS = new ArrayList<Integer>();
		//是否是父权限
		if(role.getParentRoleId() == 0) {
			//得到子权限
			List<Role> subRoleList = this.queryRoleIdByParentRoleId(role.getRoleId());
			for(Role subRole : subRoleList) {
				roleIDS.add(subRole.getRoleId());
			}
		} else {
			roleIDS.add(role.getRoleId());
		}
		for(int roid : roleIDS) {
			//得到角色的操作
			List<Operate> opList = this.queryOperateByRoleID(roid);
			operateList.addAll(opList);
		}
		//操作字符串
		for(Operate op : operateList) {
			actionList.add(op.getOpAction());
		}
		cachedDao.set(uid + "RIGHT", actionList);
	}

}
