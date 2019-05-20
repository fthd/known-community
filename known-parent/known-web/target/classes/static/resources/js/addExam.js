known.letter = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
		$("#addAnswer").click(function(event) {
			answerItem();
		});
		$(document).on("click", ".icon-del", function() {
			var index = $("#answerList div.row").length;
			var curIndex = $(this).attr('index');
			for(var i=curIndex; i < index; i++){
				$(".answer-" + i).remove();
				$(".rightAnswer-" + i).remove();
			}
		});
		function answerItem(){
			var index = $("#answerList div.row").length;
			if (index == 10) {
				layer.alert("最多只能有十个选项", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return;
			}
			var letter = known.letter[index];
			$('<div class="row answer-' + index + '"><label class="col-xs-1 control-label">' + letter + '：</label><div class="col-xs-9"><input name="answer" type="text" class="form-control answer" placeholder="输入答案"></div><div class="col-xs-2"><a href="javascript:;" index="' + index + '" class="icon icon-del"></a></div></div>').appendTo('#answerList');
			$('<label class="checkbox-inline rightAnswer-' + index + '"><input class="rightAnswer" name="rightAnswer" type="checkbox" id="" value="' + index + '">' + letter + '</label>').appendTo('#rightAnswerList');
		}
		$(document).on('click', "#postExamBtn", function(event) {
			if(known.userId=="" || known.userId == 0){
	    		goLogin();
	    	    return;
	    	}
			var index = $("#answerList div.row").length;
			var postExamForm = $("#postExamForm");
			var categoryId = $("#categoryId");
			var examTitle = $("#examTitle");
			var examChooseType = $("input[name='chooseType']:checked").val();
			var rightAnswers = $("input[name='rightAnswer']:checked");
			var answers = $("input[name='answer']");
			if(categoryId.val() == null || categoryId.val() == ''){
				layer.alert("请选择分类", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				categoryId.parent().parent().parent().addClass('has-error');
				return;
			}
			categoryId.parent().parent().parent().removeClass('has-error');
			if(examTitle.val()==null || $.trim(examTitle.val()) == ''){				
				layer.alert("题目不能为空", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				examTitle.parent().parent().addClass('has-error');
				return;
			}
			examTitle.parent().parent().removeClass('has-error');
			for(var i = 0; i < index; i++){
				if(answers[i].value == null || $.trim(answers[i].value) == ""){
					layer.alert(known.letter[i] + "答案不能为空", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
					return ;
				}
			}
			if (rightAnswers.length == 0) {
				layer.alert("请选择正确答案", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return ;
			}
			if (examChooseType == 1 && rightAnswers.length > 1) {
				layer.alert("单选题只能有一个答案", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return ;
			}
			if (examChooseType == 2 && rightAnswers.length == 1) {
				layer.alert("多选题不能只有一个答案", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return ;
			}
			if (examChooseType == 2 && rightAnswers.length == answers.length) {
				layer.alert("不能所有答案都为正确答案", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return ;
			}
			var index = layer.load(1, {shade: [0.1,'#fff'], time: 1500});
			$.ajax({
				url: '/exam/postExam',
				type: 'POST',
				dataType: 'json',
				data: $("#postExamForm").serialize(),
				success:function(res){
					if(res.errorMsg != null){
							layer.alert(res.errorMsg, {
							  icon: 5,
							  skin: 'layer-ext-moon' 
							});
							return;
						}
					layer.msg('试题发布成功', {
					  icon: 1,
					  time: 1000 //2秒关闭（如果不配置，默认是3秒）
					}, function(){
					  	layer.confirm('您是继续发布考题？', {
						  btn: ['是','否'] //按钮
						}, function(index){
							clearExamForm();
						  layer.close(index);
						}, function(){
						  document.location.href = known.realpath + "/exam";
						});
					});  
				}

			});
		});
		function clearExamForm(){
			var index = $("#answerList div.row").length;
				$("#categoryId").val("");
				$("#examTitle").val("");
				$(".answer").val("");
				$(".rightAnswer").removeAttr('checked');
				$(".analyse").val("");
			for(var i=2; i < index; i++){
				$(".answer-" + i).remove();
				$(".rightAnswer-" + i).remove();
			}
		}