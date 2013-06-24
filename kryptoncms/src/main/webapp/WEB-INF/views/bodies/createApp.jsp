<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<form id="createAppForm" class="well" action='<c:url value="/newApp/submit" />'>

  <label>Application Name</label>
  <input id="createAppForm_appName" type="text" class="span3" placeholder="Enter your application name"/>	

  <label>Public ECC Key X part</label>
  <input id="createAppForm_X" type="text" class="span3" placeholder="Enter your public ecc x part"/>
  <span class="help-inline">Enter it as a 'BigInteger' string!</span>
  
  <label>Public ECC Key Y part</label>
  <input id="createAppForm_Y" type="text" class="span3" placeholder="Enter your public ecc y part"/>
  <span class="help-inline">Enter it as a 'BigInteger' string!</span>
  
  <label>Public RSA Key n part</label>
  <input id="createAppForm_N" type="text" class="span3" placeholder="Enter your public rsa n part"/>
  <span class="help-inline">Enter it as a 'BigInteger' string!</span>
  
  <label>Public RSA Key e part</label>
  <input id="createAppForm_E" type="text" class="span3" placeholder="Enter your public rsa e part"/>
  <span class="help-inline">Enter it as a 'BigInteger' string!</span>
  
  <!--<label class="checkbox">  
    <input type="checkbox"> Check me out  
  </label>  -->
  <br>
  <button id="createAppFormSubmit" type="submit" class="btn">Submit</button>
</form>