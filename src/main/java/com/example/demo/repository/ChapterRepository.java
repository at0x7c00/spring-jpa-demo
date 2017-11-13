package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long>{

}
