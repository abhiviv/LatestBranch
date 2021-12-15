package com.management.mapperDto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.management.dto.CategoryDto;
import com.management.dto.CountryDto;
import com.management.dto.RegistrationDto;
import com.management.entity.Country;
import com.management.entity.ImageCategory;
import com.management.entity.Registration;
import com.management.entity.State;

@Component
public class RegistrationDtomapper {
	
	public RegistrationDto registrationDto(Registration registration) {
		RegistrationDto registrationDto =new RegistrationDto();
		BeanUtils.copyProperties(registration, registrationDto);
		return registrationDto;
	}

	
	// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}
		
		
		public CategoryDto categoryDto(ImageCategory b) {
			CategoryDto categoryDto=new CategoryDto();
			BeanUtils.copyProperties(b, categoryDto);
			return categoryDto;
		}
		
		
		public CategoryDto categoryDtoconvert(ImageCategory b) throws Exception {
			CategoryDto categoryDto=new CategoryDto();
			BeanUtils.copyProperties(b, categoryDto);
			return categoryDto;
		}
		
		
		private static String encodeFileToBase64Binary(File file) throws Exception{
	        @SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);
	        byte[] bytes = new byte[(int)file.length()];
	        fileInputStreamReader.read(bytes);
	        return new String(Base64.encodeBase64(bytes), "UTF-8");
	    }
		
		
		public CountryDto countryDto(Country country) {
			CountryDto countryDto=new CountryDto();
			Comparator<State> comparator = Comparator.comparing(State::getStateName);
			BeanUtils.copyProperties(country, countryDto);
			countryDto.setState(country.getState().stream().collect(Collectors.toCollection(()-> new TreeSet<>(comparator))));
			return countryDto;	
		}
		
		
}
