package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.Generation;

public interface GenerationService {
	public List<Generation> findAllGenerations() throws BusinessException;
}
