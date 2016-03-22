[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>${message("admin.promotion.list")} - Powered By JSHOP</title>
<meta name="author" content="JSHOP Team" />
<meta name="copyright" content="JSHOP" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="breadcrumb">
		<a href="${base}/admin/common/index.jhtml">${message("admin.breadcrumb.home")}</a> &raquo; ${message("admin.promotion.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonGroup">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div id="pageSizeMenu" class="dropdownMenu">
					<a href="javascript:;" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<ul>
						<li[#if page.pageSize == 10] class="current"[/#if] val="10">10</li>
						<li[#if page.pageSize == 20] class="current"[/#if] val="20">20</li>
						<li[#if page.pageSize == 50] class="current"[/#if] val="50">50</li>
						<li[#if page.pageSize == 100] class="current"[/#if] val="100">100</li>
					</ul>
				</div>
			</div>
			<div id="searchPropertyMenu" class="dropdownMenu">
				<div class="search">
					<span class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<ul>
					<li[#if page.searchProperty == "name"] class="current"[/#if] val="name">${message("Promotion.name")}</li>
					<li[#if page.searchProperty == "title"] class="current"[/#if] val="title">${message("Promotion.title")}</li>
				</ul>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">${message("Promotion.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="title">${message("Promotion.title")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="beginDate">${message("Promotion.beginDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="endDate">${message("Promotion.endDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">${message("admin.common.order")}</a>
				</th>
				<th>
					<span>${message("admin.common.action")}</span>
				</th>
			</tr>
			[#list page.content as promotion]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${promotion.id}" />
					</td>
					<td>
						<span title="${promotion.name}">${abbreviate(promotion.name, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.title}">${abbreviate(promotion.title, 50, "...")}</span>
					</td>
					<td>
						[#if promotion.beginDate??]
							<span title="${promotion.beginDate?string("yyyy-MM-dd HH:mm:ss")}">${promotion.beginDate}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						[#if promotion.endDate??]
							<span title="${promotion.endDate?string("yyyy-MM-dd HH:mm:ss")}">${promotion.endDate}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						${promotion.order}
					</td>
					<td>
						<a href="edit.jhtml?id=${promotion.id}">[${message("admin.common.edit")}]</a>
						<a href="${base}${promotion.path}" target="_blank">[${message("admin.common.view")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>
[/#escape]