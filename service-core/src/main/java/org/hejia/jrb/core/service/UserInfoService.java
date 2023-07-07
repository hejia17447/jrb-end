package org.hejia.jrb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.pojo.query.UserInfoQuery;
import org.hejia.jrb.core.pojo.vo.LoginVO;
import org.hejia.jrb.core.pojo.vo.RegisterVO;
import org.hejia.jrb.core.pojo.vo.UserIndexVO;
import org.hejia.jrb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 注册新用户
     * @param registerVO 注册信息
     */
    void register(RegisterVO registerVO);

    /**
     * 用户登录
     * @param loginVO 登录信息
     * @param ip 请求地址
     * @return 登录结果
     */
    UserInfoVO login(LoginVO loginVO, String ip);

    /**
     * 获取会员用户列表
     * @param pageParam 分页信息
     * @param userInfoQuery 用户查询信息
     * @return 用户列表
     */
    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    /**
     * 锁定与解锁
     * @param id 用户id
     * @param status 需要修改的用户状态
     */
    void lock(Long id, Integer status);

    /**
     * 检查手机号是否注册
     * @param mobile 手机号
     * @return 检查结果
     */
    Boolean checkMobile(String mobile);

    /**
     * 获取用户个人信息
     * @param userId 用户id
     * @return 个人信息
     */
    UserIndexVO getIndexUserInfo(Long userId);
}
