/**
 * 获取请求url中的参数
 * @param name 参数名称
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return r[2];
    } else {
        return null;
    }
}

/**
 * 时间戳转换为字符串格式yyyy-MM-dd HH:mm:ss
 * @param time 时间戳
 */
function timestampToString(time) {
    var date = new Date(parseInt(time) * 1000);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var str = date.getFullYear() + '-' + (month < 10 ? '0' + month : month) + "-" + (day < 10 ? '0' + day : day) + " " +
        (hour < 10 ? '0' + hour : hour) + ':' + (minute < 10 ? '0' + minute : minute);
    var sec = date.getSeconds();
    if (0 != sec) {
        str += ':' + (sec < 10 ? '0' + sec : sec);
    }
    return str;
}
