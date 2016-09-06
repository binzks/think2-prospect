/**
 * 获取一个违章绑定html
 * @param key 车牌号或者驾驶证号
 * @param value 车架号或者档案编号
 * @param uId 用户id
 * @param type 类型 1-车辆违章 2-驾驶证违章
 * @returns html
 */
function getCacheHtmlString(key, value, uId, type) {
    var html = "<li id='" + key + "' name='" + value + "' class='mui-table-view-cell'><div class='mui-slider-right mui-disabled'>" +
        "<a class='mui-btn mui-btn-red'>删除</a></div><div class='mui-slider-handle mui-navigate-right'" +
        " onclick=\"checkIllegal('" + key + "','" + value + "','" + uId + "','" + type + "')\">" +
        "<img class='mui-pull-left' style='width: 120px;height: 30px;margin-bottom: 5px;' src='../../images/car.png'>" +
        "<span style='color: #0062cc;font-size: 24px;display:block; margin-top:10px;line-height:30px'>" + key + "</span></div></li>";
    return html.toString();
}

/**
 * 获取绑定信息
 */
function loadBinding(uId) {
    if (null == uId || "" == uId) {
        loadBindingCache();
    } else {
        getBinding(uId);
    }
}

/**
 * 获取本地缓存的绑定信息
 */
function loadBindingCache() {
    var bindings = localStorage.getItem('wiseksIllegal');
    if (null == bindings || "" == bindings) {
        return;
    }
    bindings = eval(bindings);
    for (var i = 0; i < bindings.length; i++) {
        var binding = bindings[i];
        if (null == binding) {
            continue;
        }
        if (binding.type == 1) {
            $('#OA_task_vehicle').append(getCacheHtmlString(binding.key, binding.value, "", binding.type));
        } else if (binding.type == 2) {
            $('#OA_task_driving').append(getCacheHtmlString(binding.key, binding.value, "", binding.type));
        }
    }
}

/**
 * 缓存一个绑定信息
 * @param key 车牌号或者驾驶证号
 * @param value 车架号或者档案编号
 * @param type 类型 1-车辆违章 2-驾驶证违章
 */
function saveBindingCache(key, value, type) {
    var obj = {"key": key, "value": value, "type": type};
    var str = JSON.stringify(obj);
    var bindings = localStorage.getItem('wiseksIllegal');
    if (null == bindings) {
        bindings = "[]";
    }
    if (bindings.indexOf(str) == -1) {
        bindings = eval(bindings);
        bindings.push(obj);
        localStorage.setItem('wiseksIllegal', JSON.stringify(bindings));
    }
}

/**
 * 删除指定key的缓存
 * @param key 缓存key,车牌号或者驾驶证号
 */
function removeBindingCache(key, value) {
    var bindings = localStorage.getItem('wiseksIllegal');
    bindings = eval(bindings);
    for (var i = 0; i < bindings.length; i++) {
        var binding = bindings[i];
        if (null == binding) {
            continue;
        }
        if (binding.key == key && binding.value == value) {
            bindings.splice(i, 1);
            localStorage.setItem('wiseksIllegal', JSON.stringify(bindings));
            break;
        }
    }
}

/**
 * 获取注册用户绑定信息
 * @param uId 用户id
 */
function getBinding(uId) {
    $.ajax({
        url: "../../illegal/binding.do",
        type: "post",
        dataType: "json",
        data: {"u": uId},
        success: function (data) {
            var result = eval(data);
            if (result.code != "0") {
                mui.alert(result.data);
            } else {
                var bindings = eval(result.data);
                for (var i = 0; i < bindings.length; i++) {
                    var binding = bindings[i];
                    if (binding.type == 1) {
                        $('#OA_task_vehicle').append(getCacheHtmlString(binding.binding, binding.detail, uId, binding.type));
                    } else if (binding.type == 2) {
                        $('#OA_task_driving').append(getCacheHtmlString(binding.binding, binding.detail, uId, binding.type));
                    }
                }
            }
        },
        error: function (XMLHttpRequest) {
            var msg = XMLHttpRequest.responseText.toString();
            if (null != msg && "" != msg) {
                msg = JSON.parse(msg);
                mui.alert(msg.data);
            } else {
                mui.toast('系统异常,请稍后再试');
            }
        }
    });
}

