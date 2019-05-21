known.letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
	known.chooseType = {
		type1: 1,
		type2: 2
	}
	$(document).ready(function() {

		loadAllExams();
	});
		$(document).on('mouseover', '.examItem', function(event) {
			$(".currentExamNumber").text($(this).attr('index'));
		});
		$("#doTheExam").click(function(event) {
			doTheExam();
		});
		function loadAllExams(){
			$.ajax({
				url: '/exam/loadAllExam',
				type: 'POST',
				dataType: 'json',
				data: {"categoryId": known.categoryId},
				success:function(res){
					if(res.msg != null){
					layer.alert(res.msg, {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
					}
					else{
						var list = res.data;
						var _len = list.length;
						$(".totalExamNumber").text(_len);
						if (_len > 0) {
							for (var i = 0, data; i < _len, data = list[i]; i++) {
								new ExamItem(data, i).appendTo($("#examList"));
							}
						} else {
							$("<div class='no-data'>该分类下没有考题</div>").appendTo($("#examList"));
							$("#doTheExam").remove();
						}
					}
				}
			});
			
		}
		function ExamItem(data, i){
			var examItem = $('<div class="examItem" index="' + (i+1) + '" examid="' + data.id +'" id="exam' + data.id +'"></div>');
			var typetit = "单选";
			var userName = data.userName;
			if (data.chooseType.type == known.chooseType.type1) {
				typetit = "单选";
			} else if (data.chooseType.type == known.chooseType.type2) {
				typetit = "多选";
			}
			var itemNum = $('<div class="itemNum">第<span class="itemNum-no">' + (i+1) + '</span>题<span class="itemChooseType">(' + typetit + ')</span><span class="postExamUser">发布者:' + userName + '</span></div>').appendTo(examItem);
			var examTitle = $('<div class="itemTitle"><p>' + data.examTitle + '</p></div>').appendTo(examItem);
			var examAnswer = $('<div class="itemAnswer"></div>').appendTo(examItem);
			var subs = data.examDetails;
			for (var i = 0, _len = subs.length; i < _len; i++) {
				var letter = known.letters[i];
				if (data.chooseType.type == known.chooseType.type1) {
					$('<div class="radio"><label id="answer' + subs[i].id + '" letter="' + letter + '" ><input type="radio" name="answer' + data.id + '" value="' + subs[i].id + '" >' + letter + '.' + subs[i].answer + '</label></div>').appendTo(examAnswer);
				} else if (data.chooseType.type == known.chooseType.type2) {
					$('<div class="checkbox"><label id="answer' + subs[i].id + '" letter="' + letter + '"><input type="checkbox" name="answer' + data.id + '" value="' + subs[i].id + '" >' + letter + '.' + subs[i].answer + '</label></div>').appendTo(examAnswer);
				}
			}
			return examItem;
		}
		function doTheExam(){
			var examItems = $(".examItem");
			var examIds = "";
			var rightAnswers = "";
			for (var i = 0, _len = examItems.length; i < _len; i++) {
			var examItem = examItems.eq(i);
			var inputs = examItem.find("input:checked");
			if (inputs.length == 0) {
				layer.alert("第"+ (i+1) + "道题未选", {
					  icon: 5,
					  skin: 'layer-ext-moon' 
					});
				return false;
				}
			}

				for (var i = 0, _len = examItems.length; i < _len; i++) {
					if (i < _len - 1) {
						examIds = examIds + examItems.eq(i).attr("examid") + ",";
					} else {
						examIds = examIds + examItems.eq(i).attr("examid");
					}
				}
				
				var answers = $("input:checked");
				for (var i = 0, _len = answers.length; i < _len; i++) {
					if (i < _len - 1) {
						rightAnswers = rightAnswers + answers.eq(i).val() + ",";
					} else {
						rightAnswers = rightAnswers + answers.eq(i).val();
					}
				}
				$.ajax({
					url: '/exam/doMark',
					type: 'POST',
					dataType: 'json',
					data: {"examIds": examIds,
							"rightAnswers": rightAnswers
						},
					success:function(res){
						if(res.msg != null){
							layer.alert(res.msg, {
							  icon: 5,
							  skin: 'layer-ext-moon' 
							});
						}
						else{

							var list = res.data;
							var rightCount = 0;
							for (var i = 0, _len = list.length; i < _len; i++) {
								var answer = list[i];
								setResultInfo(answer);
								if (answer.correct) {
									rightCount++;
								}
							}
							$("input").prop("disabled", true);
							$("#realMark").text(rightCount * 5);
							$("#mymark").show();
							$("#doTheExam").remove();
						}
					}
				});		
		}

		function setResultInfo(answer) {
		var examId = answer.id;
		var exam_item = $("#exam" + examId);
		if (answer.correct) {
			$("<i class='exam-right'>").appendTo(exam_item);
		} else {
			$("<i class='exam-wrong'>").appendTo(exam_item);
			var correctAnswerIds = answer.correctAnswerIds;
			var answerLetter = "";
			for (var i = 0, _len = correctAnswerIds.length; i < _len; i++) {
				answerLetter = answerLetter + " " + $("#answer" + correctAnswerIds[i]).attr("letter");
			}
			$('<div class="right-answer"><span>正确答案:</span><span>' + answerLetter + '</span></div>').appendTo(exam_item);
			if (answer.analyse != null && answer.analyse != "") {
				$('<div class="analyse"><div class="analyse-tit">答案解析</div><div class="analyse-con">' + answer.analyse + '</div>').appendTo(exam_item);
			}
		}
	}