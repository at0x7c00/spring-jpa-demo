package com.example.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.IChapterService;

@RestController
@RequestMapping("chapter")
public class ChapterController {

	@Resource
	private IChapterService chapterService;
	
	@RequestMapping("list")
	public void list(HttpServletRequest request) {
		request.setAttribute("list", chapterService.list());
	}
	
}