/**
 * 删除用户绑定违章,如果用户id为null或者空则删除本地缓存
 * @param uId 用户id
 * @param key 车牌号或者驾驶证号
 * @param value 车架号或者档案编号
 * @param type 类型 1-车辆 2-驾驶证
 */
function removeBinding(uId, key, value, type) {
    if (null == uId || "" == uId) {
        removeBindingCache(key, value);
    } else {
        $.ajax({
            url: "../../illegal/deleteBinding.do",
            type: "post",
            dataType: "json",
            data: {"u": uId, "k": key, "v": value, "t": type},
            success: function (data) {
                var result = eval(data);
                if (result.code != "0") {
                    mui.alert(result.data);
                }
            },
            error: function (XMLHttpRequest) {
                var msg = XMLHttpRequest.responseText.toString();
                if (null != msg && "" != msg) {
                    msg = JSON.parse(msg);
                    mui.alert(msg.data);
                } else {
                    mui.toast('系统异常,请稍后再试');
                }
            },
        });
    }
}

/**
 * 查询车辆违章信息
 */
function queryVehicle(uId) {
    var key = $("#vehicle_key").val();
    key = key.toUpperCase();
    var value = $("#vehicle_value").val();
    if (key == "") {
        mui.alert('请输入车牌号码');
    } else if (!(/^[0-9a-zA-Z]*$/g.test(key))) {
        mui.alert("车牌号码只能是数字或字母，请重新输入！");
    } else if (key.length != 5) {
        mui.alert("车牌号码为5位，请重新输入！");
    } else if (value == "") {
        mui.alert("请输入车架号！");
    } else if (value.length != 7) {
        mui.alert("车架号为7位，请重新输入！");
    } else if (!(/^[0-9a-zA-Z]*$/g.test(value))) {
        mui.alert("车架号只能是数字或字母，请重新输入！");
    } else {
        key = "E" + key;
        checkIllegal(key, value, uId, "1");
    }
}

/**
 * 检查输入的车牌号和车架号是否有效
 */
function checkIllegal(key, value, uId, type) {
    document.getElementById("waiting").style.display="block";
    $.ajax({
        url: "../../illegal/check.do",
        type: "post",
        dataType: "json",
        data: {"k": key, "v": value, "u": uId, "t": type},
        success: function (data) {
            document.getElementById("waiting").style.display="none";
            var result = eval(data);
            if (result.code != "0") {
                mui.alert(result.data);
            } else {
                saveBindingCache(key, value, type);
                var url = "../../html/illegal/list.html?k=" + key + "&v=" + value + "&t=" + type;
                //打开列表页面
                mui.openWindow({
                    url: url,
                });
            }
        },
        error: function (XMLHttpRequest) {
            document.getElementById("waiting").style.display="none";
            var msg = XMLHttpRequest.responseText.toString();
            if (null != msg && "" != msg) {
                msg = JSON.parse(msg);
                mui.alert(msg.data);
            } else {
                mui.toast('系统异常,请稍后再试');
            }
        },
    });
}

/**
 * 查询违章信息
 * @param key 车牌号或者驾驶证号
 * @param value 车架号或者档案编号
 * @param type 类型 1-车辆违章 2-驾驶证违章
 */
