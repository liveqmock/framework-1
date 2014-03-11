package com.mopon.controller.web.member;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.member.Group;
import com.mopon.entity.member.Member;
import com.mopon.entity.sys.PageBean;
import com.mopon.entity.sys.ResultObject;
import com.mopon.entity.sys.ResultTree;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.GroupException;
import com.mopon.service.member.IGroupService;
import com.mopon.service.member.IMemberService;
import com.mopon.service.member.IRoleService;
import com.mopon.util.Base64Utils;


/**
 * <p>Description: 分组控制器类</p>
 * @date 2013年9月22日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Controller
@RequestMapping(value = "/group")
public class GroupController extends Component {

	/**
	 * 分组服务对象
	 */
	@Autowired
	private IGroupService groupService;
	
	/**
	 * 角色服务对象
	 */
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IMemberService memberService;
	
	/**
	 * 进入添加组界面
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView intoAdd(ModelAndView mav) {
		mav.setViewName("group/add");
		return mav;
	}
	
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, Group group, int[] listCheckbox, ModelAndView mav) {
		ResultObject<Group> ro = new ResultObject<Group>();
		try {
			group.setDate(new Date());
			String username = Base64Utils.decodeToString(cookieManager.readCookie("userName", request));	
			if(listCheckbox==null || listCheckbox.length==0){
				ro.setSuccess(false);
				ro.setMessage("添加分组失败，请选择角色！");
			}else{
				if("admin".equals(username) && group.getParentGroupId()==null){
						group.setParentGroupId(0);
				}else{ 
					//普通用户不允许添加顶级分组
					if(group.getParentGroupId()==null){
						ro.setSuccess(false);
						ro.setMessage("添加分组失败，请选择父分组！");
						mav.addObject("jsonData",ro);
						return mav;
					}
				}
				group.setUsername(username);
				groupService.addGroup(group, listCheckbox);
				ro.setSuccess(true);
			}
		}catch (DataAccessException e) {
			e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("添加组失败！");
		} catch (Exception e) {
            e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_ADD.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new GroupException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("添加组失败！");
		} 
		mav.addObject("jsonData",ro);
		return mav;
	}
	
	/**
	 * 进入编辑组页面
	 * @param groupId
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "edit/{groupId}", method = RequestMethod.GET)
	public ModelAndView intoEdit(@PathVariable int groupId, ModelAndView mav) {
		Group group = new Group();
		group.setGroupId(groupId);
		ResultObject<Group> ro = new ResultObject<Group>();
		try {
			group = groupService.getGroupDetail(group);
			ro.setSuccess(true);
			ro.setResult(group);
		}catch (DataAccessException e) {
			e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("编辑组失败！");
		} catch (Exception e) {
            e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new GroupException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("编辑组失败！");
		} 
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	/**
	 * 编辑组信息
	 * @param group
	 * @param roids
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ModelAndView edit(Group group, int[] listCheckbox,int[] delRoleIds, ModelAndView mav) {
		ResultObject<Group> ro = new ResultObject<Group>();
		try {
			if(listCheckbox==null || listCheckbox.length==0){
				ro.setSuccess(false);
				ro.setMessage("修改分组失败，请选择角色！");
			}else{
				groupService.editGroup(group, listCheckbox,delRoleIds);
				ro.setSuccess(true);
			}
		}catch (DataAccessException e) {
			e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("编辑组失败！");
		} catch (Exception e) {
            e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new GroupException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("编辑组失败！");
		} 
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	/**
	 * 查看组
	 * @param groupId
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "detail/{groupId}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable int groupId, ModelAndView mav) {
		ResultObject<Group> ro = new ResultObject<Group>();
		try {
			ro.setSuccess(true);
			Group group = new Group();
			group.setGroupId(groupId);
			group = groupService.getGroupDetail(group);
			ro.setResult(group);
			
		}catch (DataAccessException e) {
			e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("编辑组失败！");
		} catch (Exception e) {
            e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_DETAIL.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new GroupException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("编辑组失败！");
		} 
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	/**
	 * 删除组
	 * @param listCheckbox
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public ModelAndView remove(int[] listCheckbox, ModelAndView mav) {
		ResultObject<Group> ro = new ResultObject<Group>();
		try {
			Group group = new Group();
			for(int i=0;i<listCheckbox.length;i++){
				if(listCheckbox[i]==1){
					ro.setMessage("顶级分组(ID=1)不允许删除！");
					ro.setSuccess(false);
					mav.addObject("jsonData", ro);
					return mav;
				}else{
					group.setGroupId(listCheckbox[i]);
					groupService.removeGroup(group);
				}
			}
			ro.setMessage("删除分组成功！");
			ro.setSuccess(true);
		}catch (DataAccessException e) {
			e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("删除组失败！");
		} catch (Exception e) {
            e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_REMOVE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new GroupException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("删除组失败！");
		} 
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public ModelAndView query(HttpServletRequest request,Group group,int page,int limit, ModelAndView mav) {
		PageBean<Group> pageBean = new PageBean<Group>();
		pageBean.setDateline(System.currentTimeMillis());
		try {
			//从session中获取member信息
			Member member = memberService.findCachedMember(request);
			//从数据库中获取最新member信息
			member = memberService.queryMemberById(member);	
			int myGroupId = member.getGroup().getGroupId();
			//所有子分组ID
			List<Group> supGroupList = null;
			//如果是非管理员，查询所拥有的子分组
			if(!"admin".equals(member.getUserName())){ 
				supGroupList =groupService.queryGroupByParentGroupId(member.getGroup().getGroupId());
			}
			
			pageBean = groupService.getGroupForList(group, page, limit, 1,supGroupList);
			
			
			pageBean.setSuccess(true);
		} catch (DataAccessException e) {
			e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            pageBean.setSuccess(false);
            pageBean.setMessage("查询失败");
		} catch (Exception e) {
            e.printStackTrace();
            ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_QUERY.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new GroupException(errorMsg);
            pageBean.setSuccess(false);
            pageBean.setMessage("查询失败");
		} 
		mav.addObject("jsonData", pageBean);
		return mav;
	}
	
	/**
	 * 获取分组树
	 * @param request
	 * @param groupId  如果传递分组ID,则加载该分组下拥有的分组
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "getGroupTree", method = RequestMethod.GET)
	public ModelAndView getGroupTree(HttpServletRequest request,ModelAndView mav) {
		try{
			ResultTree<Object> result = new ResultTree<Object>();
			Map<String, Object> resultMap = new HashMap<>();
			
			//从session中获取member信息
			Member member = memberService.findCachedMember(request);
			//从数据库中获取最新member信息
			member = memberService.queryMemberById(member);	
			
			int myGroupId = member.getGroup().getGroupId();
			//要加载的所有子分组
			List<Group> groupList = new ArrayList<Group>();

			Group query = new Group();
			query.setParentGroupId(myGroupId);
			
			//如果是admin用户，则查询所有组
			if("admin".equals(member.getUserName())){
				query.setParentGroupId(null);
				groupList.addAll(groupService.queryGroupForList(query));
			}else{  //不是admin，把自己拥有的分组加进去
				groupList.add(member.getGroup());
				groupList.addAll(groupService.queryGroupByParentGroupId(member.getGroup().getGroupId()));
			}
			

			//组装group
			Map<String,Object> map = groupService.getGroupMap(groupList, member.getGroup().getGroupId());
			
			result.add(map);
			mav.addObject("jsonData", result);
		}catch(Exception e){
			
		}	
		return mav;
	}
}
