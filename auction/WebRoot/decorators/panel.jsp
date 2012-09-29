<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<p>
	<table width=250 border=0 cellpadding=0 cellspacing=0>
		<tr>
			<th class="panelTitle">
				<decorator:title default="小面板页面" />
			</th>
		</tr>
		<tr>
			<td class="panelBody">
				<decorator:body />
			</td>
		</tr>
	</table>
<p>

