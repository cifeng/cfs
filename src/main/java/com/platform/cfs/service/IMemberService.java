package com.platform.cfs.service;

import com.platform.cfs.common.response.Response;
import com.platform.cfs.config.database.PageVO;
import com.platform.cfs.entity.SystemUser;

public interface IMemberService {

    /**
     * 创建会员接口
     * @param user 传过来的参数信息
     * @return 响应对象包含错误提示信息
     */
    Response save(SystemUser user);

    /**
     * 修改会员接口
     * @param user 参数
     * @return 响应对象
     */
    Response edit(SystemUser user);

    /**
     * 删除会员
     * @param ids 多个id之间用逗号隔开
     * @return 响应对象
     */
    Response delete(String ids);

    /**
     * 根据主键查询
     * @param id 主键
     * @return 用户对象
     */
    SystemUser queryById(String id);

    PageVO queryByList(SystemUser user);
}
