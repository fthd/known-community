var addTabs = function(options) {
        var url = window.location.protocol + '//' + window.location.host + "/";
        options.url = url + options.url;
        id = "tab_" + options.id;
        var title = "", content = "";
        //如果TAB不存在，创建一个新的TAB
        if (!$("#" + id)[0]) {
            var mainHeight = App.getViewPort().height - $('.page-footer').outerHeight() - $('.page-header').outerHeight() - $(".content-tabs").height();
            //固定TAB中IFRAME高度
            // mainHeight = $(document.body).height() - 90;
            //创建新TAB的title
            title = '<a href="javascript:void(0);" id="tab_' + id + '"  data-id="' + id + '"  class="menu_tab" >' + options.title;
            //是否允许关闭
            if (options.close) {

                title += ' <i class="fa fa-remove page_tab_close" style="cursor: pointer;" data-id="' + id + '" onclick="closeTab(this)"></i>';
            }
            title += '</a>';
            var loadIframe = "";
            //是否指定TAB内容
            if (options.content) {
                content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
            } else { //没有内容，使用IFRAME打开链接
                //如果打开窗体超过10个则提示打开窗体太多
                if ($(".page-tabs-content").find('a').length >= 10) {
                    toastrAlert("warning", "为保证系统效率,只允许同时运行10个功能窗口,请关闭一些窗口后重试！", "警告");
                    return false;
                }
            //    App.startPageLoading({ message: '加载中......' });
            //    App.stopPageLoading();
                App.blockUI({
                    target: '#tab-content',
                    boxed: true,
                    message: '加载中......'//,
                   // animate: true
                });
                content = '<div role="tabpanel" class="tab-pane" id="' + id + '">';

                loadIframe = '<iframe onload="javascript:App.unblockUI(\'#tab-content\');" src="' + options.url + '" width="100%" height="' + mainHeight +
                    '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"  allowtransparency="yes" id="iframe_' + id + '" class="  tab_iframe"></iframe>';

                 content += loadIframe;
                 content += '</div>';
               
            }
           
            //加入TABS
            $(".page-tabs-content").append(title);

            $("#tab-content").append(content);
         
        }
        $(".page-tabs-content > a.active").removeClass("active");
        $("#tab-content > .active").removeClass("active");

        //var height = $(".tab_iframe").height() + 1;
        //$(".tab_iframe").css({
        //    height: height
        //});
        //激活TAB
        $("#tab_" + id).addClass('active');
        $("#" + id).addClass("active");

    };
/**
 * 弹出消息
 * @param {} type 类型：success,warning,info等
 * @param {} content 
 * @param {} title 
 * @returns {} 
 */
    var toastrAlert = function(type, content, title) {

        toastr.options = {
            "closeButton": true,
            "debug": false,
            "positionClass": "toast-bottom-right",
            "showDuration": "1000",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }
        toastr[type](content, title);

    }

    var closeTab = function(item) {
        var id = $(item).attr("data-id");
        //如果关闭的是当前激活的TAB，激活他的前一个TAB
        if ($(".page-tabs-content > a.active").attr('id') === "tab_" + id) {
            var prev = $("#tab_" + id).prev();
            var prevIframe = $("#" + id).prev();

            setTimeout(function() { //某种bug，需要延迟执行
                prev.addClass('active');
                prevIframe.addClass('active');
            }, 300);
        }

        ////关闭TAB
        $("#tab_" + id).remove();
        $("#" + id).remove();

    };
    var closeCurrentTab = function() {
        var currentTab = $('.page-tabs-content').find('.active').find('.fa-remove').parents('a');
        if (currentTab) {
            closeTab(currentTab);
        }
    }
    var refreshTab = function() {
        var currentId = $('.page-tabs-content').find('.active').attr('data-id');
        var target = $('#iframe_' + currentId);
        var url = target.attr('src');

        target.attr('src', url);
    }

    var closeOtherTabs = function(isAll) {
        if (isAll) {
            $('.page-tabs-content').children("[data-id]").find('.fa-remove').parents('a').each(function() {
                $('#' + $(this).data('id')).remove();
                $(this).remove();
            });
            var firstChild = $(".page-tabs-content").children(); //选中那些删不掉的第一个菜单
            if (firstChild) {
                $('#' + firstChild.data('id')).addClass('active');
                firstChild.addClass('active');
            }
        } else {
            $('.page-tabs-content').children("[data-id]").find('.fa-remove').parents('a').not(".active").each(function() {
                $('#' + $(this).data('id')).remove();
                $(this).remove();
            });

        }


    }
    var activeTab = function() {


        var id = $(this).attr("data-id");
        $(".menu_tab").removeClass("active");
        $("#tab-content > .active").removeClass("active");
        //激活TAB
        $("#tab_" + id).addClass('active');
        $("#" + id).addClass("active");
    }
//$(function () {
//    mainHeight = $(document.body).height() - 45;
//    $('.main-left,.main-right').height(mainHeight);
//});
 $(function () {
    $(".menuTabs").on("click", ".menu_tab", activeTab);
});