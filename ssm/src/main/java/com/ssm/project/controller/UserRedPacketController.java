package com.ssm.project.controller;

import com.ssm.project.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {

	@Autowired
	private UserRedPacketService userRedPacketService = null;

	@RequestMapping(value = "/toGrapRedPacket")
	public String toGrapRedPacket() {
		return "grap";
	}

	@RequestMapping(value = "/grapRedPacket")
	@ResponseBody
	public Map<String, Object> grapRedPacket(Long redPacketId, Long userId) {
		int result = userRedPacketService.grapRedPacket(redPacketId, userId);
		Map<String, Object> retMap = new HashMap<String, Object>();
		boolean flag = result > 0;
		retMap.put("success", flag);
		retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
		return retMap;
	}

	@RequestMapping(value = "/grapRedPacketForUpdate")
	@ResponseBody
	public Map<String, Object> grapRedPacketForUpdate(Long redPacketId, Long userId) {
		int result = userRedPacketService.getRedPacketForUpdate(redPacketId, userId);
		return null;
	}

	
	@RequestMapping(value = "/grapRedPacketForVersion")
	@ResponseBody
	public Map<String, Object> grapRedPacketForVersion(Long redPacketId, Long userId) {
		int result = userRedPacketService.grapRedPacketForVersion(redPacketId, userId);
		Map<String, Object> retMap = new HashMap<String, Object>();
		boolean flag = result > 0;
		retMap.put("success", flag);
		retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
		return retMap;
	}
//
//	@RequestMapping(value = "/grapRedPacketByRedis")
//	@ResponseBody
//	public Map<String, Object> grapRedPacketByRedis(Long redPacketId, Long userId) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Long result = userRedPacketService.grapRedPacketByRedis(redPacketId, userId);
//		boolean flag = result > 0;
//		resultMap.put("result", flag);
//		resultMap.put("message", flag ? "抢红包成功": "抢红包失败");
//		return resultMap;
//	}
}
