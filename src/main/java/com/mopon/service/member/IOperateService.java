package com.mopon.service.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mopon.entity.member.Operate;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.OperateException;

/**
 * <p>Title: 操作管理Service接口</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2013</p>
 * <p>Company:mopon</p>
 * @date 2013年9月22日
 * @author 王丽松
 * @version 1.0
 */
public interface IOperateService {
   
    /**
     * 方法用途: 添加或更新操作,更新操作，只能更新操作名、操作描述和操作请求<br>。<br>
     * 实现步骤: <br>
     * @param operate 操作实体类
     * @throws OperateException
     * @throws SQLException
     */
	public void saveOrUpdateOperate(Operate operate,List<Integer> roleIds) throws OperateException, SQLException;
	
	/**
	 * 方法用途: 批量删除操作<br>
	 * 实现步骤: <br>
	 * @param operate 操作实体类
	 * @throws OperateException
	 * @throws SQLException
	 */
	public void removeOperate(int[] opIds) throws OperateException, SQLException;
	
	/**
	 * 方法用途: 根据指定条件查询操作<br>
	 * 实现步骤: <br>
	 * @param operate 操作实体类
	 * @param pageNo 页号    
	 * @param pageSize 页结果数
	 * @return 返回 List<Operate>结果集
	 * @throws OperateException
	 * @throws SQLException
	 */
	public  PageBean<Operate> findOperate(Operate operate, int pageNo, int pageSize) throws OperateException, SQLException;
	
	/**
	 * 获取操作详情
	 * @param opId 操作Id
	 * @return Operate操作对象
	 */
	public Operate findOperateById(int opId) throws OperateException, SQLException;
	

	/**
	 * 根据角色ID和菜单ID获取对应的操作
	 * @param roleId 角色ID
	 * @param menuId 菜单ID
	 * @return 操作集合
	 * @throws OperateException
	 * @throws SQLException
	 */
	public List<Operate> getOperate(int roleId,int menuId) throws OperateException, SQLException;
	
	   /**
     * 根据角菜单ID获取对应的操作(超级用户权限)
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 操作集合
     * @throws OperateException
     * @throws SQLException
     */
    public List<Operate> getAdminOperate(int menuId) throws OperateException, SQLException;
    /**
     * 方法用途: 根据角色ID获取对应的操作
    * @param userId 用户ID
    * @return 操作集合
    */
   public List<Operate> getOperateByRoleId(@Param("roleId")Integer roleId);
   
   /**
    * 方法用途:查询记录存在条数
   * @param userId 用户ID
   * @return 操作集合
   */
  public Integer getOperate(Operate operate);
  
  /**
   * 方法用途:查询角色操作关联的记录存在条数
  * @param userId 用户ID
  * @return 操作集合
  */
  public Integer queryRoleOperate(Operate operate);
	
}
