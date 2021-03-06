<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			$(function() {
				var exam = document.getElementsByName("exam");
				for(var i = 1; i <= exam.length; i++) {
					if(i == 1) {
						$("#pageNum" + i).show();
					} else {
						$("#pageNum" + i).hide();
					}
				}
			})

			//答题时上一页下一页切换
			function setTab(index) {
				var exam = document.getElementsByName("exam");
				for(var i = 1; i <= exam.length; i++) {
					if(index == i) {
						$("#pageNum" + index).show();
					} else {
						$("#pageNum" + i).hide();
					}
				}
			}

			//提交答案
			function save() {
				var num = 0;
				var count = ${queCount};
				for(var i = 1; i <= count; i++) {
					for(var j = 0; j < document.getElementsByName("que" + i).length; j++) {
						if(document.getElementsByName("que" + i)[j].checked) {
							num++;
							break;
						} else if(j == document.getElementsByName("que" + i).length - 1) {
							layer.confirm('您还有题目未作答,确认要提交吗?', {
								title: '提示',
								offset: ['30%', '40%'],
								shade: 0.01
							}, function(index) {
								layer.close(index);
								$("#form").submit();
							});
						}
					}
				}
				if(num == count) {
					layer.confirm('您确认要提交吗?', {
						title: '提示',
						offset: ['30%', '40%'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$("#form").submit();
					});
				}
			}

			//表单防重复提交
			var isCommitted = false; //表单是否已经提交标识，默认为false
			function dosubmit() {
				if(isCommitted == false) {
					isCommitted = true; //提交表单后，将表单是否已经提交标识设置为true
					return true; //返回true让表单正常提交
				} else {
					return false; //返回false那么表单将不提交
				}
			}
		</script>
	</head>

	<body>
		<div class="container">
			<div class="col-md-12 mb10 border1 bggrey mt10">
				<div class="fl f18">考生姓名：<span class="blue b">${user.relName }</span></div>
			</div>
			<div class="col-md-12 f18 b p0">
				<c:if test="${singlePoint!=0&&multiplePoint!=0 }">
					本次考试题型包括：单选题和多选题，其中：单选题${singleNum }题，每题${singlePoint }分；多选题${multipleNum }题，每题${multiplePoint }分。
				</c:if>
				<c:if test="${singlePoint!=0&&multiplePoint==0 }">
					本次考试题型包括：单选题，共${singleNum }题，每题${singlePoint }分。
				</c:if>
				<c:if test="${singlePoint==0&&multiplePoint!=0 }">
					本次考试题型包括：多选题，共${multipleNum }题，每题${multiplePoint }分。
				</c:if>
			</div>
			<form action="${pageContext.request.contextPath }/expertExam/saveScore.html" method="post" id="form" class="clear mt10" onsubmit="return dosubmit()">
				<c:choose>
					<c:when test="${pageSize==1 }">
						<table class="clear table table-bordered table-condensed" id="pageNum1" name="exam">
							<tbody>
								<c:forEach items="${queRandom }" var="que" varStatus="l">
									<tr>
										<td class="col-md-1 tc info">${l.index+1 }</td>
										<td class="col-md-11">
											<div><span class="mr10">【${que.examQuestionType.name}】</span><span>${que.topic }</span></div>
											<c:if test="${que.examQuestionType.name=='单选题' }">
												<c:forEach items="${fn:split(que.items,';')}" var="it">
													<div class="mt10 clear fl">
														<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it }
													</div>
												</c:forEach>
											</c:if>
											<c:if test="${que.examQuestionType.name=='多选题' }">
												<c:forEach items="${fn:split(que.items,';')}" var="it">
													<div class="mt10 clear fl">
														<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it}
													</div>
												</c:forEach>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
							<button class="btn" type="button" onclick="save()">提交</button>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${pageNum }" varStatus="p">
							<c:choose>
								<c:when test="${p.first}">
									<div id="pageNum${p.index+1 }" name="exam">
										<table class="clear table table-bordered table-condensed">
											<c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
												<tr>
													<td class="col-md-1 tc info">${l.index+1 }</td>
													<td class="col-md-11">
														<div><span class="mr10">【${que.examQuestionType.name}】</span><span>${que.topic }</span></div>
														<c:if test="${que.examQuestionType.name=='单选题' }">
															<c:forEach items="${fn:split(que.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${que.examQuestionType.name=='多选题' }">
															<c:forEach items="${fn:split(que.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it}
																</div>
															</c:forEach>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
										<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
											<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
										</div>
									</div>
								</c:when>

								<c:when test="${p.last}">
									<div id="pageNum${p.index+1 }" name="exam">
										<table class="clear table table-bordered table-condensed">
											<c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
												<tr>
													<td class="col-md-1 tc info">${l.index+1 }</td>
													<td class="col-md-11">
														<div><span>[${que.examQuestionType.name}]</span><span>${que.topic }</span></div>
														<c:if test="${que.examQuestionType.name=='单选题' }">
															<c:forEach items="${fn:split(que.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${que.examQuestionType.name=='多选题' }">
															<c:forEach items="${fn:split(que.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it}
																</div>
															</c:forEach>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
										<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
											<button class="btn" type="button" onclick="setTab(${p.index})">上一页</button>
											<button class="btn" type="button" onclick="save()">提交</button>
										</div>
									</div>
								</c:when>

								<c:otherwise>
									<div id="pageNum${p.index+1 }" name="exam">
										<table class="clear table table-bordered table-condensed">
											<c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
												<tr>
													<td class="col-md-1 tc info">${l.index+1 }</td>
													<td class="col-md-11">
														<div><span>[${que.examQuestionType.name}]</span><span>${que.topic }</span></div>
														<c:if test="${que.examQuestionType.name=='单选题' }">
															<c:forEach items="${fn:split(que.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${que.examQuestionType.name=='多选题' }">
															<c:forEach items="${fn:split(que.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0 mr5" />${it}
																</div>
															</c:forEach>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
										<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
											<button class="btn" onclick="setTab(${p.index})" type="button">上一页</button>
											<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:otherwise>

				</c:choose>
				<input type="hidden" name="queAnswer" value="${queAnswer}" />
				<input type="hidden" name="queType" value="${queType}" />
				<input type="hidden" name="queId" value="${queId }" />
			</form>
		</div>
	</body>

</html>