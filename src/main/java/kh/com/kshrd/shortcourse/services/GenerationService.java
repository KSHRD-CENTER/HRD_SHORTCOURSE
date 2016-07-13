package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.GenerationFilter;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface GenerationService {
	public List<Generation> findAllGenerations(GenerationFilter filter, Pagination pagination) throws BusinessException;
	public List<Generation> findAllGenerations(Long courseTypeId) throws BusinessException;
	public Generation findGeneration(int id) throws BusinessException;
	public Long addGeneration(Generation generation) throws BusinessException;
	public Long updateGeneration(Generation generation) throws BusinessException;
	public boolean deleteGeneration(int id) throws BusinessException;
}
