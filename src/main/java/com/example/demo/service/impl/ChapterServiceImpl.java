package com.example.demo.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Chapter;
import com.example.demo.repository.ChapterRepository;
import com.example.demo.service.IChapterService;

@Service
public class ChapterServiceImpl implements IChapterService{

	@Autowired
	private ChapterRepository chapterRepository;
	
	@Override
	public List<Chapter> list() {
		return chapterRepository.findAll();
	}

}
