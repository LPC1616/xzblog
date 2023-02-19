package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AdminUserDto;
import com.xiaozhu.domain.dto.UserDto;
import com.xiaozhu.domain.entity.Role;
import com.xiaozhu.domain.entity.User;
import com.xiaozhu.domain.entity.UserRole;
import com.xiaozhu.domain.vo.*;
import com.xiaozhu.enums.AppHttpCodeEnum;
import com.xiaozhu.exception.SystemException;
import com.xiaozhu.mapper.UserMapper;
import com.xiaozhu.service.RoleService;
import com.xiaozhu.service.UserRoleService;
import com.xiaozhu.service.UserService;
import com.xiaozhu.utils.BeanCopyUtils;
import com.xiaozhu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.xiaozhu.enums.AppHttpCodeEnum.*;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-06 16:21:57
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户的id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllUserByPage(Integer pageNum, Integer pageSize, UserDto userDto) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(userDto.getStatus()), User::getStatus, userDto.getStatus());
        queryWrapper.eq(StringUtils.hasText(userDto.getPhonenumber()), User::getPhonenumber, userDto.getPhonenumber());
        queryWrapper.like(StringUtils.hasText(userDto.getUserName()), User::getUserName, userDto.getUserName());

//        2.分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

//        3.将当前页中的Role对象转换为RoleVo对象
        List<User> users = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
//        4.将LinkVo对象转换为LinkAdminVo对象
        PageVo pageVos = new PageVo(userVos, page.getTotal());
        return ResponseResult.okResult(pageVos);
    }

    @Override
    public ResponseResult addUser(AdminUserDto adminUserDto) {
        //        1.获取到AdminUserDto对象当中的roleIds属性
        List<Long> roleIds = adminUserDto.getRoleIds();
//        2.将AdminUserDto对象转化为User对象
        User user = BeanCopyUtils.copyBean(adminUserDto, User.class);
//        3.判断信息是否为空
        if (!StringUtils.hasText(user.getUserName()) ||
                !StringUtils.hasText(user.getNickName()) ||
                !StringUtils.hasText(user.getPassword()) ||
                !StringUtils.hasText(user.getEmail()) ||
                !StringUtils.hasText(user.getPhonenumber()) ||
                !StringUtils.hasText(user.getStatus()) ||
                !StringUtils.hasText(user.getSex())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
//        4.判断信息是否存在
        if (userNameExist(user.getUserName())){
            throw new SystemException(USERNAME_EXIST);
        }
        if (!judgePhoneNumber(user.getPhonenumber())){
            throw new SystemException(PHONENUMBER_EXIST);
        }
        if (!judgeEmail(user.getEmail())){
            throw new SystemException(EMAIL_EXIST);
        }
//        5.保存用户
        save(user);
//        6.获取到用户id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User getUser = getOne(queryWrapper);

//        7.向sys_user_role表中添加数据
        roleIds.stream()
                .map(roleId -> userRoleService.save
                        (new UserRole(getUser.getId(),roleId)));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
//        1.从SecurityContextHolder当中获取到当前登录用户的id
        Long userId = SecurityUtils.getUserId();
        if (userId == id) {
//            1.1如果是当前登录的用户则不允许删除
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_USER_REFUSE);
        }
//        2.删除用户所对应sys_user_role表中的角色信息
        userRoleService.removeById(id);
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfoById(Long id) {
        //        1.通过id查询用户信息
        User userById = getById(id);
//          1.1将User对象转换为UpdateUserInfoVo对象
        UserVo user = BeanCopyUtils.copyBean(userById, UserVo.class);
//        2.查询用户所具有的角色id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        List<Long> userRoleId = userRoleService.getUserRoleById(id);
//        3.查询所有角色的列表
        List<Role> allRoleList = roleService.getAllRoleList();
//            3.1将List<Role>对象转换为List<RoleVo>对象
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(allRoleList, RoleVo.class);

        UpdateUserVo updateUserVo = new UpdateUserVo(userRoleId, roleVos, user);
        return ResponseResult.okResult(updateUserVo);
    }

    @Override
    public ResponseResult updateUserStatus(UserStatusVo userStatusVo) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, Integer.parseInt(userStatusVo.getUserId()))
                .set(User::getStatus, userStatusVo.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminUpdateUserInfo(UpdateUserInfoRoleIdVo updateUserInfoRoleIdVo) {
        //        1.获取到修改后的用户的roleIds列表
        List<Long> roleIds = updateUserInfoRoleIdVo.getRoleIds();
//        2.将UpdateUserInfoRoleIdVo对象转换为User对象
        User user = BeanCopyUtils.copyBean(updateUserInfoRoleIdVo, User.class);
//          2.1查询当前用户roleIds列表
        List<Long> userRoleById = userRoleService.getUserRoleById(user.getId());
//          2.2遍历修改后的用户的roleIds列表，如果有新增的就添加到sys_user_role表中
        for (Long roleId : roleIds) {
            if (!userRoleById.contains(roleId)) {
                userRoleService.save(new UserRole(user.getId(), roleId));
            }
        }

        for (Long aLong : userRoleById) {
            if(!roleIds.contains(aLong)){
                LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(UserRole::getUserId, updateUserInfoRoleIdVo.getId()).eq(UserRole::getRoleId, aLong);
                userRoleService.remove(queryWrapper);
            }
        }

//        2.根据用户id修改用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, user.getId());
        update(user, queryWrapper);
        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper)>0;
    }

    /**
     * 判断邮箱是否存在
     * @param email
     * @return
     */
    public boolean judgeEmail(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (Objects.isNull(user)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断手机号是否存在
     * @param phonenumber
     * @return
     */
    public boolean judgePhoneNumber(String phonenumber){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getPhonenumber, phonenumber);
        User user = getOne(queryWrapper);
        if (Objects.isNull(user)){
            return true;
        }else{
            return false;
        }
    }

}
