function addEmail(){var e=$("#emails tr").length;$("#emails").append("<tr>	<td>		<select name='emails["+e+"].rel' onchange='setLabel(this)'>"+"			<option></option>"+"			<option value='http://schemas.google.com/g/2005#home'>Home</option>"+"			<option value='http://schemas.google.com/g/2005#work'>Work</option>"+"			<option value='http://schemas.google.com/g/2005#other'>Other</option>"+"		</select>"+"	</td>"+"	<td><input type='text' name='emails["+e+"].label' class='label'/></td>"+"	<td><input type='text' name='emails["+e+"].address'/></td>"+"	<td><input type='checkbox' name='emails["+e+"].primary' onchange='setPrimary(this)'/></td>"+"</tr>")}function addPhone(){var e=$("#phones tr").length;$("#phones").append("<tr>	<td>		<select name='phones["+e+"].rel' onchange='setLabel(this)'>"+"			<option></option>"+"			<option value='http://schemas.google.com/g/2005#assistant'>Assistant</option>"+"			<option value='http://schemas.google.com/g/2005#callback'>Callback</option>"+"			<option value='http://schemas.google.com/g/2005#car'>Car</option>"+"			<option value='http://schemas.google.com/g/2005#company_main'>Company Main</option>"+"			<option value='http://schemas.google.com/g/2005#fax'>Fax</option>"+"			<option value='http://schemas.google.com/g/2005#home'>Home</option>"+"			<option value='http://schemas.google.com/g/2005#home_fax'>Home Fax</option>"+"			<option value='http://schemas.google.com/g/2005#idsn'>ISDN</option>"+"			<option value='http://schemas.google.com/g/2005#main'>Main</option>"+"			<option value='http://schemas.google.com/g/2005#mobile'>Mobile</option>"+"			<option value='http://schemas.google.com/g/2005#other'>Other</option>"+"			<option value='http://schemas.google.com/g/2005#other_fax'>Other Fax</option>"+"			<option value='http://schemas.google.com/g/2005#pager'>Pager</option>"+"			<option value='http://schemas.google.com/g/2005#radio'>Radio</option>"+"			<option value='http://schemas.google.com/g/2005#telex'>Telex</option>"+"			<option value='http://schemas.google.com/g/2005#tty_tdd'>TTY TDD</option>"+"			<option value='http://schemas.google.com/g/2005#work'>Work</option>"+"			<option value='http://schemas.google.com/g/2005#work_fax'>Work Fax</option>"+"			<option value='http://schemas.google.com/g/2005#work_mobile'>Work Mobile</option>"+"			<option value='http://schemas.google.com/g/2005#work_pager'>Work Pager</option>"+"		</select>"+"	</td>"+"	<td><input type='text' name='phones["+e+"].label' class='label'/></td>"+"	<td><input type='text' name='phones["+e+"].number'/></td>"+"	<td><input type='checkbox' name='phones["+e+"].primary' onchange='setPrimary(this)'/></td>"+"</tr>")}function setLabel(e){var t=e.value!="";$(e).closest("tr").find(".label").prop("disabled",t)}function setPrimary(e){if(!$(e).prop("checked"))return;$(e).closest("tbody").find("input[type='checkbox']").each(function(t,n){n!=e&&$(n).removeAttr("checked")})};