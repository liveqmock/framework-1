package com.mopon.controller.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.sys.ResultMap;
import com.mopon.exception.HttpFileException;
import com.mopon.util.FileType;

/**
 * <p>Description:HTTP文件上传类</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Controller("FileController")
@RequestMapping(value = "/fileManager")
public class FileController extends Component {
	
	
	/** 
	 * 方法用途: 进入上传页面
	 * 实现步骤: 通过请求转发到上传的JSP页面<br>
	 * @return String 字符串唯一键值
	 */
	@RequestMapping(value="/upload", method = RequestMethod.GET)
	public String fileUpload(Model model) {
		model.addAttribute("TYPE", 4);
		return "fileManager/file";
	}

	/** 
	 * 方法用途: 上传文件
	 * 实现步骤: 上传文件到服务器端<br>
	 * @param request 请求
	 * @param response 响应
	 * @param mav mvc视图内容封装类
	 * @param type 文件类型
	 * @return 返回视图封装类型，成功提示
	 */
	
	@RequestMapping(value = "/upload/{type}")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, @PathVariable
	int type) throws Exception {
		loggerUtil.debug("==========上传文件开始==========");
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		MultipartFile multFile = multiRequest.getFile("file");
		
		String extName = "";
		String newName = "";
		boolean success = true;
		ResultMap result = new ResultMap();
		// 取得上传的文件名
		String fileName = multFile.getOriginalFilename();
		try {
			if (fileName != null && !"".equals(fileName.trim())) {
				// 上传文件的大小
				long size = multFile.getSize();
				if (size > (1024 * 1024)) {
					ErrorMsg errorMsg = new ErrorMsg();
					errorMsg.setEid(UUID.randomUUID().toString());
					errorMsg.setCode(ErrorStatus.HTTP_FILE_OVER_SIZE.value());
					errorMsg.setErrorDate(new Date());
					errorMsg.setMsg("只允许上传2M以内的文件");
					errorMsg.setLevel(ErrorLevel.ERROR.value());
					errorMsg.setType(ErrorType.OPERATION_ERROR.value());
					success = false;
					throw new HttpFileException(errorMsg);
				}
				if (fileName.lastIndexOf(".") >= 0) {
					extName = fileName.substring(fileName.lastIndexOf("."));
				}
				// 定义允许上传的文件类型
				List<String> fileTypes = new ArrayList<String>();
				fileTypes.add(".jpg");
				fileTypes.add(".jpeg");
				fileTypes.add(".gif");
				fileTypes.add(".png");
				fileTypes.add(".xls");
				fileTypes.add(".doc");
				fileTypes.add(".txt");
				fileTypes.add(".xml");
				if (!fileTypes.contains(extName.toLowerCase())) {
					ErrorMsg errorMsg = new ErrorMsg();
					errorMsg.setEid(UUID.randomUUID().toString());
					errorMsg.setCode(ErrorStatus.HTTP_FILE_TYPE_ERROR.value());
					errorMsg.setErrorDate(new Date());
					errorMsg.setMsg("文件格式不符合要求");
					errorMsg.setLevel(ErrorLevel.ERROR.value());
					errorMsg.setType(ErrorType.OPERATION_ERROR.value());
					success = false;
					throw new HttpFileException(errorMsg);
				}
	
				String root = request.getSession().getServletContext().getRealPath("");
				newName = UUID.randomUUID().toString().replace("-", "");
				String savePath = getImagePath(newName, root, type);
				System.out.println(savePath);
				if (success) {
	
					File newFile = new File(savePath + newName + extName);
					System.out.println(newFile.getAbsolutePath());
					try {
						multFile.transferTo(newFile);
						result.setMessage(multFile.getOriginalFilename() + " 上传成功");
						result.setSuccess(success);
					} catch (IllegalStateException e) {
						e.printStackTrace();
						success = false;
					} catch (IOException e) {
						e.printStackTrace();
						success = false;
					}
				}
			}
		} catch(HttpFileException e) {
			e.printStackTrace();
			result.setMessage(e.getErrorMsg().getMsg());
			result.setSuccess(success);
			loggerUtil.error("上传文件异常" + e.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		mav.addObject("jsonData", result);
		loggerUtil.debug("==========上传文件结束==========");
		return mav;
	}

	private static String getImagePath(String filename, String root, int type) throws Exception {
		String[] d = new String[3];
		d[0] = filename.substring(0, 2);
		d[1] = filename.substring(2, 5);
		d[2] = filename.substring(5, 9);
		String path = "";
		if (root.length() != 0) {
			path = root + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator")
					+ FileType.getName(type) + System.getProperty("file.separator") + d[0]
					+ System.getProperty("file.separator") + d[1] + System.getProperty("file.separator") + d[2]
					+ System.getProperty("file.separator");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			return path;
		} else {
			path = System.getProperty("file.separator") + "resources" + System.getProperty("file.separator")
					+ FileType.getName(type) + System.getProperty("file.separator") + d[0]
					+ System.getProperty("file.separator") + d[1] + System.getProperty("file.separator") + d[2]
					+ System.getProperty("file.separator");
			return path;
		}

	}
}
