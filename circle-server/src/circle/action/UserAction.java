package circle.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;

import circle.po.User;
import circle.po.Class;
import circle.po.Comment;
import circle.po.Dynamic;
import circle.po.Notice;
import circle.service.UserService;

public class UserAction {
	private UserService userService;
	private String phone;
	private String password;
	private User user;
	private Class aclass;
	private Dynamic dynamic;
	private Comment comment;
	private Notice notice;
	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/** 这里的名字和html的名字必须对称 */
	private File upload;
	/** 要上传的文件类型 */
	private String uploadContentType;
	/** 文件的名称 */
	private String uploadFileName;
	private static final String[] types = { "application/x-zip-compressed", "ZIP", "image/pjpeg", "image/x-png",
			"image/jpeg", "image/png" };

	public boolean filterType() {
		boolean isFileType = false;
		String fileType = getUploadContentType();
		System.out.println(fileType);
		for (String type : types) {
			if (type.equals(fileType)) {
				isFileType = true;
				break;
			}
		}
		return isFileType;
	}


	public String upload() {
		return "success";
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public static String[] getTypes() {
		return types;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Class getAclass() {
		return aclass;
	}

	public void setAclass(Class aclass) {
		this.aclass = aclass;
	}

	private Map<String, Object> json;

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		json = userService.login(phone, password);
		return "success";
	}

	public String register_p() {
		json=userService.register_tp(user);
		return "success";
	}

	public String register_t() {
		json=userService.register_tp(user);
		return "success";
	}

	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}
	
	public String publishNotice() {
		json = new HashMap<String, Object>();
		json.put("result", userService.publishNotice(notice));
		return "success";
	}

	public String register_m() {
		json = userService.register_m(user, aclass);
		return "success";
	}

	public long getFileSize(File f) throws Exception {
		return f.length();
	}

	public String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	public Dynamic getDynamic() {
		return dynamic;
	}
	
	public String getDynamics() {
		json=userService.getDynamic(aclass.getCode()+"");
		return "success";
	}
	
	public String getNotices() {
		json=userService.getNotice(aclass.getCode()+"");
		return "success";
	}
	public Notice getNotice() {
		return notice;
	}

	public String publishDynamic() {
		json = new HashMap<String, Object>();
		json.put("result", userService.publishDynamic(dynamic));
		return "success";
	}
	
	public String publishComment() {
		json = new HashMap<String, Object>();
		json.put("result", userService.publishComment(comment));
		return "success";
	}
	
	public String addPraise() {
		json = new HashMap<String, Object>();
		json.put("result", userService.addPraise(dynamic));
		return "success";
	}
	
	public String getAllUsers() {
		json = userService.getAllUsers(aclass.getCode()+"");
		return "success";
	}
	public String delUser() {
		json = new HashMap<String, Object>();
		json.put("result", userService.delUser(user));
		return "success";
	}
	
	public String delDynamic() {
		json = new HashMap<String, Object>();
		json.put("result", userService.delDynamic(dynamic));
		return "success";
	}
	public String updateClass() {
		json = new HashMap<String, Object>();
		json.put("result", userService.updateClass(user));
		return "success";
	}

	public String uploadImg() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String ct = request.getHeader("Content-Type");
		System.out.println("Content-Type=" + ct);
		String result = "unknow error";
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		if (!filterType()) {
			System.out.println("文件类型不正确");
			ServletActionContext.getRequest().setAttribute("typeError", "您要上传的文件类型不正确");

			result = "error:" + getUploadContentType() + " type not upload file type";
		} else {
			System.out.println("当前文件大小为：" + FormetFileSize(getFileSize(getUpload())));
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				// 保存文件那一个路径
				fos = new FileOutputStream(
						"E:\\SoftWare\\Java\\workspace\\circle\\WebContent\\images\\dynamic\\" + getUploadFileName());
				System.out.println("\\" + getUploadFileName());
				fis = new FileInputStream(getUpload());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				result = "success";
			} catch (Exception e) {
				result = "failed";
				e.printStackTrace();
			} finally {
				fos.close();
				fis.close();
			}
		}
		json = new HashMap<String, Object>();
		json.put("result", result);
		return "success";
	}
}
