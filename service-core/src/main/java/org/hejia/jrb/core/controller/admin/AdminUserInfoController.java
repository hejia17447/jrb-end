package org.hejia.jrb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.pojo.query.UserInfoQuery;
import org.hejia.jrb.core.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/userInfo")
public class AdminUserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/list/{page}/{limit}")
    public Result listPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            UserInfoQuery userInfoQuery
    ) {
        Page<UserInfo> userInfoPage = new Page<>(page, limit);
        IPage<UserInfo> pageResult = userInfoService.listPage(userInfoPage, userInfoQuery);
        return Result.success().data("pageModel", pageResult);
    }

}
