<%@page import="javassist.bytecode.stackmap.TypeData.ClassName"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.lang.reflect.Type"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ page import="edu.asu.krypton.form.annotations.TextArea"%>
<%@ page import="java.lang.annotation.Annotation"%>
<%@ page import="java.lang.reflect.Field"%>
<%@ page language="java" pageEncoding="windows-1256"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<% String formAction = (String)pageContext.findAttribute("URLTosubmit"); %>
	<sf:form class="form-horizontal" method="POST" className="${formObject.getClass().getName()}" commandName="formObject">
	<div class="modal-header">
	<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button> -->
	      <h1> <%= pageContext.findAttribute("formObject").getClass().getSimpleName() %></h1>
	</div>
	<div class="modal-body" >
	<% 
		Field[] fields = pageContext.findAttribute("formObject").getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {%>					
			<div>
			
			<% if(fields[i].getName().equalsIgnoreCase("runTimeAttributes")){
				continue;
			} %>
			<%Annotation[] annotations = fields[i].getAnnotations();
			for (int j = 0; j < annotations.length; j++) {
				if (annotations[j].toString().contains("InputText")) {
					if (annotations[j].toString().contains("readOnly=true")) {%>
							<% fields[i].setAccessible(true); %>
							<div class="control-group input-prepend input-append">
								<label for="<%= fields[i].getName() %>" class="add-on pull-left">
									<span class="icon-asterisk"></span><%= fields[i].getName() %>
								</label>
								<sf:input class="input-medium" readonly="true" path="<%= fields[i].getName() %>" size="15" id="<%= fields[i].getName() %>"/>
								<label id="title-description" for="<%= fields[i].getName() %>" class="add-on"></label>
							</div>
					<%} else {%>
							<div class="control-group input-prepend input-append">
								<label for="<%= fields[i].getName() %>" class="add-on pull-left">
									<span class="icon-asterisk"></span><%= fields[i].getName() %>
								</label>
								<sf:input class="input-medium" path="<%= fields[i].getName() %>" size="15" id="<%= fields[i].getName() %>"/>
								<label id="title-description" for="<%= fields[i].getName() %>" class="add-on"></label>
							</div>
					<% }
				} else if (annotations[j].toString().contains("Password")) {
					if (annotations[j].toString().contains("showPassword=true")) {%>
							<div class="control-group input-prepend input-append">
								<label for="<%= fields[i].getName() %>" class="add-on pull-left">
									<span class="icon-asterisk"></span><%= fields[i].getName() %>
								</label>
								<sf:password path="<%= fields[i].getName() %>" size="30" showPassword="true" id="<%= fields[i].getName() %>"/>
								<label id="title-description" for="<%= fields[i].getName() %>" class="add-on"></label>
							</div>
					<%} else {%>
							<div class="control-group input-prepend input-append">
								<label for="<%= fields[i].getName() %>" class="add-on pull-left">
									<span class="icon-asterisk"></span><%= fields[i].getName() %>
								</label>
							<sf:password path="<%= fields[i].getName() %>" size="30" id="<%= fields[i].getName() %>"/>
								<label id="title-description" for="<%= fields[i].getName() %>" class="add-on"></label>
							</div>
						<%}
				} else if (annotations[j].toString().contains("TextArea")) {
					if (annotations[j].toString().contains("applyCKEditor=true")) { %>
						<sf:textarea class="ckeditor" path="<%= fields[i].getName() %>" rows="5" cols="30" id="<%= fields[i].getName() %>"/>
					<% } else {%>
							<sf:textarea path="<%= fields[i].getName() %>" rows="5" cols="30" id="<%= fields[i].getName() %>"/>
					<% }
				} else if (annotations[j].toString().contains("CheckBox")) {
					if (annotations[j].toString().contains("unChecked=false")) {%>
							<label class="checkbox"><%= fields[i].getName() %>
								<sf:checkbox checked="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/>
							</label>
					<%} else {%>
							<label class="checkbox"><%= fields[i].getName() %>
								<sf:checkbox path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/>
							</label>
					<%}
				} else if (annotations[j].toString().contains("File")) {%>
							<% StringTokenizer tokenizer = new StringTokenizer(annotations[j].toString(),"(=)"); 
							   tokenizer.nextToken();
							   tokenizer.nextToken();
							   String accept = tokenizer.nextToken();%>
							<div class="fileupload fileupload-new" data-provides="fileupload">
								<i class="icon-file fileupload-exists"></i>
								<sf:input type="file" accept="<%= accept %>" id="<%= fields[i].getName() %>" path="<%= fields[i].getName() %>"/>Accepted Files are: <%= accept %>
							</div>
							
				<%} else if (annotations[j].toString().contains("Select")) {%>
						
							<sf:select style="width:auto;" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>">
							<% StringTokenizer tokenizer = new StringTokenizer(annotations[j].toString(),"(=)"); 
							   tokenizer.nextToken();
							   tokenizer.nextToken();
							   String options = tokenizer.nextToken(); 
							   tokenizer = new StringTokenizer(options,",");
							   while(tokenizer.hasMoreTokens()){
							   String option = tokenizer.nextToken();%>
							   <sf:option value="<%= option %>" label="<%= option %>" />
							   <%}%>
							</sf:select>
					
				<%} else if (annotations[j].toString().contains("RadioButton")) {
					if (annotations[j].toString().contains("selected=true")) {%>
						<label class="radio">
							<sf:radiobutton value="true" checked="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/>
						</label>
					<%} else {%>
						<label class="radio">
							<sf:radiobutton value="true" path="<%= fields[i].getName() %>" id="<%= fields[i].getName() %>"/>
						</label>
						<%}
				}
			}%>
			</div>
		<%}%>
			
		
	</div>
	<div class="modal-footer">
		<div class="btn-group pull-left">
			 <sf:input type="submit" class="btn btn-primary" value="Submit" path=""/>
		</div>
	</div>
	</sf:form>
