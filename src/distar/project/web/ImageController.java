package distar.project.web;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import distar.project.service.ParamImage;


public class ImageController extends UsableController {
	private ParamImage paramImage;

	public void setParamImage(ParamImage paramImage) {
		this.paramImage = paramImage;
	}

	public ModelAndView about(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		img.append(request.getSession().getAttribute("schoolId"));
		img.append(File.separator).append("etc").append(File.separator);
		img.append("about.jpg");

		File fileImage = new File(img.toString());
		if (!fileImage.exists()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("about.jpg");
			fileImage = new File(img.toString());
		}

		// logger.debug(imgFile);

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}

	public ModelAndView header(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		img.append(request.getSession().getAttribute("schoolId"));
		img.append(File.separator).append("etc").append(File.separator);
		img.append(paramImage.getHeader());

		File fileImage = new File(img.toString());
		if (!fileImage.exists()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("Header.jpg");
			fileImage = new File(img.toString());
		}

		// logger.debug(imgFile);

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}

	public ModelAndView logo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		img.append(request.getParameter("schoolId")).append(File.separator);
		img.append("etc").append(File.separator).append("logo.jpg");

		File fileImage = new File(img.toString());
		if (!fileImage.exists()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("logo.jpg");
			fileImage = new File(img.toString());
		}

		// logger.debug(imgFile);

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}

	public ModelAndView bg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		img.append(request.getParameter("schoolId"));
		img.append(File.separator).append("etc");
		img.append(File.separator).append(paramImage.getBg());

		File fileImage = new File(img.toString());
		if (!fileImage.exists()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append(paramImage.getBg());
			fileImage = new File(img.toString());
		}

		// logger.debug(imgFile);

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}

	public ModelAndView user(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		if (request.getParameter("schoolId") != null)
			img.append(request.getParameter("schoolId"));
		else
			img.append(request.getSession().getAttribute("schoolId"));
		img.append(File.separator);

		String positionName = request.getParameter("positionName");
		if (positionName.startsWith("student")) {
			img.append(paramImage.getStuDir()).append(File.separator);
			img.append(request.getParameter("fileName"));
		} else {
			img.append(paramImage.getEmpDir()).append(File.separator);
			img.append(request.getParameter("fileName"));
		}

		File fileImage = new File(img.toString());
		if (!fileImage.exists() || fileImage.isDirectory()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("unknown-person.jpg");
			fileImage = new File(img.toString());
		}
		// logger.debug(img.toString());

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}

	public ModelAndView getSlider(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		img.append(request.getParameter("schoolId")).append(File.separator);
		img.append(paramImage.getSliderDir()).append(File.separator);
		img.append(request.getParameter("fileName"));

		File fileImage = new File(img.toString());

		if (!fileImage.exists()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("about.jpg");
			fileImage = new File(img.toString());
		}

		// logger.debug(imgFile);

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}
	
	
	public ModelAndView behavior(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		if (request.getParameter("schoolId") != null)
			img.append(request.getParameter("schoolId"));
		else
			img.append(request.getSession().getAttribute("schoolId"));
		img.append(File.separator);


			img.append(paramImage.getBehaviorDir()).append(File.separator);
			img.append(request.getParameter("fileName"));
			System.out.println(img);
		

		File fileImage = new File(img.toString());
		if (!fileImage.exists() || fileImage.isDirectory()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("unknown-person.jpg");
			fileImage = new File(img.toString());
		}
		// logger.debug(img.toString());

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}
	
	public ModelAndView voting(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer img = new StringBuffer();
		
		img.append(paramImage.getPath()).append(File.separator);
		img.append(File.separator);


			img.append(paramImage.getVotingDir()).append(File.separator);
			img.append(request.getParameter("fileName"));
			
			System.out.println(img);
		

		File fileImage = new File(img.toString());
		if (!fileImage.exists() || fileImage.isDirectory()) {
			img = new StringBuffer();
			img.append(paramImage.getPath()).append(File.separator);
			img.append("common").append(File.separator).append("etc");
			img.append(File.separator).append("unknown-bg.jpg");
			fileImage = new File(img.toString());
		}
		// logger.debug(img.toString());

		BufferedImage buf = ImageIO.read(fileImage);
		ImageIO.write(buf, "JPG", response.getOutputStream());
		return new ModelAndView("image");
	}
	
}
