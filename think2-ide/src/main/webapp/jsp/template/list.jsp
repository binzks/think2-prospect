<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}列表</title>
    <link href="../../css/plugins/dataTables/datatables.min.css" rel="stylesheet">
</head>

<body>
<div class="ibox float-e-margins">
    <div class="ibox-title">
        <h5 style="font-size: 25px">搜索</h5>
        <div class="ibox-tools">
            <a class="collapse-link">
                <i class="fa fa-chevron-up fa-2x"></i>
            </a>
            <a class="">
                <i class="fa fa-search fa-2x btn-outline btn-warning"></i>
            </a>
            <a class="">
                <i class="fa fa-plus fa-2x btn-outline btn-info"></i>
            </a>
        </div>
    </div>
    <div class="ibox-content">
        aaa
    </div>
    <div class="ibox-footer">
        <div class="table-responsive">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-striped table-bordered table-hover dataTable">
                    <thead>
                    <tr>
                        <%@include file="/view/system/template/listHead.jsp" %>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="data" items="${dataList}" varStatus="status">
                        <tr class="<c:if test="${status.index % 2 == 0 }">even</c:if><c:if test="${status.index % 2 != 0 }">odd</c:if>">
                                <%--<td class="center"><span class="lbl">${ status.index + 1}</span></td>--%>
                            <%@include file="/view/system/template/listColumn.jsp" %>
                            <c:if test="${optWidth != 0}">
                                <td class="center">
                                    <div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'>
                                        <c:set var="pkValue" value="${data.get(pk)}"></c:set>
                                        <c:if test="${not empty wid}">
                                            <a class="ace-icon fa fa-search-plus bigger-130"
                                               href="${detailHref}${mid}-${pkValue}.idea"
                                               title="查看详情"></a>
                                            <c:set var="stepType" value="${steps.get(data.get(field).toString()).type}"></c:set>
                                            <c:choose>
                                                <%--起点--%>
                                                <c:when test="${stepType == '0'}">
                                                    <a class="green ace-icon fa fa-pencil bigger-130"
                                                       href="${editHref}${mid}-${pkValue}.idea" title="编辑${title}"></a>
                                                    <c:forEach var="action" items="${actions }">
                                                        <c:if test="${ action.type==1 && empty powerActions.get(action.name)}">
                                                            <a class='${action.css}'
                                                               <c:if test="${not empty action.confirm }">onclick="return confirm('${action.confirm}');"</c:if>
                                                               href='${action.href }${mid }-${pkValue}.idea'
                                                               title="${action.describe }"></a>
                                                        </c:if>
                                                    </c:forEach>
                                                    <a class="green ace-icon fa fa-check bigger-130"
                                                       href="${baseUrl}/submit${mid}-${pkValue}.idea"
                                                       onClick="return confirm('确定提交审核?');" title="提交"></a>
                                                </c:when>
                                                <%--终点--%>
                                                <c:when test="${stepType == '1'}">
                                                    <a class="red ace-icon fa fa-remove bigger-130"
                                                       onClick="refuse(${mid},${pkValue});"
                                                       title="拒绝${title}"></a>
                                                </c:when>
                                                <%--节点--%>
                                                <c:when test="${stepType == '2'}">
                                                    <a class="green ace-icon fa fa-check bigger-130"
                                                       href="${baseUrl}/agree${mid}-${pkValue}.idea"
                                                       onClick="return confirm('确定审核通过?');" title="同意"></a>
                                                    <a class="red ace-icon fa fa-remove bigger-130"
                                                       onClick="refuse(${mid},${pkValue});"
                                                       title="拒绝${title}"></a>
                                                </c:when>
                                            </c:choose>
                                            <a class="blue ace-icon fa fa-book bigger-130"
                                               onClick="log(${mid},${pkValue},${wid});"
                                               title="审核日志"></a>
                                        </c:if>
                                        <c:if test="${empty wid}">
                                            <c:forEach var="action" items="${actions }">
                                                <c:if test="${ action.type==1 && empty powerActions.get(action.name)}">
                                                    <a class='${action.css}'
                                                       <c:if test="${not empty action.confirm }">onclick="return confirm('${action.confirm}');"</c:if>
                                                       href='${action.href }${mid }-${pkValue}.idea'
                                                       title="${action.describe }"></a>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="table table-bordered dataTable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                    </tbody>
                </table>
                <div class="row">
                    <div class="dataTables_info col-sm-5">共一页/75条记录</div>
                    <div class="dataTables_paginate col-sm-7">
                        <ul class="pagination input-group">
                            <li class="paginate_button previous disabled"><a href="#">上一页</a></li>
                            <li class="paginate_button active"><a href="#">1</a></li>
                            <li class="paginate_button "><a href="#">2</a></li>
                            <li class="paginate_button "><a href="#">3</a></li>
                            <li class="paginate_button "><a href="#">4</a></li>
                            <li class="paginate_button "><a href="#">5</a></li>
                            <li class="paginate_button "><a href="#">6</a></li>
                            <li class="paginate_button"><a href="#">下一页</a></li>
                            <li class="paginate_button next disabled"><a href="#">尾页</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>


</html>

