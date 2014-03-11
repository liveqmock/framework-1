package com.mopon.controller.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.dao.master.ITestDao;
import com.mopon.dao.slave.IReadTestDao;
import com.mopon.entity.Bill;
import com.mopon.entity.Report;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.logs.OPType;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.member.Group;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.FtpTask;
import com.mopon.entity.sys.MessageEntity;
import com.mopon.entity.sys.PageBean;
import com.mopon.entity.sys.ResultList;
import com.mopon.entity.sys.ResultMap;
import com.mopon.entity.sys.ResultObject;
import com.mopon.entity.sys.Table;
import com.mopon.entity.sys.TableRule;
import com.mopon.entity.sys.XmlBeanList;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.ErrorMsgException;
import com.mopon.exception.GroupException;
import com.mopon.ftp.FtpTransfer;
import com.mopon.listener.FtpUploadListener;
import com.mopon.listener.JmsMessageListener;
import com.mopon.protobuf.entity.AdminBuf;
import com.mopon.service.member.IGroupService;
import com.mopon.service.sys.IOperateMsgService;
import com.mopon.service.sys.ITableService;
import com.mopon.util.CookieManager;
import com.mopon.util.MD5;
import com.mopon.util.Session;


@Controller
@RequestMapping("/test")
public class TestController extends Component  {
	
	@Autowired
	private IGroupService groupService;
	
	@Autowired
	private ITableService tableService;
	
	@Autowired
	private ITestDao testDao;
	
	@Autowired
	private IReadTestDao readTestDao;
	
	@Autowired
	private IOperateMsgService operateMsgService;
	
	
	@RequestMapping(value = "/proto", method = RequestMethod.GET)
	public ModelAndView getAdminBuf(ModelAndView mav) {
		AdminBuf.Admin.Builder admin = AdminBuf.Admin.newBuilder();
		admin.setAdminID("001100");
		admin.setAdminName("reaganjava");
		admin.setAdminPwd("33333");
		System.out.println(admin.build().toByteArray());
		mav.addObject("protoDate", admin.build().toByteArray());
		return mav;
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getTest() {
		System.out.println(md5.getMD5ofStr("Hello world"));
		System.out.println(this.arith);
		System.out.println(this.signaturer);
		System.out.println(this.signProvider);
		System.out.println(this.keyGenerater);
		System.out.println(typeFormat.formatCNDate(new Date()));
		return "test";
	}
	
	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public String userCenter() {
		return "center";
	}
	
	
	@RequestMapping(value = "/admin/page", method = RequestMethod.GET)
	public String reAdminPage() {
		return "admin/test";
	}
	
	@RequestMapping(value = "/bill", method = RequestMethod.GET)
	public ModelAndView getXls(ModelAndView mav) {
		List<Bill> billList = new ArrayList<Bill>();
		XmlBeanList<Bill> xmlBeanList = new XmlBeanList<Bill>();
		for(int i = 0; i < 100; i++) {
			Bill bill = new Bill();
			bill.setBid(10000);
			bill.setCompnay("aaaaaaaaaaaaaaaaaa");
			bill.setDateline(System.currentTimeMillis());
			bill.setIncome(3580002.00);
			bill.setPay(0.00);
			billList.add(bill);			
		}
		xmlBeanList.setTotalCount(billList.size());
		xmlBeanList.setStart(120);
		xmlBeanList.setEnd(240);
		xmlBeanList.setCurrentPage(2);
		xmlBeanList.setCount(120);
		xmlBeanList.setItems(billList);
		mav.addObject("xmlData", xmlBeanList);
		return mav;
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void writeSession(HttpServletRequest request, HttpServletResponse response) {
		Session session = this.sessionManager.getSession(request.getSession().getId());
		session.setAttribute("KEY", "HELLO");
		cookieManager.writeCookie("test", "aaaaaaaaaaaaaa", CookieManager.HALF_HOUR, response);
	}
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void readSession(HttpServletRequest request) {
		Session session = this.sessionManager.getSession(request.getSession().getId());
		Object value = session.getAttribute("KEY");
		System.out.println(value);
		System.out.println(cookieManager.readCookie("test", request));
	}
	
	@RequestMapping(value = "/get/{groupId}", method = RequestMethod.GET)
	public void getGroup(@PathVariable int groupId) {
		Group group = new Group();
		group.setGroupId(groupId);
		try {
			groupService.queryGroupForList(group);
		} catch (GroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public ModelAndView getGroupPage(ModelAndView mav) {
		Group group = new Group();
		try {
			PageBean<Group> pageBean = groupService.getGroupForList(group, 0, 20, 1,null);
			ResultList<Group> resultList = new ResultList<Group>();
			resultList.setSuccess(true);
			resultList.setMessage("");
			resultList.setResultList(pageBean.getDataList());
			mav.addObject("jsonData", pageBean);
		} catch (GroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getObject", method = RequestMethod.GET)
	public ModelAndView getGroupObject(ModelAndView mav) {
		Group group = new Group();
		group.setGroupId(1);
		try {
			group = groupService.getGroupDetail(group);
			ResultObject<Group> resultObject = new ResultObject<Group>();
			resultObject.setSuccess(true);
			resultObject.setMessage("");
			resultObject.setResult(group);
			mav.addObject("jsonData", resultObject);
		} catch (GroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/manyObject", method = RequestMethod.GET)
	public ModelAndView getManyObject(ModelAndView mav) {
		Group group = new Group();
		Role role = new Role();
		role.setRoleId(2);
		role.setRoleName("admin");
		group.setGroupId(1);
		try {
			group = groupService.getGroupDetail(group);
			ResultMap rm = new ResultMap();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Group", group);
			map.put("Role", role);
			rm.setSuccess(true);
			rm.setMessage("");
			rm.setResultMap(map);
			mav.addObject("jsonData", rm);
		} catch (GroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/table", method = RequestMethod.GET)
	public void createTable() {
		/*tableService.createTable("ORDER_" + System.currentTimeMillis());
		Table table = new Table();
		table.setTableName("ORDER");
		List<Table> tableList = tableService.queryTable(table, TABLE_NAME);
		for(Table t : tableList) {
			System.out.println(t.getTableName());
		}*/
		/*//
		TableRule tableRule = new TableRule();
		tableRule.setTableName("FUCK");
		tableRule.setStartDate(new Date());
		tableRule.setInterval(50000);
		testDao.saveTest(tableRule);*/
		/*List<TableRule> tableRule = tableService.queryTableRule();
		for(TableRule tr : tableRule) {
			System.out.println(tr.getId());
			System.out.println(tr.getTableName());
			System.out.println(tr.getUnionUpdateSQL());
			System.out.println(tr.getUnionUpdateSQL());
			System.out.println(tr.getUnionTable());
			System.out.println(tr.getCreateSQL());
		}*/
		//System.out.println(readTestDao.query().size());
		tableService.startCreateTableTask();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void add() {
		System.out.println("add");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void update() {
		System.out.println("update");
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public void query() {
		System.out.println("query");
	}
	
	@RequestMapping(value = "/op", method = RequestMethod.GET)
	public void addOP() {
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.ADD.getOpType());
		om.setUid(1);
		om.setName("reaganjava");
		om.setSiteId(1);
		om.setSiteName("CRM系统");
		om.setDateline(new Date());
		om.setMessage("添加用户");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
	}
	
}
