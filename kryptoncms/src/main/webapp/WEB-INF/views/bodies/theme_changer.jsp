<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div class="page-header">
      <h1>Theme Changer</h1>
</div>
<form method="POST" action='<c:url value="/themes" />' id="themes_changer" >
	<div class="control-group input-prepend input-append">
		<label for="blue" class="add-on pull-left">
			<span>blue</span>
		</label>
		<input id="blue" name="blue" type="text" class="cp-basic"/>
		<label for="blue" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="green" class="add-on pull-left">
			<span>green</span>
		</label>
		<input id="green" name="green" type="text" class="cp-basic"/>
		<label for="green" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="red" class="add-on pull-left">
			<span>red</span>
		</label>
		<input id="red" name="red" type="text" class="cp-basic"/>
		<label for="red" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="yellow" class="add-on pull-left">
			<span>yellow</span>
		</label>
		<input id="yellow" name="yellow" type="text" class="cp-basic"/>
		<label for="yellow" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="orange" class="add-on pull-left">
			<span>orange</span>
		</label>
		<input id="orange" name="orange" type="text" class="cp-basic"/>
		<label for="orange" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="pink" class="add-on pull-left">
			<span>pink</span>
		</label>
		<input id="pink" name="pink" type="text" class="cp-basic"/>
		<label for="pink" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="purple" class="add-on pull-left">
			<span>purple</span>
		</label>
		<input id="purple" name="purple" type="text" class="cp-basic"/>
		<label for="purple" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="warningText" class="add-on pull-left">
			<span>warning text</span>
		</label>
		<input id="warningText" name="warningText" type="text" class="cp-basic"/>
		<label for="warningText" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="warningBackground" class="add-on pull-left">
			<span>warning background</span>
		</label>
		<input id="warningBackground" name="warningBackground" type="text" class="cp-basic"/>
		<label for="warningBackground" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="errorText" class="add-on pull-left">
			<span>error text</span>
		</label>
		<input id="errorText" name="errorText" type="text" class="cp-basic"/>
		<label for="errorText" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="errorBackground" class="add-on pull-left">
			<span>error background</span>
		</label>
		<input id="errorBackground" name="errorBackground" type="text" class="cp-basic"/>
		<label for="errorBackground" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="successText" class="add-on pull-left">
			<span>success text</span>
		</label>
		<input id="successText" name="successText" type="text" class="cp-basic"/>
		<label for="successText" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="successBackground" class="add-on pull-left">
			<span>success background</span>
		</label>
		<input id="successBackground" name="successBackground" type="text" class="cp-basic"/>
		<label for="successBackground" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="infoText" class="add-on pull-left">
			<span>info text</span>
		</label>
		<input id="infoText" name="infoText" type="text" class="cp-basic"/>
		<label for="infoText" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="infoBackground" class="add-on pull-left">
			<span>info background</span>
		</label>
		<input id="infoBackground" name="infoBackground" type="text" class="cp-basic"/>
		<label for="infoBackground" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="navbarBackground" class="add-on pull-left">
			<span>navbar background</span>
		</label>
		<input id="navbarBackground" name="navbarBackground" type="text" class="cp-basic"/>
		<label for="navbarBackground" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="navbarBackgroundHighlight" class="add-on pull-left">
			<span>navbar background highlight</span>
		</label>
		<input id="navbarBackgroundHighlight" name="navbarBackgroundHighlight" type="text" class="cp-basic"/>
		<label for="navbarBackgroundHighlight" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="navbarText" class="add-on pull-left">
			<span>navbar text</span>
		</label>
		<input id="navbarText" name="navbarText" type="text" class="cp-basic"/>
		<label for="navbarText" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarBrandColor" class="add-on pull-left">
			<span>navbar brand color</span>
		</label>
		<input id="navbarBrandColor" name="navbarBrandColor" type="text" class="cp-basic"/>
		<label for="navbarBrandColor" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarLinkColor" class="add-on pull-left">
			<span>navbar Link Color</span>
		</label>
		<input id="navbarLinkColor" name="navbarLinkColor" type="text" class="cp-basic"/>
		<label for="navbarLinkColor" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarLinkColorHover" class="add-on pull-left">
			<span>navbar Link Color Hover</span>
		</label>
		<input id="navbarLinkColorHover" name="navbarLinkColorHover" type="text" class="cp-basic"/>
		<label for="navbarLinkColorHover" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarLinkColorActive" class="add-on pull-left">
			<span>navbar Link Color Active</span>
		</label>
		<input id="navbarLinkColorActive" name="navbarLinkColorActive" type="text" class="cp-basic"/>
		<label for="navbarLinkColorActive" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarLinkBackgroundHover" class="add-on pull-left">
			<span>navbar Link Background Hover</span>
		</label>
		<input id="navbarLinkBackgroundHover" name="navbarLinkBackgroundHover" type="text" class="cp-basic"/>
		<label for="navbarLinkBackgroundHover" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarLinkBackgroundActive" class="add-on pull-left">
			<span>navbar Link Background Active</span>
		</label>
		<input id="navbarLinkBackgroundActive" name="navbarLinkBackgroundActive" type="text" class="cp-basic"/>
		<label for="navbarLinkBackgroundActive" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarSearchBackground" class="add-on pull-left">
			<span>navbar Search Background</span>
		</label>
		<input id="navbarSearchBackground" name="navbarSearchBackground" type="text" class="cp-basic"/>
		<label for="navbarSearchBackground" class="add-on"></label>
	</div>
	<div class="control-group input-prepend input-append">
		<label for="navbarSearchBackgroundFocus" class="add-on pull-left">
			<span>navbar Search Background Focus</span>
		</label>
		<input id="navbarSearchBackgroundFocus" name="navbarSearchBackgroundFocus" type="text" class="cp-basic"/>
		<label for="navbarSearchBackgroundFocus" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarSearchBorder" class="add-on pull-left">
			<span>navbar Search Border</span>
		</label>
		<input id="navbarSearchBorder" name="navbarSearchBorder" type="text" class="cp-basic"/>
		<label for="navbarSearchBorder" class="add-on"></label>
	</div>	
	<div class="control-group input-prepend input-append">
		<label for="navbarSearchPlaceholderColor" class="add-on pull-left">
			<span>navbar Search Placeholder Color</span>
		</label>
		<input id="navbarSearchPlaceholderColor" name="navbarSearchPlaceholderColor" type="text" class="cp-basic"/>
		<label for="navbarSearchPlaceholderColor" class="add-on"></label>
	</div>	
	
	<div class="control-group input-prepend input-append">
		<label for="linkColor" class="add-on pull-left">
			<span>link Color</span>
		</label>
		<input id="linkColor" name="linkColor" type="text" class="cp-basic"/>
		<label for="linkColor" class="add-on"></label>
	</div>

	<div class="control-group input-prepend input-append">
		<label for="tableBackgroundHover" class="add-on pull-left">
			<span>table Background Hover</span>
		</label>
		<input id="tableBackgroundHover" name="tableBackgroundHover" type="text" class="cp-basic"/>
		<label for="tableBackgroundHover" class="add-on"></label>
	</div>
	
	<p>
	  <label for="amount">navigation bar height:</label>
	  <input type="text" id="amount" name="navbarHeight"  style="border: 0; color: #f6931f; font-weight: bold;" />
	</p>
	<div id="slider-navbarHeight"></div>
	
	<p>
	  <label for="amount-gridColumnWidth">grid Column Width:</label>
	  <input type="text" id="amount-gridColumnWidth" name="gridColumnWidth"  style="border: 0; color: #f6931f; font-weight: bold;" />
	</p>
	<div id="slider-gridColumnWidth"></div>
	
	<p>
	  <label for="amount-baseFontSize">base Font Size:</label>
	  <input type="text" id="amount-baseFontSize" name="baseFontSize"  style="border: 0; color: #f6931f; font-weight: bold;" />
	</p>
	<div id="slider-baseFontSize"></div>

	<p>
	  <label for="amount-baseLineHeight">base Line Height:</label>
	  <input type="text" id="amount-baseLineHeight" name="baseLineHeight"  style="border: 0; color: #f6931f; font-weight: bold;" />
	</p>
	<div id="slider-baseLineHeight"></div>

	<p>
	  <label for="amount-baseBorderRadius">base Border Radius:</label>
	  <input type="text" id="amount-baseBorderRadius" name="baseBorderRadius"  style="border: 0; color: #f6931f; font-weight: bold;" />
	</p>
	<div id="slider-baseBorderRadius"></div>

	<div><a id="sendVars" class="btn btn-primary">Submit</a></div>
	</div>
</form>