<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<h3>${hint}</h3>
<h4>Elliptic Curve used y^2 = x^3 + ${ec_a}x + ${ec_b} (mod ${ec_p})</h4>
<h4>Elliptic Curve used has order of ${ec_order}</h4>
<h4>Use ECC Base point as ( ${ec_base_x} , ${ec_base_y} )</h4>
<span class="help-inline">The point is in a BigInteger string format!</span>
<label>Our Public ECC Key X part</label>
<span class="uneditable-input">${ec_public_x_server}</span>
<label>Our Public ECC Key Y part</label>
<span class="uneditable-input">${ec_public_y_server}</span>
<label>Our Public RSA Key n part</label>
<span class="uneditable-input">${n_server}</span>
<label>Our Public RSA Key e part</label>
<span class="uneditable-input">${e_server}</span>