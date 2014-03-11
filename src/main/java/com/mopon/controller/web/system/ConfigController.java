package com.mopon.controller.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.logs.OPType;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.sys.Config;
import com.mopon.entity.sys.PageBean;
import com.mopon.entity.sys.ResultObject;
import com.mopon.exception.ConfigException;
import com.mopon.exception.DatabaseException;
import com.mopon.service.sys.IConfigService;
import com.mopon.service.sys.IOperateMsgService;
import com.mopon.util.Base64Utils;

@Controller
@RequestMapping("/config")
public class ConfigController extends Component{

	@Autowired
	private IConfigService configService;
	
	@Autowired
	private IOperateMsgService operateMsgService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ModelAndView add(Config config, ModelAndView mav,HttpServletRequest request) {
		ResultObject<Config> ro = new ResultObject<Config>();
		try {
			Integer count = configService.queryForKeyCount(config);
			if(count>0){
	            ro.setSuccess(false);
	            ro.setMessage("配置键重复，请重新输入！");
			}else{
				config.setDate(new Date());
				configService.setConfig(config);
				ro.setSuccess(true);
			}
		} catch (ConfigException e) {
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.CONFIG_SAVE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("添加配置失败！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("添加配置失败！");
		}
		
		//添加系统配置日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.ADD.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("添加系统配置");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
		
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ModelAndView update(Config config, ModelAndView mav,HttpServletRequest request) {
		ResultObject<Config> ro = new ResultObject<Config>();
		try {
			configService.modifiConfig(config);
			ro.setSuccess(true);
		} catch (ConfigException e) {
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.CONFIG_SAVE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new ConfigException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("修改配置失败！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("修改配置失败！");
		}
		
		//修改系统配置日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.UPDATE.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("修改系统配置");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	public ModelAndView remove(String[] ids, ModelAndView mav,HttpServletRequest request) {
		ResultObject<Config> ro = new ResultObject<Config>();
		try {
			configService.removeConfig(ids);
			ro.setSuccess(true);
		} catch (ConfigException e) {
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.CONFIG_SAVE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new ConfigException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("删除配置失败！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            ro.setSuccess(false);
            ro.setMessage("删除配置失败！");
		}
		
		//删除系统配置日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.DELETE.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("删除系统配置");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
		
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	@RequestMapping(value = "list")
	public ModelAndView list(ModelAndView mav,Config config,Integer page,Integer limit) {
		PageBean<Config> rl = new PageBean<Config>();
		rl.setDateline(System.currentTimeMillis());
		try {
			rl = configService.queryConfigForList(page,limit,config);
			rl.setSuccess(true);
		} catch (ConfigException e) {
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.CONFIG_SAVE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new ConfigException(errorMsg);
            rl.setSuccess(false);
            rl.setMessage("查找配置失败！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
            new DatabaseException(errorMsg);
            rl.setSuccess(false);
            rl.setMessage("查找配置失败！");
		}
		mav.addObject("jsonData", rl);
		return mav;
	}
	
}