function queryIllegal(key, value, type) {
    $.ajax({
        url: "../../illegal/query.do",
        type: "post",
        dataType: "json",
        data: {"k": key, "v": value, "t": type},
        success: function (data) {
            var result = eval(data);
            if (result.code != "0") {
                mui.alert(result.data);
            } else {
                var illegals = eval(result.data);
                for (var i = 0; i < illegals.length; i++) {
                    var illegal = illegals[i];
                    var time = timestampToString(illegal.violation_time);
                    if (illegal.violation_status == 0) {
                        $('#OA_task_no_pay').append(getIllegalHtml(illegal.id, time, illegal.violation_address, illegal.surveil_file_no,
                            illegal.violation_act, illegal.violation_score, illegal.violation_amount, type));
                    } else if (illegal.violation_status == 1) {
                        $('#OA_task_paid').append(getIllegalHtml(illegal.id, time, illegal.violation_address, illegal.surveil_file_no,
                            illegal.violation_act, illegal.violation_score, illegal.violation_amount, type));
                    }
                }
                if ($("#OA_task_no_pay li").length == 0) {
                    $('#item_no_pay').attr("style", "background-color: #efeff4;box-shadow: 0 0px 0px rgba(0, 0, 0, 0)");
                    $('#OA_task_no_pay').attr("style", "background-color: #efeff4;border:0px");
                    $('#OA_task_no_pay').append("<img style='width: 100%; height: 100%;' src='../../images/ok.png'>");
                }
                if ($("#OA_task_paid li").length == 0) {
                    $('#item_paid').attr("style", "background-color: #efeff4;box-shadow: 0 0px 0px rgba(0, 0, 0, 0)");
                    $('#OA_task_paid').attr("style", "background-color: #efeff4;border:0px");
                    $('#OA_task_paid').append("<img style='width: 100%; height: 100%;' src='../../images/ok-paid.png'>");
                }
            }
        },
        error: function (XMLHttpRequest) {
            var msg = XMLHttpRequest.responseText.toString();
            if (null != msg && "" != msg) {
                msg = JSON.parse(msg);
                mui.alert(msg.data);
            } else {
                mui.toast('系统异常,请稍后再试');
            }
        }
    });
}

/**
 * 获取违章list页面单个违章html
 */
function getIllegalHtml(id, time, address, no, act, score, amount, type) {
    var html = "<li class='mui-table-view-cell' onclick=\"showDetail('" + id + "','" + type + "')\"><div class='mui-table'>" +
        "<div class='mui-table-cell mui-col-xs-11'><h5>" + time + "</h5><h4>" + address + "</h4>" +
        "<h4>档案编号:" + no + "</h4><h6>" + act + "</h6><h5>罚:" + amount + "&nbsp;&nbsp;记:" + score + "</h5></div>" +
        "<div class='mui-table-cell mui-col-xs-1' style='vertical-align: middle'><span class='mui-icon mui-icon-forward'></span></div></div></li>";
    return html.toString();
}

/**
 * 查询违章详情
 */
function showDetail(id, type) {
    var url = "../../html/illegal/detail.html?id=" + id + "&t=" + type;
    //打开详情
    mui.openWindow({
        url: url,
    });
}

/**
 * 查询违章详情
 */
function queryDetail(id, type) {
    $.ajax({
        url: "../../illegal/queryDetail.do",
        type: "post",
        dataType: "json",
        data: {"id": id, "t": type},
        success: function (data) {
            var result = eval(data);
            if (result.code != "0") {
                mui.alert(result.data);
            } else {
                var illegal = eval(result.data);
                $('#surveil_file_no').append(illegal.surveil_file_no);
                $('#violation_processed_by').append(illegal.violation_processed_by);
                var time = timestampToString(illegal.violation_time);
                $('#violation_time').append(time);
                $('#violation_address').append(illegal.violation_address);
                $('#violation_act').append(illegal.violation_act);
                if ("" != illegal.violation_pic_url) {
                    $('#violation_pic_url').attr("src", "http://file.wiseks.net:9530/" + illegal.violation_pic_url);
                }
            }
        },
        error: function (XMLHttpRequest) {
            var msg = XMLHttpRequest.responseText.toString();
            if (null != msg && "" != msg) {
                msg = JSON.parse(msg);
                mui.alert(msg.data);
            } else {
                mui.toast('系统异常,请稍后再试');
            }
        },
    });
}