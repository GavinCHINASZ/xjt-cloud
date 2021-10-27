package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.Inventory;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName InventoryDao
 * @Description 库存管理
 * @Author wangzhiwen
 * @Date 2020/8/20 16:16
 **/
@Repository
public interface InventoryDao {
    /**
     * @param inventory
     * @return List<Inventory>
     * @Description 查询库存信息列表
     * @author wangzhiwen
     * @date 2020/9/7 15:23
     */
    List<Inventory> findInventoryList(Inventory inventory);

    /**
     * @param inventory
     * @return Integer
     * @Description 查询库存信息列表总数
     * @author wangzhiwen
     * @date 2020/9/7 15:23
     */
    Integer findInventoryListTotalCount(Inventory inventory);

    /**
     * @return List<PutStorageProduct>
     * @Description 查询未激活移动物联卡列表
     * @author wangzhiwen
     * @date 2020/9/8 17:16
     */
   List<PutStorageProduct> findOneLinkCardNotActiveList(PutStorageProduct putStorageProduct);

    /**
     * @param list
     * @return
     * @Description 批量修改物联卡状态
     * @author wangzhiwen
     * @date 2020/9/9 10:08
     */
   void modifyCardStatusByList(List<PutStorageProduct> list);

}
