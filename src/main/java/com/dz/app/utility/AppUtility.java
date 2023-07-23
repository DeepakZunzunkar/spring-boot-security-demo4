package com.dz.app.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class AppUtility {

	public static DecimalFormat decif = new DecimalFormat("$#0.00");

	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//	in windows single \ are not allowed to access folder
	public static final String UPLOAD_DIR = "D:\\STS_workspace\\spring-boot-rest-data-jpa-demo2\\src\\main\\resources\\static\\images";

	// in linux slash are like this '/'

	public static String getDoubleValueFormated(Double val) {

		return decif.format(val);
	}

	public static boolean fileuploadHelper(MultipartFile file) {

		boolean status = false;

		try {

			/*
			 * //read data InputStream is = file.getInputStream(); byte data[] =new
			 * byte[is.available()]; is.read(data);
			 * 
			 * //write FileOutputStream fos = new
			 * FileOutputStream(UPLOAD_DIR+file.getOriginalFilename()); fos.write(data);
			 * 
			 * fos.flush(); fos.close();
			 */

			// using nio package

			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			status = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail to upload... .");
		}
		return status;
	}

	public static Boolean dynamicPathUpload(MultipartFile file) {
		
		boolean status = false;
		try {

//		it give path upto src/main/resources
			final String TARGETED_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();

			Files.copy(file.getInputStream(), Paths.get(TARGETED_DIR + File.separator + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			status = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail to upload... .");
		}
		return status;
	}

}
