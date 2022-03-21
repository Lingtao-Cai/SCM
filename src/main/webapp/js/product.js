function addProduct() {
    Ajax.getHtml('/productAdd.html', function (html) {
        $(function (){
            let date = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
            $('#productCreateDate').attr("value", date);
        })
        Ajax.post('/getAllCategoryName', {}, function (result){
            $.each(result.data, function (i, item){
                // $('#selectBox').options.add(new Option(item))
                $('#selectBox').append("<option>"+item+"</option>");

            })
        })
        layer.open({
            type: 1,
            title: '新增产品',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testProduct();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function saveProduct() {
    Ajax.post('/getCategoryId',{categoryName: $('#selectBox').find("option:selected").text()}, function (result){
        Ajax.post(
            '/addProduct',
            {
                productCode: $('#productCode').val(),
                categoryId: result.data,
                name:$('#productName').val(),
                unitName: $('#productUnit').val(),
                price: $('#productPrice').val(),
                createDate: $('#productCreateDate').val(),
                remark: $('#productRemark').val(),
                num: $('#productNum').val(),
                poNum: $('#poNum').val(),
                soNum: $('#soNum').val()
            },
            function (result) {
                doResult(result, function () {
                    layer.closeAll();
                    alert(result.msg);
                    show('product.html');
                    //layer.alert(result.msg)
                })
            }
        )
    })

}

//测试是否符合要求
function testProduct() {
    if($('#productCode').val()==""){
        alert("产品编号不能为空");
        return false;
    }
    if($('#productName').val()==""){
        alert("产品名称不能为空");
        return false;
    }
    if($('#productUnit').val()==""){
        alert("产品计量单位不能为空");
        return false;
    }
    if($('#productPrice').val()==""){
        alert("产品销售价格不能为空");
        return false;
    }
    if($('#productCreateDate').val()==""){
        alert("产品创建日期不能为空");
        return false;
    }
    if($('#poNum').val()==""){
        alert("采购在途数不能为空");
        return false;
    }
    if($('#soNum').val()==""){
        alert("销售待发数不能为空");
        return false;
    }
    return saveProduct();
}

function updateProduct(obj) {
    Ajax.getHtml('/productUpdate.html', function (html) {
        // $(function (){
        //     let date = new Date().getFullYear() + '' + (new Date().getMonth() + 1) + '' + new Date().getDate() + '' + new Date().getSeconds();
        //     $('#categoryId').attr("value", date);
        // })
        Ajax.post('/getAllCategoryName', {}, function (result){
            $.each(result.data, function (i, item){
                // $('#selectBox').options.add(new Option(item))
                $('#selectBox').append("<option>"+item+"</option>");

            })
            $(function () {
                var a = obj.parentNode.parentNode.cells[1].innerText;
                $('#productCode').attr("value",a);
                var b = obj.parentNode.parentNode.cells[2].innerText;
                $('#selectBox').val(b);
                var c = obj.parentNode.parentNode.cells[3].innerText;
                $('#productName').attr("value",c);
                var d = obj.parentNode.parentNode.cells[4].innerText;
                $('#productUnit').attr("value",d);
                var e = obj.parentNode.parentNode.cells[5].innerText;
                $('#productPrice').attr("value",e);
                var f = obj.parentNode.parentNode.cells[6].innerText;
                $('#productCreateDate').attr("value",f);
                var g = obj.parentNode.parentNode.cells[7].innerText;
                $('#productRemark').attr("value",g);
                var h = obj.parentNode.parentNode.cells[8].innerText;
                $('#productNum').attr("value",h);
                var i = obj.parentNode.parentNode.cells[9].innerText;
                $('#poNum').attr("value",i);
                var j = obj.parentNode.parentNode.cells[10].innerText;
                $('#soNum').attr("value",j);
            });
        })


        layer.open({
            type: 1,
            title: '确认修改产品',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                testProductUpdate();
            },
            btn2: function () {
                console.log('')
            }
        })
    })
}

function testProductUpdate() {
    if($('#selectBox').val()==""){
        alert("类别不能为空");
        return false;
    }
    if($('#productCode').val()==""){
        alert("产品编号不能为空");
        return false;
    }
    if($('#productName').val()==""){
        alert("产品名称不能为空");
        return false;
    }
    if($('#productUnit').val()==""){
        alert("产品计量单位不能为空");
        return false;
    }
    if($('#productPrice').val()==""){
        alert("产品销售价格不能为空");
        return false;
    }
    if($('#productCreateDate').val()==""){
        alert("产品创建日期不能为空");
        return false;
    }
    if($('#poNum').val()==""){
        alert("采购在途数不能为空");
        return false;
    }
    if($('#soNum').val()==""){
        alert("销售待发数不能为空");
        return false;
    }
    return updateProduct2();
}

function updateProduct2(){
    Ajax.post(
        '/updateProduct',
        {
            productCode: $('#productCode').val(),
            categoryId:$('#cateId').val(),
            name:$('#productName').val(),
            unitName: $('#productUnit').val(),
            price: $('#productPrice').val(),
            createDate: $('#productCreateDate').val(),
            remark: $('#productRemark').val(),
            num: $('#productNum').val(),
            poNum: $('#poNum').val(),
            soNum: $('#soNum').val(),
            categoryName: $('#selectBox').val()
        },
        function (result) {
            doResult(result, function () {
                layer.closeAll();
                alert(result.msg);
                show('product.html');
            })
        }
    )
}


//删除产品
function deleteProduct(obj) {
    function delProduct(){
        var a = obj.parentNode.parentNode.cells[1].innerText;
        Ajax.post("/removeProduct",{productCode:a},function (result) {
            doResult(result,function () {
                layer.closeAll();
                alert(result.msg);
                show('product.html');
            })
        })
    }
    Ajax.getHtml('/blank.html', function (html) {

        layer.open({
            type: 1,
            title: '确认删除',
            area: ['700px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                delProduct();
            },
            btn2: function () {
                layer.closeAll();
            }
        })
    })
}


function searchProductStockByMonth(){
    let dateRange=$('#productStockTime').val();
    let startDate = dateRange + "-1";
    let endDate = dateRange + "-31";
    showPage({
        currentPage: 1,
        pageSize: 4,
        url: '/searchProductStockByMonth',
        templateId: 'productStockListTemplate',
        contentId: 'productStockTableBody',
        params:{
            startDate:startDate,
            endDate:endDate
        }
    })
}

function showProductReportDetails(obj){
    let p = obj.innerText;
    Ajax.getHtml('/productInfo.html', function (html){
        Ajax.post('/findByProductCode', {productCode:p}, function (result){
            $('#productInfoCode').attr('value',result.data.productCode);
            $('#defaultCategoryInfo').text(result.data.categoryId);
            $('#productInfoName').attr('value',result.data.name);
            $('#productInfoUnit').attr('value',result.data.unitName);
            $('#productInfoPrice').attr('value',result.data.price);
            $('#productInfoCreateDate').attr('value',result.data.createDate);
            $('#productInfoRemark').text(result.data.remark);
            $('#productInfoNum').attr('value',result.data.num);
            $('#poInfoNum').attr('value',result.data.poNum);
            $('#soInfoNum').attr('value',result.data.soNum);
        })

        layer.open({
            type: 1,
            title: '详细信息',
            area: ['1200px'],
            height:['300px'],
            content: html,
            btn: ['确认', '取消'],
            yes: function () {
                //saveCount(productCode);
                layer.closeAll();
            },
            btn2: function () {
                console.log('')
            }
        })
    })

}

