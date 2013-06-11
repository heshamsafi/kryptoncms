<%@ page import="java.util.Iterator"%>
<%@ page import="java.lang.reflect.Type"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ page import="edu.asu.krypton.form.annotations.TextArea"%>
<%@ page import="java.lang.annotation.Annotation"%>
<%@ page import="java.lang.reflect.Field"%>
<%@ page language="java" pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/WEB-INF/views/includes/form_head.jsp"%>
<title>Add/Edit <%= pageContext.findAttribute("formObject").getClass().getSimpleName() + "s" %></title>
</head>
<body>
	<div>
	<% String formAction = (String)pageContext.findAttribute("URLTosubmit"); %>
	<sf:form class="form-horizontal" method="POST" action="<%= formAction %>" commandName="formObject">
	<fieldset>
	<table>
	<% 
		Field[] fields = pageContext.findAttribute("formObject").getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {%>
						<tr>
						<div class="control-group">
							<th><label class="control-label" for="<%= fields[i].getName() %>"><%= fields[i].getName() %>:</label></th>
							<div class="controls">
			<% if(fields[i].getName().equalsIgnoreCase("runTimeAttributes")){
				continue;
			} %>
			<%Annotation[] annotations = fields[i].getAnnotations();
			for (int j = 0; j < annotations.length; j++) {
				if (annotations[j].toString().contains("InputText")) {
					if (annotations[j].toString().contains("readOnly=true")) {%>
							<% fields[i].setAccessible(true); %>
							<td><div class="input-prepend input-append"><span class="add-on"></span><sf:input class="input-medium" readonly="true" path="<%= fields[i].getName() %>" size="15" id="<%= fields[i].getName() %>"/><%= (fields[i].get(pageContext.findAttribute("formObject"))==null)?"":fields[i].get(pageContext.findAttribute("formObject")) %><span class="add-on"></span></div></td>
					<%} else {%>
							<td><div class="input-prepend input-append"><span class="add-on"></span><sf:input class="input-medium" path="<%= fields[i].getName() %>" size="15" id="<%= fields[i].getName() %>"/><span class="add-on"></span></div></td>
					<% }
				} else if (annotations[j].toString().contains("Password")) {
					if (annotations[j].toString().contains("showPassword=true")) {%>
							<td><div class="input-prepend input-append"><span class="add-on"></span><sf:password path="<%= fields[i].getName() %>" size="30" showPassword="true" id="<%= fields[i].getName() %>"/><span class="add-on"></span></div></td>
					<%} else {%>
							<td><div class="input-prepend input-append"><span class="add-on"></span><sf:password path="<%= fields[i].getName() %>" size="30" id="<%= fields[i].getName() %>"/><span class="add-on"></span></div></td>
						<%}
				} else if (annotations[j].toString().contains("TextArea")) {
					if (annotations[j].toString().contains("applyCKEditor=true")) {
						// Waleed
					} else {%>
							<td><sf:textarea path="<%= fields[i].getName() %>" rows="5" cols="30" id="<%= fields[i].getName() %>"/></td>
					<% }
				} else if (annotations[j].toString().contains("CheckBox")) {
					if (annotations[j].toString().contains("unChecked=false")) {%>
							<td><label class="checkbox"><sf:checkbox value="true" checked="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/></label></td>
					<%} else {%>
							<td><label class="checkbox"><sf:checkbox value="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/></label></td>
					<%}
				} else if (annotations[j].toString().contains("File")) {%>
							<% StringTokenizer tokenizer = new StringTokenizer(annotations[j].toString(),"(=)"); 
							   tokenizer.nextToken();
							   tokenizer.nextToken();
							   String accept = tokenizer.nextToken();%>
							<td><div class="fileupload fileupload-new" data-provides="fileupload"><i class="icon-file fileupload-exists"></i><sf:input type="file" accept="<%= accept %>" id="<%= fields[i].getName() %>" path="<%= fields[i].getName() %>"/>Accepted Files are: <%= accept %></div></td>
							
				<%} else if (annotations[j].toString().contains("Select")) {%>
						
							<td><sf:select style="width:auto;" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>">
							<% StringTokenizer tokenizer = new StringTokenizer(annotations[j].toString(),"(=)"); 
							   tokenizer.nextToken();
							   tokenizer.nextToken();
							   String options = tokenizer.nextToken(); 
							   tokenizer = new StringTokenizer(options,",");
							   while(tokenizer.hasMoreTokens()){
							   String option = tokenizer.nextToken();%>
							   <sf:option value="<%= option %>" label="<%= option %>" />
							   <%}%>
								</sf:select></td>	
					
				<%} else if (annotations[j].toString().contains("RadioButton")) {
					if (annotations[j].toString().contains("selected=true")) {%>
							<td><label class="radio"><sf:radiobutton value="true" checked="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/></label></td>
					<%} else {%>
							<td><label class="radio"><sf:radiobutton value="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/></label></td>
						<%}
				}
			}%>
					</div>
					</div>
					<br>
					</tr>
		<%}%>

			</table>
		</fieldset>
		<div style="margin-left:200px;">
        <sf:input type = "submit" class="btn  btn-primary" name="commit" value="Submit" path=""/>
    	</div>   	
	</sf:form>
	</div>
</body>
</html>