package com.smart.lajim.controller;

import com.smart.lajim.domain.Resource;
import com.smart.lajim.domain.Role;
import com.smart.lajim.service.ResourceService;
import com.smart.lajim.service.RoleService;
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
import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("role:view")
    @RequestMapping(value = "/roleManage.html", method = RequestMethod.GET)
    public String list() {
        return "role/list";
    }

    @RequestMapping(value = "/listRole.html", method = RequestMethod.GET)
    @ResponseBody
    public List showCreateForm(Model model) {
        return roleService.findAll();
    }

    @RequestMapping(value = "listResources.html")
    @ResponseBody
    public List listResources(){
        List<Resource> resources = resourceService.findAll();
        List list = new ArrayList();
        List menuList = new ArrayList();
        List buttonList = new ArrayList();
        TreeNode node = new TreeNode();
        for (int i = 0;i < resources.size();i++){
            node = new TreeNode();
            Resource resource = resources.get(resources.size() - i -1);
            if(resource.getType() == Resource.ResourceType.menu && !resource.isRootNode()){
                node.setId(resource.getId().toString());
                node.setText(resource.getName());
                node.setChildren(buttonList);
                menuList.add(node);
                buttonList = new ArrayList();
            }
            if (resource.getType() == Resource.ResourceType.button){
                node.setId(resource.getId().toString());
                node.setText(resource.getName());
                buttonList.add(node);
            }
            if (resource.isRootNode()){
                Collections.reverse(menuList);
                node.setId(resource.getId().toString());
                node.setText(resource.getName());
                node.setChildren(menuList);
            }
        }
        list.add(node);
        return list;
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "/createRole.html", method = RequestMethod.POST)
    @ResponseBody
    public Map create(HttpServletRequest request, HttpServletResponse response) {
        String role = request.getParameter("role");
        String description = request.getParameter("description");
        String[] resourcesStr = request.getParameter("resourceIds").split(",");
        List<Long> resourceIds = new ArrayList<Long>();
        for (int i = 0;i < resourcesStr.length;i++){
            resourceIds.add(Long.parseLong(resourcesStr[i]));
        }
        Role role1 = new Role();
        role1.setRole(role);
        role1.setDescription(description);
        role1.setResourceIds(resourceIds);
        role1.setAvailable(true);
        Map result = new HashMap();
        try {
            roleService.createRole(role1);
            result.put("success","true");
            result.put("msg","添加成功了！！");
        }catch (Exception e){
            result.put("success","false");
            result.put("msg","添加失败了！！");
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("op", "修改");
        return "role/edit";
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Role role, RedirectAttributes redirectAttributes) {
        roleService.updateRole(role);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/role";
    }


    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/deleteRole.html")
    @ResponseBody
    public  Map<String,Object> deleteRole(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            roleService.deleteRole(id);
            result.put("success","true");
            result.put("msg","删除成功");
        }catch (Exception e){
            result.put("success","false");
            result.put("msg","删除失败");
        }
        return result;
    }

    private void setCommonData(Model model) {
        model.addAttribute("resourceList", resourceService.findAll());
    }

}
