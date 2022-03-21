var ctx = '/demo';

function logout() {

}

function show(url) {
    $('#right').load(url)
}

var loadingIndex;

function doAjax(url, type, data, datatype, success) {
    $.ajax({
        url: ctx + url,
        type: type,
        data: data,
        datatype: datatype,

        beforeSend: function () {
            loadingIndex = layer.msg('加载中', {
                icon: 16,
                shade: 0.01
            })
        },
        complete: function () {
            layer.close(loadingIndex)
        },
        success: success,
        // 自定义请求头
        headers: {'Authorization': window.sessionStorage.getItem('token')}
    })
}

var Ajax = {
    get: function (url, data, success) {
        doAjax(url, 'get', data, 'json', success)
    },
    post: function (url, data, success) {
        doAjax(url, 'post', data, 'json', success)
    },
    put: function (url, data, success) {
        doAjax(url, 'put', data, 'json', success)
    },
    delete: function (url, data, success) {
        doAjax(url, 'delete', data, 'json', success)
    },
    getHtml: function (url, success) {
        doAjax(url, 'get', null, 'html', success)
    }
}

function doResult(result, success) {
    if (result.code == 500) {
        alert(result.msg)
    } else if (result.code == 401) {
        alert(result.msg)
        document.location.href = 'entry.html'
    } else {
        success();
    }
}

function showPage(options) {
    var data = {
        currentPage: options.currentPage,
        pageSize: options.pageSize
    };
    if (options.params) {
        // 把第二个参数内的属性 合并到 第一个参数中
        Object.assign(data, options.params);
    }
    Ajax.get(
        options.url,
        data,
        function (result) {
            doResult(result, function () {
                // 查询到的数据通过art模板,添加到页面的指定位置
                var str = template(options.templateId, result);
                document.getElementById(options.contentId).innerHTML = str;
                // 分页
                // 总页数
                if (result.total != '0'){
                    var totalPages = Math.ceil(result.total / options.pageSize);
                    // 分页插件提供的方法
                    $('#page').bootstrapPaginator({
                        bootstrapMajorVersion: 3,
                        currentPage: options.currentPage,
                        numberOfPages: 5,
                        totalPages: totalPages,
                        shouldShowPage: true,
                        onPageClicked: function (event, originalEvent, type, nowPage) {
                            let params1 = options.params
                            showPage({
                                currentPage: nowPage,
                                pageSize: options.pageSize,
                                url: options.url,
                                templateId: options.templateId,
                                contentId: options.contentId,
                                params:params1
                            })
                        }
                    })
                }
                else {
                    alert(result.msg);
                }
            })
        }
    )
}
