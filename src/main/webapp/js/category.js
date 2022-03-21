function addCategory() {
    Ajax.getHtml('/categoryAdd.html', function (html) {
        $(function (){
            let date = new Date().getFullYear() + '' + (new Date().getMonth() + 1) + '' + new Date().getDate() + '' + new Date().getSeconds();
            $('#categoryId').attr("value", date);
        })
        layer.open({
            type: 1,
            title: 'New product category?',
            area: ['700px'],
            content: html,
            btn: ['yes', 'no'],
            yes: function () {
                testCategory();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}
function saveCategory() {
    Ajax.post(
        '/addCategory',
        {
            categoryId: $('#categoryId').val(),
            name: $('#categoryName').val(),
            remark:$('#categoryRemark').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('category.html');
                //layer.alert(result.msg)
            })
        }
    )
}
//测试是否符合要求
function testCategory() {
    if($('#categoryId').val()==""){
        alert("Product category id cannot be empty");
        return false;
    }
    if($('#categoryName').val()==""){
        alert("Product category name cannot be empty");
        return false;
    }
    return saveCategory();
}

//修改信息
function updateCategory(obj) {
    Ajax.getHtml('/categoryUpdate.html', function (html) {
        $(function () {
            var a = obj.parentNode.parentNode.cells[0].innerText;
            $('#no').attr("value",a);
            var b = obj.parentNode.parentNode.cells[1].innerText;
            $('#categoryId').attr("value",b);
            var c = obj.parentNode.parentNode.cells[2].innerText;
            $('#categoryName').attr("value",c);
            var d = obj.parentNode.parentNode.cells[3].innerText;
            $('#categoryRemark').attr("value",d);
        });

        layer.open({
            type: 1,
            title: 'Confirm updating product category?',
            area: ['700px'],
            content: html,
            btn: ['yes', 'no'],
            yes: function () {
                testCategoryUpdate();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}
//测试修改信息
function testCategoryUpdate() {
    if($('#name').val()==""){
        alert("Product category name cannot be empty");
        return false;
    }
    return updateCategory2();
}

//更新用户信息
function updateCategory2(){
    Ajax.post(
        '/updateCategory',
        {
            categoryId: $('#categoryId').val(),
            name:$('#categoryName').val(),
            remark:$('#categoryRemark').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('category.html');
            })
        }
    )
}

//删除分类
function deleteCategory(obj) {
    function delCategory(){
        var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removeCategory",{categoryId:a},function (result) {
            doResult(result,function () {
                layer.closeAll();
                alert(result.msg);
                show('category.html');
            })
        })
    }
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: 'Confirm deletion?',
            area: ['700px'],
            content: html,
            btn: ['yes', 'no'],
            yes: function () {
                delCategory();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}