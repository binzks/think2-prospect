<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${systemName}|<sitemesh:write property='title'/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="../css/animate.css" rel="stylesheet">
    <link href="../css/plugins/codemirror/codemirror.css" rel="stylesheet">
    <link href="../css/plugins/codemirror/ambiance.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
    <sitemesh:write property='head'/>
</head>

<body>
<div id="wrapper">
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear">
                                <span class="block m-t-xs"><strong
                                        class="font-bold">${adminCode}${adminName}</strong></span>
                                <span class="text-muted text-xs block">${adminRoleName}<b class="caret"></b></span>
                            </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li>
                                <button type="button" class="btn btn-w-m btn-warning col-xs-12" data-toggle="modal"
                                        data-target="#admin-password-modal">修改密码
                                </button>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <button type="button" class="btn btn-w-m btn-danger col-xs-12" data-toggle="modal"
                                        onclick="javascript:window.location.href='../admin/logout.do'">退出
                                </button>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i>
                    </a>
                    <form role="search" class="navbar-form-custom" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="Search for something..." class="form-control"
                                   name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">Welcome to ${systemName}.</span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#" aria-expanded="false">
                            <i class="fa fa-gear"></i> <span class="label label-warning">${adminRole}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages">
                            <li>
                                <button type="button" class="btn btn-w-m btn-info col-xs-12" data-toggle="modal"
                                        data-target="#admin-password-modal">修改密码
                                </button>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="../admin/logout.do">
                            <i class="fa fa-sign-out"></i>退出
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <sitemesh:write property='body'/>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal inmodal" id="admin-password-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <form class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-body">
                <div class="form-group">
                    <label>旧密码</label> <input type="password" placeholder="请输入旧密码" class="form-control">
                    <label>新密码</label> <input type="password" placeholder="请输入新密码" class="form-control">
                    <label>确认密码</label> <input type="password" placeholder="请输入确认密码" class="form-control">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="submit" class="ladda-button btn btn-info" data-style="slide-up">保存</button>
                <button class="ladda-button ladda-button-demo btn btn-primary" data-style="zoom-in">Submit</button>
            </div>
        </div>
        <script src="../js/jquery-2.1.1.js"></script>
        <script>
            $(document).ready(function () {
                // Bind normal buttons
                $('.ladda-button').ladda('bind', {timeout: 2000});
                // Bind progress buttons and simulate loading progress
                Ladda.bind('.progress-demo .ladda-button', {
                    callback: function (instance) {
                        var progress = 0;
                        var interval = setInterval(function () {
                            progress = Math.min(progress + Math.random() * 0.1, 1);
                            instance.setProgress(progress);
                            if (progress === 1) {
                                instance.stop();
                                clearInterval(interval);
                            }
                        }, 200);
                    }
                });
                var l = $('.ladda-button-demo').ladda();
                l.click(function () {
                    // Start loading
                    l.ladda('start');
                    // Timeout example
                    // Do something in backend and then stop ladda
                    setTimeout(function () {
                        l.ladda('stop');
                    }, 12000)
                });
            });
        </script>
    </form>
</div>

<script src="../js/jquery-2.1.1.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<script src="../js/inspinia.js"></script>
<script src="../js/plugins/pace/pace.min.js"></script>
<script src="../js/think2-common.js"></script>

<script type="text/javascript">
    initMenu();
    function initMenu() {
        buildMenu(eval(${adminModules}), document.getElementById("side-menu"), 0);
        doSel();
    }

    function buildMenu(data, tag, pid) {
        for (var key in data) {
            if (data[key].parentId != pid) {
                continue;
            }
            if (data[key].type == 0) {//分组
                var li = document.createElement("li");
                var a = document.createElement("a");
                a.href = data[key].uri;
                a.innerHTML = "<i class='" + data[key].icon + "'></i><span class='nav-label'>"
                        + data[key].name + "</span><span class='fa arrow'></span>";
                li.appendChild(a);
                var ul = document.createElement("ul");
                ul.className = "nav nav-second-level";
                li.appendChild(ul);
                buildMenu(data, ul, data[key].id);
                tag.appendChild(li);
            } else {
                var li = document.createElement("li");
                li.id = "menu_" + data[key].id;
                var a = document.createElement("a");
                a.href = data[key].uri + ".view?m=" + data[key].id;
                a.text = data[key].name;
                li.appendChild(a);
                tag.appendChild(li);
            }
        }
    }

    function doSel() {
        var m = getQueryString("m");
        var li = document.getElementById("menu_" + m);
        if (null != li) {
            li.setAttribute("class", "active");
            setParentOpen(li);
        }
    }

    function setParentOpen(obj) {
        var parent = obj.parentNode;
        if (null != parent) {
            if (parent.tagName == "LI") {
                parent.setAttribute("class", "active");
            }
            setParentOpen(parent);
        }
    }
</script>

</body>
</html>