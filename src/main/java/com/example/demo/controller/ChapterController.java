package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Chapter;
import com.example.demo.service.IChapterService;

@RestController
@RequestMapping("chapter")
public class ChapterController {

	@Resource
	private IChapterService chapterService;
	
	@RequestMapping("list")
	public List<Chapter> list(HttpServletRequest request) {
		return chapterService.list();
	}
	
}
