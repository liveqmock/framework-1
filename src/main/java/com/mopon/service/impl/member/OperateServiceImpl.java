package com.mopon.service.impl.member;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.member.IOperateDao;
import com.mopon.dao.master.member.IRoleDao;
import com.mopon.entity.member.Operate;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.OperateException;
import com.mopon.service.impl.sys.BaseServiceImpl;
import com.mopon.service.member.IOperateService;

/**
 * <p>Title: 操作管理Service接口实现类</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2013</p>
 * <p>Company:mopon</p>
 * @date 2013年9月22日
 * @author 王丽松
 * @version 1.0
 */
@Service("operateService")
public class OperateServiceImpl extends BaseServiceImpl implements IOperateService {
    
    @Autowired
    private IOperateDao operateDao;
    
    @Autowired
    private IRoleDao roleDaoImpl;
    
     @Override
     @Transactional
    public void removeOperate(int[] opId) throws OperateException, DataAccessException {
         for(int id :opId){
             operateDao.removeRoleOperateByOpId(id);
             operateDao.removeByOpId(id); 
         }   
    }
     
    @Override
    public PageBean<Operate> findOperate(Operate operate, int pageNo, int pageSize) throws OperateException,
            DataAccessException {
                
        //创建分页对象
        PageBean<Operate> pageBean = new PageBean<Operate>();
        
        //设置当前页
        pageBean.setCurrentPage(pageNo);
        if(pageNo > 0) {
            pageNo = pageNo - 1;
        }
        
        //设置开始位置
        int startPage = pageNo * pageSize;
        
        //返回列表
        List<Operate> groupList = operateDao.queryOperateForPages(operate,startPage,pageSize);
        
        //得到结果总数
        int count = operateDao.queryOperateForCount(operate);
        
        //放入分页容器
        pageBean.setDataList(groupList);
        
        //设置页大小
        pageBean.setPageSize(pageSize);
        
        //总记录数
        pageBean.setRecordCount(count);
        
        return pageBean;
    }

    @Override
    @Transactional
    public void saveOrUpdateOperate(Operate operate,List<Integer> roleIds) throws OperateException,
            DataAccessException {
        
        //如果是新增
        if(operate.getOpId()==null){
        	
            //添加操作
        	operateDao.save(operate);
        	
        	//添加角色与操作的关联
            if(operate.getRoleId()!=null){
        		for(Integer roleId:roleIds){
        			operate.setRoleId(roleId);
        			//操作角色关联(父角色)
                	operateDao.saveRoleOperate(operate);
        		}
            	
            }
            
            
        }else{   
            
            //修改操作
            operateDao.update(operate);
        }   
    }

    @Override
    public Operate findOperateById(int opId) {
        return  operateDao.findOperateById(opId);
    }

    @Override
    public List<Operate> getOperate(int roleId, int menuId) throws OperateException, SQLException {
        return operateDao.getOperate(roleId, menuId);
    }


    @Override
    public List<Operate> getAdminOperate(int menuId) throws OperateException, SQLException {
        return operateDao.queryOperateByMenuId(menuId);
    }
    
	@Override
	public List<Operate> getOperateByRoleId(Integer roleId) {
		return operateDao.getOperateByRoleId(roleId);
	}

	@Override
	public Integer getOperate(Operate operate) {
		return operateDao.queryOperateForCount(operate);
	}

	@Override
	public Integer queryRoleOperate(Operate operate) {
		return operateDao.queryRoleOperate(operate);
	}
}
