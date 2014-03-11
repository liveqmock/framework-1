package com.mopon.controller.web.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.mopon.entity.sys.ResultObject;

@Controller
public class VCodeController extends Component {

	private final String word = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@RequestMapping(value = "/vcode", method = RequestMethod.GET)
	public void viewCode(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BufferedImage img = new BufferedImage(68, 22,
					BufferedImage.TYPE_INT_RGB);
			// 得到该图片的绘图对象
			Graphics g = img.getGraphics();
			Random r = new Random();
			Color c = new Color(200, 150, 255);
			g.setColor(c);
			// 填充整个图片的颜色
			g.fillRect(0, 0, 68, 22);
			// 向图片中输出数字和字母
			StringBuffer sb = new StringBuffer();
			char[] ch = word.toCharArray();
			int index, len = ch.length;
			for (int i = 0; i < 4; i++) {
				index = r.nextInt(len);
				g.setColor(new Color(r.nextInt(88), r.nextInt(188), r
						.nextInt(255)));
				g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
				// 输出的 字体和大小
				g.drawString("" + ch[index], (i * 15) + 3, 18);
				// 写什么数字，在图片 的什么位置画
				sb.append(ch[index]);
			}
			request.getSession()
					.setAttribute(sb.toString().toUpperCase(), true);
			ImageIO.write(img, "JPG", response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg();
			errorMsg.setEid(UUID.randomUUID().toString());
			errorMsg.setCode(ErrorStatus.VCODE_ERROR.value());
			errorMsg.setErrorDate(new Date());
			errorMsg.setMsg("验证码异常");
			errorMsg.setLevel(ErrorLevel.ERROR.value());
			errorMsg.setType(ErrorType.OPERATION_ERROR.value());
		}
	}

	@RequestMapping(value = "/validate/{code}", method = RequestMethod.GET)
	public ModelAndView validateCode(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String code, ModelAndView mav) {
		ResultObject<Object> ro = new ResultObject<Object>();
		try {
			code = code.toUpperCase();
			Boolean isValid = (Boolean) request.getSession().getAttribute(code);
			if (isValid != null && isValid) {
				request.getSession().removeAttribute(code);
				ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				ro.setMessage("验证码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg errorMsg = new ErrorMsg();
			errorMsg.setEid(UUID.randomUUID().toString());
			errorMsg.setCode(ErrorStatus.VCODE_ERROR.value());
			errorMsg.setErrorDate(new Date());
			errorMsg.setMsg("验证码异常");
			errorMsg.setLevel(ErrorLevel.ERROR.value());
			errorMsg.setType(ErrorType.OPERATION_ERROR.value());
		}
		mav.addObject("jsonData", ro);
		return mav;
	}
}
