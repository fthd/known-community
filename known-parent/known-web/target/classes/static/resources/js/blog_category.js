known.categoryUrl = {
    loadCategories: known.realpath + "/admin/loadBlogCategories.action",
    saveCategory: known.realpath + "/admin/saveBlogCategory.action",
    delCategory: known.realpath + "/admin/delBlogCategory.action"
};
$(function() {
    known.categories = $("#categories");

    loadCategories();

    $(document).on("click", ".edit-btn", function() {
        editCategory($(this));
    });

    $(document).on("click", ".save-btn", function() {
        saveCategory($(this));
    });

    $(document).on("click", ".del-btn", function() {
        delCategory($(this));
    });

    $("#add-category").click(function() {
        addCategory($(this));
    });
});


function delCategory(curObj) {
    var categoryid = curObj.attr("categoryid");
    layer.confirm('是否删除该分类？', {
        btn: ['是', '否'], //按钮
        shade: false //不显示遮罩
    }, function() {
        var d = dialog({
            content: "<div><img src='" + known.realpath + "/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;删除中...</div>",
        });
        d.showModal();
        setTimeout(function() {
            d.close().remove();
        }, 1000);
        $.ajax({
            url: known.categoryUrl.delCategory,
            type: 'POST',
            dataType: 'json',
            data: {
                categoryId: categoryid
            },
            success: function(res) {
                if (res.errorMsg != null) {
                    layer.msg(res.errorMsg, {
                        icon: 5,
                        time: 1500 //2秒关闭（如果不配置，默认是3秒）
                    });
                } else {
                    layer.msg('删除成功', {
                        icon: 1,
                        time: 1500 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        loadCategories();
                    });
                }
            }
        });
    }, function() {

    });
}

function loadCategories() {
    known.categories.empty();

    $.ajax({
        url: known.categoryUrl.loadCategories,
        type: 'POST',
        dataType: 'json',
        data: {},
        success: function(res) {
            var list = res.data;
            if (list.length == 0) {
                $("<div class='no-data'>没有分类，点击新增分类添加吧。</div>").appendTo(known.categories);
            } else {
                for (var i = 0, _len = list.length; i < _len; i++) {
                    new CategoryItem(list[i]).appendTo(known.categories);
                }
            }
        }
    });
}

function CategoryItem(data) {
    var item = $("<div class='category'></div>");
    new editItem(data, true).appendTo(item);
    return item;
}

function editItem(data) {
    var item = $("<div class='edit-item'></div>");
    var form = $("<form></form>").appendTo(item);
    $("<input type='hidden' name='categoryId' value=" + data.categoryId + ">").appendTo(form);
    $("<span class='cate-rank'><input type='text' name='rank' readonly='readonly'  placeholder='请输入序号' class='none-input' value=" + data.rank + "></span>").appendTo(form);
    $("<span class='cate-name'><input type='text' name='name' readonly='readonly' placeholder='请输入分类名称' class='none-input' value=" + data.name + "></span>").appendTo(form);
    $("<a href='javascript:;' class='edit-btn'>修改</a>").appendTo(form);
    $("<a href='javascript:;' class='save-btn'>保存</a>").appendTo(form);
    $("<a href='javascript:;' class='del-btn' categoryid=" + data.categoryId + ">删除</a>").appendTo(form);
    return item;
}

// 编辑分类
function editCategory(curObj) {
    var edit_item = curObj.parent();
    var input = edit_item.find("span input");
    var save_btn = edit_item.find(".save-btn")
    input.removeClass("none-input");
    input.removeAttr("readonly");
    $(curObj).hide();
    save_btn.show();
    input.focus();
}

function addCategory(curObj) {
    var rank = 0;
    var pid = 0;
    var children_panel;
    rank = known.categories.find(".category").length;
    var data = {
        name: "",
        categoryId: '',
        rank: rank + 1
    }
    if (known.categories.find(".no-data").length > 0) {
        known.categories.empty();
    }
    var category = new CategoryItem(data).appendTo(known.categories);
    editCategory(category.find(".edit-btn"));
}

// 保存分类
function saveCategory(curObj) {
    var form = curObj.parent();
    var name = $.trim(form.find("input[name=name]").val());
    var pid = $.trim(form.find("input[name=pid]").val());
    var rank = $.trim(form.find("input[name=rank]").val());
    var numberReg = /^\d+$/;
    if (name == "") {
        layer.msg("分类名称不能为空", {
            icon: 5,
            time: 1500 //2秒关闭（如果不配置，默认是3秒）
        });
        return;
    }
    if (rank == "" || !numberReg.test(rank)) {
        layer.msg("编号只能是数字，且不能为空", {
            icon: 5,
            time: 1500 //2秒关闭（如果不配置，默认是3秒）
        });
        return;
    }


    var d = dialog({
        content: "<div><img src='" + known.realpath + "/resources/images/loading.gif' />&nbsp;&nbsp;&nbsp;删除中...</div>",
    });
    d.showModal();
    setTimeout(function() {
        d.close().remove();
    }, 1000);
    $.ajax({
        url: known.categoryUrl.saveCategory,
        type: 'POST',
        dataType: 'json',
        data: form.serialize(),
        success: function(res) {
            if (res.errorMsg != null) {
                layer.msg(res.errorMsg, {
                    icon: 5,
                    time: 1500 //2秒关闭（如果不配置，默认是3秒）
                });
            } else {
                layer.msg('修改成功', {
                    icon: 1,
                    time: 1500 //2秒关闭（如果不配置，默认是3秒）
                }, function() {
                    loadCategories();
                });
            }
        }
    });
}