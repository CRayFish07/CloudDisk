package com.cloudDisk.controller;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.cloudDisk.entity.User;
import com.cloudDisk.entity.Userfile;
import com.cloudDisk.page.PageResult;
import com.cloudDisk.service.FileService;
import com.cloudDisk.utils.PathConvert;
import com.cloudDisk.utils.QueryHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NetDiskAction extends ActionSupport {
	
	private static int DEFAULT_PAGE_SIZE=10;
	@Resource
	FileService fileService;

	/**
	 * �ض���action ִ��list() ���� ��ת�����ļ��б����
	 * @return
	 */
	public String toListUI() {
		return "list";
	}

	
	
	/**
	 * ǰ���ļ��ϴ�����
	 * @return
	 */
	public String toFileUploadUI() {
		return "fileUploadUI";
	}

	
	
	/**
	 * �ļ�����������ģ������
	 * @return
	 */
	private String searchFile(){
		// userFile�����Ѿ���ֵ��
		return "";
	}
	
	
	/**
	 * ɾ���ļ�
	 * @return
	 */
	public String deleteFile() {
		// ��һ��ɾ���������ϵ��ļ�
		if (userFile != null) {
			userFile = fileService.findById(userFile.getFileId());
			String path = userFile.getFileUrl();
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}else{
				System.out.println("----------------�ļ�������------------");
			}
			// �ڶ����Ƴ����ݿ��еļ�¼
			fileService.delete(userFile.getFileId());
		}
		return toListUI();
	}

	
	/**
	 * �����ļ�
	 * @return
	 */
	public String downloadFile() {
		// ��һ������ѯ��Ŀ���ļ������ص�ַ
		userFile = fileService.findById(userFile.getFileId());
		return "download";
	}

	/**
	 * �ļ��ϴ�
	 * @return
	 * @throws Exception
	 */
	public String fileUpload() throws Exception {

		// ���ļ��������Ϣ�洢�����ݿ���
		user = (User) ActionContext.getContext().getSession().get("userInfo");
		if (userFile != null) {
			String userName = user.getUserName();
			// �������������ļ����·�� ·����Ϊ"/upload/�û���/�ļ���"
			String filepath = ServletActionContext.getServletContext()
					.getRealPath("/upload" + "/" + userName);
			// ����Ŀ���ļ�����
			File desFile = new File(filepath, userfileFileName);
			// ֱ�ӿ���
			FileUtils.copyFile(userfile, desFile);
			// ����ʵ��׼���������ݿ�
			if (userFile.getFileName().equals("")) {
				userFile.setFileName(userfileFileName);
			}
			userFile.setFileUrl(desFile.getAbsolutePath());
			userFile.setFileSize((float) userfile.length() / 1024); // ת��Ϊ��kbΪ��λ�ٽ��д洢
			 Date date=new Date();
		    Timestamp timestamp=new Timestamp(date.getTime());
			userFile.setUploadDate(timestamp); // �����ʱ������پ�ȷһ��
			userFile.setUser(user);
			fileService.save(userFile);
		}
		return toListUI();
	}

	/**
	 * �б�չʾ
	 * @return
	 */
	public String list() {
		System.out.println("ִ�б��-----");
		
		user = (User) ActionContext.getContext().getSession().get("userInfo");
		QueryHelper helper = new QueryHelper(Userfile.class, "userfile");
		// �жϵ�ǰ�ǲ�����ִ�������Ĳ���
		if(userFile!=null&&!userFile.getFileName().equals("")){
			// ˵����ǰ���ж������������
			helper.setWhereClause("fileName like ?", "%"+userFile.getFileName()+"%");
		}
		
		// �����ݿ��ѯ��ָ���û��������ϴ��ļ�
		
		helper.setWhereClause("userId=?", user.getUserId());
		helper.setOrderByClause(QueryHelper.ORDER_BY_DESC,"uploadDate"); //����ʱ�併������
		// ����1����ѯ���� ����2����ǰҳ�� ����3��ҳ��С[�����ȹ̶�ÿҳ��ʾ10������]
		
		System.out.println("ҳ��"+getPageNo());
		pageResult = fileService.getPageResult(helper, getPageNo(), DEFAULT_PAGE_SIZE);
		return "listUI";
	}

	
	
	
//--------------------------------------------- get set ������-------------------------------------
	
	private User user;
	// �����ļ��ϴ���ǰ������
	private File userfile;
	private String userfileFileName;
	private String userfileContentType;
	PageResult pageResult;
	// ���� Userfile ���� ����ǰ�˴��ݹ����Ĳ�����ֵ
	private Userfile userFile;
    private int pageNo; //����ǰ̨���ݹ�����ҳ��
	private String downloadFileName;

	
	
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}



	// ʵ��struts���ļ����صķ���
	public InputStream getAttrInputStream() {
		// �õ�����·��
		String path = userFile.getFileUrl(); 
		// �����ļ���
		setDownloadFileName(path.substring(path.lastIndexOf("\\")+1));
	    // ��ȡ����·����ȡ�����·��
		String relativePath=PathConvert.convertpath(path);		
	
		return ServletActionContext.getServletContext().getResourceAsStream(relativePath);
	}

	public String getDownloadFileName() {
		try {
			downloadFileName = URLEncoder.encode(downloadFileName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downloadFileName;
	}

	// �����ļ����ı�������
	public void setDownloadFileName(String downloadFileName) {
		try {
			downloadFileName = new String(
					downloadFileName.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		this.downloadFileName = downloadFileName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	public void setUserfileContentType(String userfileContentType) {
		this.userfileContentType = userfileContentType;
	}

	public void setUserfileFileName(String userfileFileName) {
		this.userfileFileName = userfileFileName;
	}

	public void setUserfile(File userfile) {
		this.userfile = userfile;
	}

	public File getUserfile() {
		return userfile;
	}

	public Userfile getUserFile() {
		return userFile;
	}

	public void setUserFile(Userfile userFile) {
		this.userFile = userFile;
	}

}
