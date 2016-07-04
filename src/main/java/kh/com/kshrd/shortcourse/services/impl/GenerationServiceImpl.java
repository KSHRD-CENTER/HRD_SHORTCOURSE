package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.repositories.GenerationRepository;
import kh.com.kshrd.shortcourse.services.GenerationService;

@Service
public class GenerationServiceImpl implements GenerationService{
	
	@Autowired
	private GenerationRepository generationRepository;

	@Override
	public List<Generation> findAllGenerations() throws BusinessException {
		try {
			return generationRepository.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public List<Generation> findAllGenerations(Long courseTypeId) throws BusinessException {
		try {
			return generationRepository.findAllByCourseTypeId(courseTypeId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

}
