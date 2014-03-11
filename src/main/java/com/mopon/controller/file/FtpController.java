package com.mopon.controller.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.sys.FileMessage;
import com.mopon.entity.sys.FtpTask;
import com.mopon.entity.sys.ResultMap;
import com.mopon.exception.FtpException;
import com.mopon.ftp.FtpTransfer;
import com.mopon.listener.FtpUploadListener;
import com.mopon.service.sys.IFileService;

/**
 * <p>Description:FTP文件上传类</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Controller
@RequestMapping(value = "/fileManager")
public class FtpController extends Component implements FtpUploadListener {
	
	
	/**
	 * FTP工具类
	 */
	@Autowired
	private FtpTransfer ftpTransfer;
	
	/**
	 * FILE管理类
	 */
	@Autowired
	private IFileService fileService;
	/**
	 * 进度信息更新
	 */
	private static Map<String, String> progressMap = new ConcurrentHashMap<String, String>();
	
	/** 
	 * 方法用途: 进入上传页面
	 * 实现步骤: 通过请求转发到上传的JSP页面<br>
	 * @param model 上传页面需要的ID值
	 */
	@RequestMapping(value="/ftp", method = RequestMethod.GET)
	public ModelAndView reFtpUpload(ModelAndView mav) {
		String ftpkey = UUID.randomUUID().toString();
		mav.addObject("FTPKEY", ftpkey);
		mav.setViewName("/fileManager/ftp");
		progressMap.put(ftpkey, "0");
		return mav;
	}
	
	/** 
	 * 方法用途: 上传文件
	 * 实现步骤: 通过FTP来上传文件到FTP文件服务器<br>
	 * @param file 描述文件信息的对象
	 * @param key 文件的ID信息
	 */
	@RequestMapping(value="/ftpUpload/{key}", method = RequestMethod.POST)
	public ModelAndView getFTPUpload(@RequestParam(value = "upload", required = true) MultipartFile file, @PathVariable String key, ModelAndView mav) {
		loggerUtil.debug("==========FTP上传文件开始==========");
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		int index = filename.lastIndexOf(".") + 1;
		String type = filename.substring(index);
		System.out.println(type);
		long filesize = file.getSize();
		ResultMap result = new ResultMap();
		try {
			ftpTransfer.setListener(this);
			loggerUtil.debug("==========文件ID " + key + "==========");
			InputStream input = file.getInputStream();
			FtpTask ftpTask = new FtpTask();
			ftpTask.setFilename(filename);
			ftpTask.setFilesize(filesize);
			ftpTask.setInput(input);
			ftpTask.setEnd(false);
			boolean isSuccess = ftpTransfer.addFtpTask(key, ftpTask);
			if(isSuccess) {
				FileMessage fm = new FileMessage();
				fm.setFilename(filename);
				fm.setType(type);
				fm.setPath("/ftp");
				fm.setLength(filesize);
				fm.setUpType("FTP");
				fm.setUrl("/ftpdown/" + fm.getFid());
				fm.setDate(new Date());
				fileService.addFile(fm);
				progressMap.remove(key);
				result.setMessage("上传文件成功");
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg();
			errorMsg.setEid(UUID.randomUUID().toString());
			errorMsg.setCode(ErrorStatus.FTP.value());
			errorMsg.setErrorDate(new Date());
			errorMsg.setMsg(e.getMessage());
			errorMsg.setLevel(ErrorLevel.ERROR.value());
			errorMsg.setType(ErrorType.SYSTEM_ERROR.value());
			result.setMessage("上传文件失败");
			result.setSuccess(false);
			new FtpException(errorMsg);
		}
		mav.addObject("jsonData", result);
		loggerUtil.debug("==========FTP上传文件结束==========");
		return mav;
	}
	
	/** 
	 * 方法用途: 更新文件进度
	 * 实现步骤: FTP上传大文件需要显示进度，根据事件更新上传页面的进度<br>
	 * @param key 文件的ID信息
	 * @param request 请求
	 * @param response 响应
	 */
	@RequestMapping(value="/ftpProgress/{key}", method = RequestMethod.GET)
	public void getProgress(@PathVariable String key, HttpServletRequest request, HttpServletResponse response) {
		try {
			String value = progressMap.get(key);
			if(value == null) {
				value = "0";
			}
			response.getWriter().print(value);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * 方法用途: 下载FTP文件
	 * 实现步骤: 从FTP下载文件<br>
	 * @param fid 文件ID
	 * 
	 * @param response 响应
	 */
	@RequestMapping(value="/ftpdown/{fid}", method = RequestMethod.GET)
	public void ftpDownload(@PathVariable String fid, HttpServletResponse response) {
		try {
			OutputStream out = response.getOutputStream();
			System.out.println(fid);
			FileMessage fm = fileService.getFileMessageByFid(fid);
			System.out.println(fm.getFilename());
			response.setHeader("Content-Disposition", "attachment; filename=" + fm.getFilename());
			response.setContentLength((int) fm.getLength());
			ftpTransfer.downloadFile(fm.getFilename(), out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * 方法用途: 进度事件处理方法
	 * 实现步骤: 更新进度条的值<br>
	 * @param key 文件的ID信息
	 * @param progressvalue 进度条的值
	 */
	@Override
	public void progress(String key, int progressvalue) {
		String msg = progressvalue + "";
		progressMap.put(key, msg);
	}
}
