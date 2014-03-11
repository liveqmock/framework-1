package com.mopon.dao.master.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mopon.entity.member.Operate;
import com.mopon.entity.member.Role;
import com.mopon.exception.OperateException;

/**
 * <p>Title: 操作管理Dao接口</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2013</p>
 * <p>Company:mopon</p>
 * @date 2013年9月22日
 * @author 王丽松
 * @version 1.0
 */
@Repository
public interface IOperateDao{

    /**
     * 方法用途: 新增操作<br>
     * 实现步骤: <br>
     * @param entity 操作实体类
     * @throws DataAccessException
     */
	public void save(Operate entity);
	
	/**
	 * 方法用途: 添加角色操作关联表<br>
     * 实现步骤: <br>
	 * @param entity 操作实体类
	 * @throws DataAccessException
	 */
	public void saveRoleOperate(Operate entity);
	
	/**
	 * 方法用途: 查询角色操作关联表,用来判断是否有关联<br>
     * 实现步骤: <br>
	 * @param entity 操作实体类
	 * @throws DataAccessException
	 */
	public Integer queryRoleOperate(Operate entity);
	
	
	/**
	 * 方法用途: 根据操作ID删除操作<br>
     * 实现步骤: <br>
	 * @param entity 操作实体类
	 * @throws DataAccessException
	 */
	public void removeByOpId(Integer opId);  
	
	/**
	 * 方法用途: 根据菜单ID删除操作<br>
     * 实现步骤: <br>
	 * @param menuId 菜单ID
	 * @throws DataAccessException
	 */
    public void removeByMenuID(int menuId);  
	
	/**
	 * 方法用途: 更新操作<br>
     * 实现步骤: <br>
	 * @param entity 操作实体类
	 * @throws DataAccessException
	 */
	public void update(Operate entity);
	

	/**
     * 方法用途: 删除菜单多余操作<br>
     * 实现步骤: <br>
	 * @param menuId
	 */
	public void clearOpMenuId(@Param("menuId") Integer menuId, @Param("opIds") List<Integer> opIds);
	
	/**
     * 方法用途: 分页查询<br>
     * 实现步骤: <br>
	 * @param entity 操作实体类
	 * @param start 开始位置
	 * @param pageSize 页结果数
	 * @return 返回查询的列表List<Operate>
	 * @throws DataAccessException
	 */
	public List<Operate> queryOperateForPages( @Param("entity") Operate entity, @Param("start") int start, @Param("pageSize") int pageSize);
	
	/**
	 * 方法用途: 分页的总记录数<br>
     * 实现步骤: <br>
	 * @param entity 操作实体类
	 * @return 返回查询的记录数
	 * @throws DataAccessException
	 */
	public Integer queryOperateForCount(Operate entity);
	
	 /**
     *  方法用途: 查询操作,根据菜单ID获取所有对应的操作<br>
     *  实现步骤: <br>
	  * @param menuId 菜单ID
	  * @return 返回查询的列表List<Operate>
	  * @throws DataAccessException
	  */
    public List<Operate> queryOperateByMenuId(Integer menuId);
	
    /**
     * 方法用途: 根据操作ID的集合删除对应的角色操作关联表<br>
     * 实现步骤: <br>
     * @param opIds 权限ID的集合
     * @throws OperateException
     * @throws DataAccessException
     */
    public void removeRoleOperateByInteger(List<Integer> opIds);
    
    /**
     * 方法用途: 根据操作id的集合删除对应的操作集合<br>
     * 实现步骤: <br>
     * @param opIds 权限ID的集合
     * @throws OperateException
     * @throws DataAccessException
     */
    public void removeOperateByInteger(List<Integer> opIds);
    
    /**
     * 方法用途: 根据操作id删除操作角色关联表<br>
     * 实现步骤: <br>
     * @param operate 操作实体类
     */
    public void removeRoleOperateByOpId(Integer opId);
    
    /**
     * 方法用途: 根据菜单Id删除操作角色关联表<br>
     * 实现步骤: <br>
     * @param operate 操作实体类
     */
    public void removeRoleOperateByMenuId(Integer menuId);
    
    /**
     * 方法用途: 根据菜单Id和角色Id删除操作角色关联表<br>
     * 实现步骤: <br>
     * @param operate 操作实体类
     */
    public void removeRoleOperate(@Param("menuId") Integer menuId,@Param("roleId") Integer roleId);
   
    /**
     * 方法用途:  根据操作ID获取操作对象
     * @param opId 操作Id
     * @return Operate操作对象
     */
    public Operate findOperateById(Integer opId);

    
    /**
     * 方法用途: 根据菜单id查询可分配的操作（没有设置父菜单的操作和对应的菜单ID的操作）
     * @param menuId
     * @return
     */
    public List<Operate> queryByMenuId(Integer menuId);
    
    /**
      * 方法用途: 根据角色ID和菜单ID获取对应的操作
     * @param userId 用户ID
     * @param menuId 菜单ID
     * @return 操作集合
     */
    public List<Operate> getOperate(@Param("roleId") int roleId, @Param("menuId") int menuId) ;
    
    /**
     * 方法用途: 根据角色ID获取对应的操作
    * @param userId 用户ID
    * @return 操作集合
    */
   public List<Operate> getOperateByRoleId(@Param("roleId")Integer roleId);
    
}
