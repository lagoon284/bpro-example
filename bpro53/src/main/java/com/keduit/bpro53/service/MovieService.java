package com.keduit.bpro53.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.keduit.bpro53.dto.MovieDTO;
import com.keduit.bpro53.dto.MovieImageDTO;
import com.keduit.bpro53.dto.PageRequestDTO;
import com.keduit.bpro53.dto.PageResultDTO;
import com.keduit.bpro53.entity.Movie;
import com.keduit.bpro53.entity.MovieImage;

public interface MovieService {

	Long register(MovieDTO movieDTO);
	
	PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
	
	MovieDTO getMovie(Long mno); 
	
	default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
		Map<String, Object> entityMap = new HashMap<>();
		
		Movie moive = Movie.builder()
						   .mno(movieDTO.getMno())
						   .title(movieDTO.getTitle())
						   .build();
		
		entityMap.put("movie", moive);
		
		List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
		
		if(imageDTOList != null && imageDTOList.size() > 0) {
			List<MovieImage> movieImageList = imageDTOList.stream()
					.map(movieImageDTO -> {
						
						MovieImage movieImage = MovieImage.builder()
														.path(movieImageDTO.getPath())
														.imgname(movieImageDTO.getImgname())
														.uuid(movieImageDTO.getUuid())
														.movie(moive)
														.build();
						return movieImage;
					}).collect(Collectors.toList());
					
			entityMap.put("imgList", movieImageList);
		}
		
		return entityMap;
	}
	
	default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, double avg, Long reviewCnt) {
		
		MovieDTO movieDTO = MovieDTO.builder()
								.mno(movie.getMno())
								.title(movie.getTitle())
								.regDate(movie.getRegDate())
								.updateDate(movie.getUpdateDate())
								.build();
		
		List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
			if(movieImage != null) {
			return MovieImageDTO.builder().imgname(movieImage.getImgname())
										.path(movieImage.getPath())
										.uuid(movieImage.getUuid())
										.build();
			}
			return null;
			}).collect(Collectors.toList());
		movieDTO.setImageDTOList(movieImageDTOList);
		movieDTO.setAvg(avg);
		movieDTO.setReviewCnt(reviewCnt.intValue());
		
		return movieDTO;
	}
	
}
