package com.smart.lajim.controller;


import com.smart.lajim.domain.Role;
import com.smart.lajim.domain.User;
import com.smart.lajim.service.RoleService;
import com.smart.lajim.service.UserService;
import com.smart.lajim.util.TreeNode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("user:view")
    @RequestMapping(value = "/userManage.html", method = RequestMethod.GET)
    public String userManage() {
//        model.addAttribute("userList", userService.findAll());
        return "user/list";
    }

    @RequestMapping(value = "/listUser.html", method = RequestMethod.GET)
    @ResponseBody
    public List listUser() {
        return userService.findAll();
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/createUser.html", method = RequestMethod.POST)
    @ResponseBody
    public Map create(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] rolesStr = request.getParameter("roleIds").split(",");
        List<Long> rolesId = new ArrayList<Long>();
        for (int i = 0;i < rolesStr.length;i++){
            rolesId.add(Long.parseLong(rolesStr[i]));
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoleIds(rolesId);
        user.setLocked(false);
        Map result = new HashMap();

        try {
            userService.createUser(user);
            result.put("success","true");
            result.put("msg","添加成功了！！");
        }catch (Exception e){
            result.put("success","false");
            result.put("msg","添加失败了！！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/listRoles.html")
    @ResponseBody
    public List listRoles() {
        List<Role> roles = new ArrayList();
        roles = roleService.findAll();
        List list = new ArrayList();
        for (Role role:roles) {
            TreeNode node = new TreeNode();
            node.setId(role.getId().toString());
            node.setText(role.getDescription());
            list.add(node);
        }
        return list;

//        for (Object role:roles) {
//            System.out.println("#" + role);
//        }
//        userService.createUser(user);
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update.html", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update.html", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/deleteUser.html")
    @ResponseBody
    public Map<String,Object> deleteUser(HttpServletRequest request, HttpServletResponse response)throws Exception{
        long id = Long.parseLong(request.getParameter("id"));
        System.out.println(id);
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            userService.deleteUser(id);
            result.put("success","true");
            result.put("msg","删除成功");
        }catch (Exception e){
            result.put("success","false");
            result.put("msg","删除失败");
        }
        return result;
    }


    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword.html", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword.html", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    private void setCommonData(Model model) {
        model.addAttribute("roleList", roleService.findAll());
    }
}
