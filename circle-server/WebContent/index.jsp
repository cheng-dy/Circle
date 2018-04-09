<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
</head>
<body>
	测试登录
	<form action="user_login.action" method="post">
		<table width="298" border="0" align="center" cellpading="2"
			cellspacing="1">
			<tr>
				<td align="right">手机号：</td>
				<td align="left"><input type="text" name="phone"></td>
			</tr>
			<tr>
				<td align="right">密码：</td>
				<td align="left"><input type="text" name="password"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="登录">
					&nbsp; <input type="reset" value="取消"></td>
			</tr>
		</table>
	</form>
	测试家长注册
	<s:form action="user_register_p.action" method="post">
		<s:textfield name="user.username" label="用户名" />
		<s:textfield name="user.phoneNumber" label="手机号" />
		<s:password name="user.password" label="密码" />
		<s:textfield name="user.characters" label="身份" />
		<s:textfield name="user.childName" label="孩子姓名" />
		<s:textfield name="user.classID" label="班级代号" />
		<s:submit value="注册" />
		<s:reset value="重置" />
	</s:form>
	测试任课老师注册
	<s:form action="user_register_t.action" method="post">
		<s:textfield name="user.username" label="用户名" />
		<s:textfield name="user.phoneNumber" label="手机号" />
		<s:password name="user.password" label="密码" />
		<s:textfield name="user.characters" label="身份" />
		<s:textfield name="user.classID" label="班级代号" />
		<s:submit value="注册" />
		<s:reset value="重置" />
	</s:form>
	测试班主任注册
	<s:form action="user_register_m.action" method="post">
		<s:textfield name="user.username" label="用户名" />
		<s:textfield name="user.phoneNumber" label="手机号" />
		<s:password name="user.password" label="密码" />
		<s:textfield name="user.characters" label="身份" />
		<s:textfield name="aclass.className" label="班级" />
		<s:textfield name="aclass.school" label="学校" />
		<s:submit value="注册" />
		<s:reset value="重置" />
	</s:form>
	测试上传图片
	<form action="upload.action" method="post"
		enctype="multipart/form-data">
		选择文件： <input type="file" name="upload" /> <br> <input type="submit"
			value="上传" />
	</form>
	测试动态的获取
	<s:form action="user_getDynamics.action" method="post">
		<s:textfield name="aclass.code" label="班级号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试所有人员的获取
	<s:form action="user_getAllUsers.action" method="post">
		<s:textfield name="aclass.code" label="班级号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试人员的删除
	<s:form action="user_delUser.action" method="post">
		<s:textfield name="user.userID" label="人员编号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试动态的删除
	<s:form action="user_delDynamic.action" method="post">
		<s:textfield name="dynamic.dynamicID" label="动态号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试通知的获取
	<s:form action="user_getNotices.action" method="post">
		<s:textfield name="aclass.code" label="班级号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试发布动态
	<s:form action="user_publishDynamic.action" method="post">
		<s:textfield name="dynamic.userID" label="发布者" />
		<s:textfield name="dynamic.content" label="内容" />
		<s:textfield name="dynamic.imageUrls" label="图片urls" />
		<s:textfield name="dynamic.likeAmount" label="点赞数" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试发布评论
	<s:form action="user_publishComment.action" method="post">
		<s:textfield name="comment.dynamicID" label="对应动态" />
		<s:textfield name="comment.content" label="内容" />
		<s:textfield name="comment.userID" label="评论人" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试点赞
	<s:form action="user_addPraise.action" method="post">
		<s:textfield name="dynamic.dynamicID" label="动态号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试发布通知
	<s:form action="user_publishNotice.action" method="post">
		<s:textfield name="notice.content" label="通知内容" />
		<s:textfield name="notice.classID" label="班级号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
	测试更新班级
	<s:form action="user_updateClass.action" method="post">
		<s:textfield name="user.userID" label="用户编号" />
		<s:textfield name="user.classID" label="新班级号" />
		<s:submit value="提交" />
		<s:reset value="重置" />
	</s:form>
</body>
</html>
