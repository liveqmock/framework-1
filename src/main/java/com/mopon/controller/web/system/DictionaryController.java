package com.mopon.controller.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.entity.sys.Dictionary;
import com.mopon.entity.sys.ResultList;
import com.mopon.service.sys.IDictionaryService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends Component {

	@Autowired
	private IDictionaryService dictionaryService;

	@RequestMapping(value = "read")
	public ModelAndView list(ModelAndView mav, Dictionary dictionary) {
		ResultList<Dictionary> resultList = new ResultList<Dictionary>();
		List<Dictionary> dictionarys = null;
		try {

			dictionarys = dictionaryService.getDictionary(dictionary);
			resultList.setResultList(dictionarys);
			resultList.setMessage("操作成功！");
			resultList.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultList.setSuccess(false);
			resultList.setMessage("获取数据异常！");
		}
		mav.addObject("jsonData", resultList);
		return mav;
	}

}
