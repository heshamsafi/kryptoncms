<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
%>

<%-- JSTL --%>
<%-- Libraries --%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<%-- <c:set var="ctx" value="${pageContext['request'].contextPath}"/> --%>


<!-- <script type="text/javascript" src="/kryptoncms/resources/js/ckeditor/ckeditor.js"></script> -->
<spring:url scope="page" var="ckeditor" value="/resources/js/ckeditor/ckeditor.js"/>
<%-- js --%>
<%-- libraries --%>
<spring:url scope="page" var="compiledRequireJqueryJs"          value="/resources/js/target/libraries/require-jquery.js" />
<spring:url scope="page" var="requireJqueryJs"          value="/resources/js/libraries/require-jquery.js" />
<spring:url scope="page" var="requireJs"          value="/resources/js/libraries/require.js" />

<spring:url scope="page" var="jqueryJavascriptUrl"       value="/resources/js/libraries/jquery-1.7.1.js" />
<spring:url scope="page" var="jqueryTmplJavascriptUrl"   value="/resources/js/libraries/jquery.tmpl.min.js" />
<spring:url scope="page" var="jqueryAtmosphereUrl"       value="/resources/js/libraries/jquery.atmosphere.js" />
<%-- <spring:url scope="page" var="jqueryAtmosphereUrl"       value="/resources/js/libraries/jquery.atmosphere.modified.js" /> --%>
<spring:url scope="page" var="MootoolsBase" 	         value="/resources/js/libraries/mootools-1.2.4-base.js" />
<spring:url scope="page" var="JQueryClassMutators"       value="/resources/js/libraries/Class.Mutators.jQuery.js" />
<spring:url scope="page" var="bootstrapUrl"			     value="/resources/js/libraries/bootstrap.js" />
<spring:url scope="page" var="jqueryCookie"			     value="/resources/js/libraries/jquery.cookie.js" />
<spring:url scope="page" var="jqueryValidation"		     value="/resources/js/libraries/jquery-validate.min.js" />
<spring:url scope="page" var="jqueryHistoryApi"		     value="/resources/js/libraries/jquery.history.js" />
<spring:url scope="page" var="jqueryScrollTo"		     value="/resources/js/libraries/jquery.scrollto.min.js" />

<spring:url scope="page" var="tmpl_min" value="/resources/js/libraries/tmpl.min.js" />
<spring:url scope="page" var="load_image_min" value="/resources/js/libraries/load-image.min.js" />
<spring:url scope="page" var="canvas_to_blob_min" value="/resources/js/libraries/canvas-to-blob.min.js" />
<spring:url scope="page" var="bootstrap_image_gallery_min" value="/resources/js/libraries/bootstrap-image-gallery.min.js" />
<spring:url scope="page" var="jq_context_menu_js" value="/resources/js/jquery.contextMenu.js" />

<%-- js --%>
<%-- Message Proxies --%>
<spring:url scope="page" var="ChatMessage_class"          value="/resources/js/message_proxies/ChatMessage.class.js" />

<%-- application-code --%>
<spring:url scope="page" var="FormSerializer_class"		 value="/resources/js/FormSerializer.class.js" />
<spring:url scope="page" var="Logger_class"              value="/resources/js/Logger.class.js" />
<spring:url scope="page" var="Membership_class"          value="/resources/js/Membership.class.js" />
<spring:url scope="page" var="Notifier_class"			 value="/resources/js/Notifier.class.js" />
<spring:url scope="page" var="NavigationMenu_class"		 value="/resources/js/NavigationMenu.class.js" />
<spring:url scope="page" var="Validator_class"		     value="/resources/js/Validator.class.js" />
<spring:url scope="page" var="DataToggleButton_class"    value="/resources/js/DataToggleButton.class.js" />
<spring:url scope="page" var="SocketHandler_class"       value="/resources/js/SocketHandler.class.js" />
<spring:url scope="page" var="Chatter_class"		     value="/resources/js/Chatter.class.js" />
<spring:url scope="page" var="CommentManager_class"		 value="/resources/js/CommentManager.class.js" />
<spring:url scope="page" var="ajaxifier"		 	     value="/resources/js/Ajaxifier.js" />
<spring:url scope="page" var="Main"					     value="/resources/js/Main.js" />
<spring:url scope="page" var="CompiledMain"					     value="/resources/js/target/Main.js" />

<%-- css --%>
<%-- libraries --%>
<spring:url scope="page" var="bootstrapCssUrl"		     value="/resources/css/bootstrap.css" />
<spring:url scope="page" var="bootstrapResponsiveCssUrl" value="/resources/css/bootstrap-responsive.css" />
<spring:url scope="page" var="bootstrap_image_gallery"   value="/resources/css/bootstrap-image-gallery.min.css" />
<spring:url scope="page" var="colorpickerCss" 			 value="/resources/css/jquery.colorpicker.css" />

<%-- css --%>
<%-- application-code --%>
<spring:url scope="page" var="main_style"				 value="/resources/css/main_style.css" />

<c:set var="view_prefix" value="/WEB-INF/views/" />
<c:set var="view_prefix_bodies" value="${view_prefix}/bodies/" />
<c:set var="view_suffix" value=".jsp" />
