/**
 * 
 */
package ses.controller.sys.ems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ses.model.ems.ExamPaper;
import ses.model.ems.ExamQuestion;
import ses.model.ems.ExamQuestionType;
import ses.model.ems.ExamUserAnswer;
import ses.model.ems.ExamUserScore;
import ses.service.ems.ExamPaperServiceI;
import ses.service.ems.ExamPaperUserServiceI;
import ses.service.ems.ExamQuestionServiceI;
import ses.service.ems.ExamQuestionTypeServiceI;
import ses.service.ems.ExamUserAnswerServiceI;
import ses.service.ems.ExamUserScoreServiceI;


/**
 * @Title:PurchaserExamController 
 * @Description: 采购人考试Controller层
 * @author ZhaoBo
 * @date 2016-9-7上午10:39:06
 */
@Controller
@RequestMapping("/purchaserExam")
public class PurchaserExamController {
	@Autowired
	private ExamQuestionServiceI examQuestionService;
	@Autowired
	private ExamQuestionTypeServiceI examQuestionTypeService;
	@Autowired
	private ExamUserScoreServiceI examUserScoreService;
	@Autowired
	private ExamPaperServiceI examPaperService;
	@Autowired
	private ExamPaperUserServiceI examPaperUserService;
	@Autowired
	private ExamUserAnswerServiceI examUserAnswerService;
	
	/**
	 * 
	* @Title: purchaserList
	* @author ZhaoBo
	* @date 2016-9-7 上午11:25:28  
	* @Description: 采购人题库管理页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/purchaserList")
	public String purchaserList(){
		return "ses/ems/exam/purchaser/question/list";
	}
	
	/**
	 * 
	* @Title: getAllPurchaserQuestion
	* @author ZhaoBo
	* @date 2016-9-19 下午1:31:17  
	* @Description: 查询所有的采购人题库 
	* @param @return      
	* @return List<ExamQuestion>
	 */
	@RequestMapping("/getAllPurchaserQuestion")
	@ResponseBody
	public List<ExamQuestion> getAllPurchaserQuestion(){
		List<ExamQuestion> examQuestion = examQuestionService.getAllPurchaserQuestion();
		List<ExamQuestion> newExamQuestion = new ArrayList<ExamQuestion>();
		for(int i = 0;i<examQuestion.size();i++){
			if(examQuestion.get(i).getExamQuestionType().getName().equals("单选题")){
				newExamQuestion.add(examQuestion.get(i));
			}
		}
		for(int i = 0;i<examQuestion.size();i++){
			if(examQuestion.get(i).getExamQuestionType().getName().equals("多选题")){
				newExamQuestion.add(examQuestion.get(i));
			}
		}
		for(int i = 0;i<examQuestion.size();i++){
			if(examQuestion.get(i).getExamQuestionType().getName().equals("判断题")){
				newExamQuestion.add(examQuestion.get(i));
			}
		}
		return newExamQuestion;
	}
	
	/**
	 * 
	* @Title: queryPurchaser
	* @author ZhaoBo
	* @date 2016-9-7 上午11:26:37  
	* @Description: 根据条件查询采购人题库  
	* @param @param request
	* @param @param examQuestion
	* @param @return      
	* @return List<ExamPool>
	 */
	@RequestMapping("/queryPurchaser")
	@ResponseBody
	public List<ExamQuestion> queryPurchaser(HttpServletRequest request,ExamQuestion examQuestion){
		List<ExamQuestion> queryList = new ArrayList<ExamQuestion>();
		if(!request.getParameter("queType").isEmpty()){
			examQuestion.setQuestionTypeId(Integer.parseInt(request.getParameter("queType")));
		}
		if(!request.getParameter("queName").isEmpty()){
			String queName = request.getParameter("queName");
			examQuestion.setTopic("%"+queName+"%");
		}
		queryList = examQuestionService.queryPurchaserByTerm(examQuestion);
		return queryList;
	}
	
	/**
	 * 
	* @Title: addPurQue
	* @author ZhaoBo
	* @date 2016-9-7 上午11:27:18  
	* @Description: 采购人新增题库页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addPurQue")
	public String addPurQue(){
		return "ses/ems/exam/purchaser/question/add";
	}
	
	/**
	 * 
	* @Title: saveToPurPool
	* @author ZhaoBo
	* @date 2016-9-7 上午11:28:10  
	* @Description: 采购人新增题库方法 
	* @param @param model
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToPurPool")
	public String saveToPurPool(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setQuestionTypeId(Integer.parseInt(request.getParameter("queType")));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A"+queOption[0].trim()+";");
		sb_option.append("B"+queOption[1].trim()+";");
		sb_option.append("C"+queOption[2].trim()+";");
		sb_option.append("D"+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		examQuestion.setPersonType(2);
		examQuestion.setCreatedAt(new Date());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		if(request.getParameter("judge")!=null){
			String[] queJudge = request.getParameterValues("judge");
			for(int i = 0;i<queJudge.length;i++){
				sb.append(queJudge[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
		examQuestionService.insertSelective(examQuestion);
		return "redirect:purchaserExam.html";
	}
	
	/**
	 * 
	* @Title: editPurQue
	* @author ZhaoBo
	* @date 2016-9-7 上午11:28:50  
	* @Description: 采购人修改题库页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editPurQue")
	public String editPurQue(HttpServletRequest request,Model model){
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("purchaserQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("purchaserAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectPurchaserAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(1));
		model.addAttribute("optionB", queOption[1].substring(1));
		model.addAttribute("optionC", queOption[2].substring(1));
		model.addAttribute("optionD", queOption[3].substring(1));
		return "ses/ems/exam/purchaser/question/edit";
	}
	
	/**
	 * 
	* @Title: editToPurchaser
	* @author ZhaoBo
	* @date 2016-9-7 上午11:29:36  
	* @Description: 采购人题库修改保存 
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editToPurchaser")
	public String editToPurchaser(HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setId(request.getParameter("id"));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A"+queOption[0].trim()+";");
		sb_option.append("B"+queOption[1].trim()+";");
		sb_option.append("C"+queOption[2].trim()+";");
		sb_option.append("D"+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		if(request.getParameter("judge")!=null){
			String[] queJudge = request.getParameterValues("judge");
			for(int i = 0;i<queJudge.length;i++){
				sb.append(queJudge[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:purchaserExam.html";
	}
	
	/**
	 * 
	* @Title: deleteById
	* @author ZhaoBo
	* @date 2016-9-7 上午11:12:08  
	* @Description: 删除题库中的题目 
	* @param @param request
	* @param @return      
	* @return Integer
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public Integer deleteById(HttpServletRequest request){
		String[] ids = request.getParameter("ids").split(",");
		Integer id = null;
		for(int i = 0;i<ids.length;i++){
			id = examQuestionService.deleteByPrimaryKey(ids[i]);
		}
		return id;
	}
	
	private static final String uploadFolderName = "uploadFiles"; //上传到服务器的文件夹名 
	private static String [] extensionPermit = {"xls","xlsx"}; //允许上传的文件格式
	
	/**
	 * 
	* @Title: importExcel
	* @author ZhaoBo
	* @date 2016-9-7 上午11:31:37  
	* @Description: 导入Excel(采购人题库)  
	* @param @param file
	* @param @param session
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws FileNotFoundException
	* @param @throws IOException      
	* @return String
	 */
	@RequestMapping(value="/importExcel",method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
        String saveDirectoryPath = curProjectPath + "/" + uploadFolderName;  
        // File newFileName = new File(saveDirectoryPath); 
        // 判断文件是否存在  
        String fileName = null;
        File excelFile = null;
        if (!file.isEmpty()) {  
            fileName = file.getOriginalFilename();  
            String fileExtension = FilenameUtils.getExtension(fileName);   
            if(!Arrays.asList(extensionPermit).contains(fileExtension)){
               
            } 
            excelFile = new File(saveDirectoryPath,System.currentTimeMillis()+file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
        }
        //File excelFile = new File(newFileName, fileName);
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		Sheet sheet = workbook.getSheetAt(0);
		for (int j=1;j<=sheet.getPhysicalNumberOfRows();j++) {
			Row row = sheet.getRow(j);
			if (row==null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queOption = row.getCell(2);
				Cell queAnswer = row.getCell(3);
				Cell quePoint = row.getCell(4);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(2);
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setItems(queOption.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setPoint((int) quePoint.getNumericCellValue());
				if(queType.toString().equals("单选题")) {
					examQuestion.setQuestionTypeId(1);
				}else{
					examQuestion.setQuestionTypeId(2);
				}
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
			if(queType.toString().equals("判断题")){
				ExamQuestion examQuestion = new ExamQuestion();
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(3);
				Cell quePoint = row.getCell(4);
				examQuestion.setPersonType(2);
				examQuestion.setItems(" ");
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setPoint((int) quePoint.getNumericCellValue());
				examQuestion.setQuestionTypeId(3);
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
		}
		return "1";
	}
	
	/**
	 * 
	* @Title: timing
	* @author ZhaoBo
	* @date 2016-9-6 下午2:33:17  
	* @Description: 采购人开始考试之前等待页面  
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/timing")
	public String timing(Model model,HttpServletRequest request){
		String paperNo = request.getParameter("paperNo");
		ExamPaper examPaper = examPaperService.selectByPaperNo(paperNo);
		model.addAttribute("paperId", examPaper.getId());
		return "ses/ems/exam/purchaser/timing";
	}
	
	/**
	 * 
	* @Title: test
	* @author ZhaoBo
	* @date 2016-9-6 下午2:39:01  
	* @Description: 采购人开始考试  
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/test")
	public String test(Model model,HttpServletRequest request){
		String paperId = request.getParameter("paperId");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");
		Integer multipleNum = Integer.parseInt(multipleN);
		String judgeN = (String) obj.get("judgeNum");
		Integer judgeNum = Integer.parseInt(judgeN);
		ExamQuestion single = new ExamQuestion();
		single.setSingleNum(singleNum);
		ExamQuestion multiple = new ExamQuestion();
		multiple.setMultipleNum(multipleNum);
		ExamQuestion judge = new ExamQuestion();
		judge.setJudgeNum(judgeNum);
		List<ExamQuestion> singleQue = examQuestionService.selectSingleRandom(single);
		List<ExamQuestion> multipleQue = examQuestionService.selectMultipleRandom(multiple);
		List<ExamQuestion> judgeQue = examQuestionService.selectJudgeRandom(judge);
		List<ExamQuestion> purchaserQue = new ArrayList<ExamQuestion>();
		purchaserQue.addAll(singleQue);
		purchaserQue.addAll(multipleQue);
		purchaserQue.addAll(judgeQue);
		List<Integer> pageNum = new ArrayList<Integer>();
		if(purchaserQue.size()%5==0){
			for(int i=0;i<purchaserQue.size()/5;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<purchaserQue.size()/5+1;i++){
				pageNum.add(i);
			}
		}
		StringBuffer sb_answers = new StringBuffer();
		StringBuffer sb_queTypes = new StringBuffer();
		StringBuffer sb_questionIds =  new StringBuffer();
		for(int i=0;i<purchaserQue.size();i++){
			sb_answers.append(purchaserQue.get(i).getAnswer()+",");
			sb_queTypes.append(purchaserQue.get(i).getExamQuestionType().getName()+",");
			sb_questionIds.append(purchaserQue.get(i).getId()+",");
		}
		model.addAttribute("purQueType",sb_queTypes);
		model.addAttribute("purQueAnswer", sb_answers);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("purchaserQue",purchaserQue);
		model.addAttribute("paperId", paperId);
		model.addAttribute("purQueId", sb_questionIds);
		model.addAttribute("count", 0);
		System.out.println(pageNum.size());
		model.addAttribute("pageSize", pageNum.size());
		return "ses/ems/exam/purchaser/test";
	}
	
	/**
	 * 
	* @Title: paperManage
	* @author ZhaoBo
	* @date 2016-9-7 上午11:33:34  
	* @Description: 历史考卷管理页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/paperManage")
	public String paperManage(){
		return "ses/ems/exam/purchaser/paper/list";
	}
	
	/**
	 * 
	* @Title: loadPaper
	* @author ZhaoBo
	* @date 2016-9-7 上午11:34:49  
	* @Description: 页面初始化加载所有考卷 
	* @param @return      
	* @return List<ExamPaper>
	 */
	@RequestMapping("/loadPaper")
	@ResponseBody
	public List<ExamPaper> loadPaper(){
		List<ExamPaper> examPaper = examPaperService.queryAllPaper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<examPaper.size();i++){
			examPaper.get(i).setStartTrueDate(sdf.format(examPaper.get(i).getStartTime()));
		}
		return examPaper;
	}
	
	/**
	 * 
	* @Title: addPaper
	* @author ZhaoBo
	* @date 2016-9-7 上午11:35:33  
	* @Description: 采购人新增考卷 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addPaper")
	public String addPaper(Model model){
		List<Integer> hour = new ArrayList<Integer>();
		List<Integer> second = new ArrayList<Integer>();
		for(int i=1;i<25;i++){
			hour.add(i);
		}
		for(int i=0;i<60;i++){
			second.add(i);
		}
		model.addAttribute("hour", hour);
		model.addAttribute("second", second);
		return "ses/ems/exam/purchaser/paper/add";
	}
	
	/**
	 * 
	* @Title: saveToExamPaper
	* @author ZhaoBo
	* @date 2016-9-5 下午4:19:29  
	* @Description: 新增考卷并保存 
	* @param @param request
	* @param @param model
	* @param @param examPaper
	* @param @return      
	* @return String
	 * @throws ParseException 
	 */
	@RequestMapping("/saveToExamPaper")
	public String saveToExamPaper(HttpServletRequest request,Model model,ExamPaper examPaper) throws ParseException{
		examPaper.setCreatedAt(new Date());
		examPaper.setName(request.getParameter("paperName"));
		examPaper.setCode(request.getParameter("paperNo"));
		examPaper.setScore(request.getParameter("totalPoint"));
		examPaper.setTestTime(request.getParameter("useTime"));
		String startTime = request.getParameter("startTime");
		String newHour = null;
		String newSecond = null;
		String hour = request.getParameter("hour");
		String second = request.getParameter("second");
		if(hour.length()==1){
			newHour = "0"+hour;
		}else{
			newHour = hour;
		}
		if(second.length()==1){
			newSecond = "0"+second;
		}else{
			newSecond = second;
		}
		String examStartTime = startTime+" "+newHour+":"+newSecond+":"+"00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		examPaper.setStartTime(sdf.parse(examStartTime));
		String[] isAllow = request.getParameterValues("isAllow");
		if(isAllow[0].equals("是")){
			examPaper.setIsAllowRetake(1);
		}else{
			examPaper.setIsAllowRetake(0);
		}
		examPaper.setYear(startTime.substring(0, 4));
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String judgeNum = request.getParameter("judgeNum");
		String judgePoint = request.getParameter("judgePoint");
//		String type = "{\"singleNum\":\""+singleNum+"\",\"singlePoint\":\""+singlePoint+"\",\"multipleNum\":\"+multipleNum+\","+
//		  "\"multiplePoint\":\""+multiplePoint+"\",\"judgeNum\":\""+judgeNum+"\",\"judgePoint\":\""+judgePoint+"\"}";
//		JSONObject typeDistribution = new JSONObject(type);
//		examPaper.setTypeDistribution(typeDistribution.toString());
		Map<String,String> map = new HashMap<String,String>();
		map.put("singleNum", singleNum);
		map.put("singlePoint", singlePoint);
		map.put("multipleNum", multipleNum);
		map.put("multiplePoint", multiplePoint);
		map.put("judgeNum", judgeNum);
		map.put("judgePoint", judgePoint);
		JSONSerializer.toJSON(map);
		examPaper.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examPaperService.insertSelective(examPaper);
		return "redirect:paperManage.html";
	}
	
	/**
	 * 
	* @Title: editSelectedPaper
	* @author ZhaoBo
	* @date 2016-9-6 上午10:18:23  
	* @Description: 判断当前选中考卷是否可编辑 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editSelectedPaper")
	@ResponseBody
	public String editSelectedPaper(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id[0]);
		String str = null;
		if(examPaper.getStartTime().getTime()-new Date().getTime()>0){
			str = "1";
		}else{
			str = "0";
		}
		return str;
	}
	
	/**
	 * 
	* @Title: editNoTestPaper
	* @author ZhaoBo
	* @date 2016-9-6 上午10:55:31  
	* @Description: 修改考卷页面
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editNoTestPaper")
	public String editNoTestPaper(Model model,HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id[0]);
		model.addAttribute("examPaper", examPaper);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(examPaper.getStartTime());
		String startTime = time.substring(0,10);
		String hour = time.substring(11, 13);
		String second = time.substring(14, 16);
		model.addAttribute("startTime", startTime);
		model.addAttribute("hour", hour);
		model.addAttribute("second", second);
		List<Integer> hours = new ArrayList<Integer>();
		List<Integer> seconds = new ArrayList<Integer>();
		for(int i=1;i<25;i++){
			hours.add(i);
		}
		for(int i=0;i<60;i++){
			seconds.add(i);
		}
		model.addAttribute("hours", hours);
		model.addAttribute("seconds", seconds);
		return "ses/ems/exam/purchaser/paper/edit";
	}
	
	/**
	 * 
	* @Title: editToExamPaper
	* @author ZhaoBo
	* @date 2016-9-5 下午4:19:29  
	* @Description: 编辑考卷并保存 
	* @param @param request
	* @param @param model
	* @param @param examPaper
	* @param @return      
	* @return String
	 * @throws ParseException 
	 */
	@RequestMapping("/editToExamPaper")
	public String editToExamPaper(HttpServletRequest request,Model model,ExamPaper examPaper) throws ParseException{
		examPaper.setId(request.getParameter("paperId"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date year = sdf.parse(request.getParameter("startTime"));
		examPaper.setYear(sdf.format(year));
		examPaper.setName(request.getParameter("paperName"));
		examPaper.setCode(request.getParameter("paperNo"));
		examPaper.setScore(request.getParameter("totalPoint"));
		examPaper.setTestTime(request.getParameter("useTime"));
		examPaper.setStartTime(new Date());
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String judgeNum = request.getParameter("judgeNum");
		String judgePoint = request.getParameter("judgePoint");
		Map<String,String> map = new HashMap<String,String>();
		map.put("singleNum", singleNum);
		map.put("singlePoint", singlePoint);
		map.put("multipleNum", multipleNum);
		map.put("multiplePoint", multiplePoint);
		map.put("judgeNum", judgeNum);
		map.put("judgePoint", judgePoint);
		JSONSerializer.toJSON(map);
		examPaper.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examPaperService.updateByPrimaryKeySelective(examPaper);
		return "redirect:paperManage.html";
	}
	
	/**
	 * 
	* @Title: entryPaperNumber
	* @author ZhaoBo
	* @date 2016-9-6 下午1:16:07  
	* @Description: 判断输入的考试编号是否正确 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/entryPaperNumber")
	@ResponseBody
	public String entryPaperNumber(HttpServletRequest request){
		String paperNo = request.getParameter("paperNo");
		ExamPaper examPaper = examPaperService.selectByPaperNo(paperNo);
		String str = null;
		if(examPaper==null){
			str = "0";
		}else{
			str = "1";
		}
		return str;
	}
	
	/**
	 * 
	* @Title: purReadyExam
	* @author ZhaoBo
	* @date 2016-9-6 下午1:31:10  
	* @Description: 采购人考试最初页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/ready")
	public String ready(){
		return "ses/ems/exam/purchaser/ready";
	}
	
	/**
	 * 
	* @Title: savePurchaserScore
	* @author ZhaoBo
	* @date 2016-9-6 下午7:01:41  
	* @Description: 采购人答题算总分 
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/savePurchaserScore")
	public String savePurchaserScore(Model model,HttpServletRequest request){
		String[] purQueAnswer = request.getParameter("purQueAnswer").split(",");
		String[] purQueType = request.getParameter("purQueType").split(",");
		String[] purQueId = request.getParameter("purQueId").split(",");
		String paperId = request.getParameter("paperId");
		ExamPaper userExamPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = userExamPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleP = (String) obj.get("singlePoint");
		Integer singlePoint = Integer.parseInt(singleP);
		String multipleP = (String) obj.get("multiplePoint");
		Integer multiplePoint = Integer.parseInt(multipleP);
		String judgeP = (String) obj.get("judgePoint");
		Integer judgePoint = Integer.parseInt(judgeP);
		Integer score = 0;
		for(int i=0;i<purQueAnswer.length;i++){
			StringBuffer sb = new StringBuffer();
			if(request.getParameterValues("que"+(i+1))==null){
				ExamUserAnswer examUserAnswer = new ExamUserAnswer();
				examUserAnswer.setContent(" ");
				examUserAnswer.setCreatedAt(new Date());
				examUserAnswer.setQuestionId(purQueId[i]);
				examUserAnswer.setUserType(2);
				examUserAnswer.setPaperId(paperId);
				examUserAnswerService.insertSelective(examUserAnswer);
				continue;
			}else{
				String[] queUserAnswer = request.getParameterValues("que"+(i+1));
				for(int j=0;j<queUserAnswer.length;j++){
					sb.append(queUserAnswer[j]);
				}
				ExamUserAnswer examUserAnswer = new ExamUserAnswer();
				examUserAnswer.setContent(sb.toString());
				examUserAnswer.setCreatedAt(new Date());
				examUserAnswer.setQuestionId(purQueId[i]);
				examUserAnswer.setUserType(2);
				examUserAnswer.setPaperId(paperId);
				examUserAnswerService.insertSelective(examUserAnswer);
				if(purQueAnswer[i].equals(sb.toString())){
					if(purQueType[i].equals("单选题")){
						score = score + singlePoint;
					}else if(purQueType[i].equals("多选题")){
						score = score + multiplePoint;
					}else if(purQueType[i].equals("判断题")){
						score = score + judgePoint;
					}
				}
			}
		}
		model.addAttribute("isAllowRetake", userExamPaper.getIsAllowRetake());
		model.addAttribute("score", score);
		model.addAttribute("paperId", paperId);
		if(request.getParameter("count")!=null){
			model.addAttribute("count", request.getParameter("count"));
		}else{
			model.addAttribute("count", 0);
		}
		return "ses/ems/exam/purchaser/score";
	}
	
	/**
	 * 
	* @Title: result
	* @author ZhaoBo
	* @date 2016-9-6 下午8:05:00  
	* @Description: 采购人考试成绩查询页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/result")
	public String result(){
		return "ses/ems/exam/purchaser/result";
	}
	
	/**
	 * 
	* @Title: queryPurParam
	* @author ZhaoBo
	* @date 2016-9-6 下午8:46:47  
	* @Description: 采购人考试成绩按条件查询 
	* @param @param request
	* @param @return      
	* @return List<ExamUserScore>
	 */
	@RequestMapping("/queryPurParam")
	public List<ExamUserScore> queryPurParam(HttpServletRequest request){
		
		return null;
	}
	
	/**
	 * 
	* @Title: printTable
	* @author ZhaoBo
	* @date 2016-9-12 上午9:59:59  
	* @Description: 采购人打印表格 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/printTable")
	public String printTable(Model model){
		List<ExamQuestion> examPool = examQuestionService.selectAllContent();
		model.addAttribute("examPool", examPool);
		return "ses/ems/exam/purchaser/abc";
	}
	
	/**
	 * 
	* @Title: reTake
	* @author ZhaoBo
	* @date 2016-9-14 下午2:56:38  
	* @Description: 采购人重考方法 
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/reTake")
	public String reTake(Model model,HttpServletRequest request){
		String paperId = request.getParameter("paperId");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");
		Integer multipleNum = Integer.parseInt(multipleN);
		String judgeN = (String) obj.get("judgeNum");
		Integer judgeNum = Integer.parseInt(judgeN);
		ExamQuestion single = new ExamQuestion();
		single.setSingleNum(singleNum);
		ExamQuestion multiple = new ExamQuestion();
		multiple.setMultipleNum(multipleNum);
		ExamQuestion judge = new ExamQuestion();
		judge.setJudgeNum(judgeNum);
		List<ExamQuestion> singleQue = examQuestionService.selectSingleRandom(single);
		List<ExamQuestion> multipleQue = examQuestionService.selectMultipleRandom(multiple);
		List<ExamQuestion> judgeQue = examQuestionService.selectJudgeRandom(judge);
		List<ExamQuestion> purchaserQue = new ArrayList<ExamQuestion>();
		purchaserQue.addAll(singleQue);
		purchaserQue.addAll(multipleQue);
		purchaserQue.addAll(judgeQue);
		List<Integer> pageNum = new ArrayList<Integer>();
		if(purchaserQue.size()%2==0){
			for(int i=0;i<purchaserQue.size()/2;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<purchaserQue.size()/2+1;i++){
				pageNum.add(i);
			}
		}
		StringBuffer sb_answers = new StringBuffer();
		StringBuffer sb_queTypes = new StringBuffer();
		StringBuffer sb_questionIds =  new StringBuffer();
		for(int i=0;i<purchaserQue.size();i++){
			sb_answers.append(purchaserQue.get(i).getAnswer()+",");
			sb_queTypes.append(purchaserQue.get(i).getExamQuestionType().getName()+",");
			sb_questionIds.append(purchaserQue.get(i).getId()+",");
		}
		model.addAttribute("purQueType",sb_queTypes);
		model.addAttribute("purQueAnswer", sb_answers);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("purchaserQue",purchaserQue);
		model.addAttribute("paperId", paperId);
		model.addAttribute("purQueId", sb_questionIds);
		model.addAttribute("countDown", 1);
		model.addAttribute("count", request.getParameter("count"));
		return "ses/ems/exam/purchaser/test";
	}
	
	/**
	 * 
	* @Title: reTake
	* @author ZhaoBo
	* @date 2016-9-14 下午2:56:38  
	* @Description: 采购人重考方法 
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/reTakes")
	public String reTakes(Model model,HttpServletRequest request){
		String paperId = request.getParameter("paperId");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");
		Integer multipleNum = Integer.parseInt(multipleN);
		String judgeN = (String) obj.get("judgeNum");
		Integer judgeNum = Integer.parseInt(judgeN);
		ExamQuestion single = new ExamQuestion();
		single.setSingleNum(singleNum);
		ExamQuestion multiple = new ExamQuestion();
		multiple.setMultipleNum(multipleNum);
		ExamQuestion judge = new ExamQuestion();
		judge.setJudgeNum(judgeNum);
		List<ExamQuestion> singleQue = examQuestionService.selectSingleRandom(single);
		List<ExamQuestion> multipleQue = examQuestionService.selectMultipleRandom(multiple);
		List<ExamQuestion> judgeQue = examQuestionService.selectJudgeRandom(judge);
		List<ExamQuestion> purchaserQue = new ArrayList<ExamQuestion>();
		purchaserQue.addAll(singleQue);
		purchaserQue.addAll(multipleQue);
		purchaserQue.addAll(judgeQue);
		List<Integer> pageNum = new ArrayList<Integer>();
		if(purchaserQue.size()%2==0){
			for(int i=0;i<purchaserQue.size()/2;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<purchaserQue.size()/2+1;i++){
				pageNum.add(i);
			}
		}
		StringBuffer sb_answers = new StringBuffer();
		StringBuffer sb_queTypes = new StringBuffer();
		StringBuffer sb_questionIds =  new StringBuffer();
		for(int i=0;i<purchaserQue.size();i++){
			sb_answers.append(purchaserQue.get(i).getAnswer()+",");
			sb_queTypes.append(purchaserQue.get(i).getExamQuestionType().getName()+",");
			sb_questionIds.append(purchaserQue.get(i).getId()+",");
		}
		model.addAttribute("purQueType",sb_queTypes);
		model.addAttribute("purQueAnswer", sb_answers);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("purchaserQue",purchaserQue);
		model.addAttribute("paperId", paperId);
		model.addAttribute("purQueId", sb_questionIds);
		model.addAttribute("countDown", 1);
		model.addAttribute("count", request.getParameter("count"));
		return "ses/ems/exam/purchaser/test";
	}
	
	/**
	 * 
	* @Title: view
	* @author ZhaoBo
	* @date 2016-9-18 下午5:18:00  
	* @Description: 采购人查看题库页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request,Model model){
		ExamQuestion examPool = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("purchaserQue",examPool);
		String queAnswer = examPool.getAnswer();
		model.addAttribute("purchaserAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectPurchaserAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examPool.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(1));
		model.addAttribute("optionB", queOption[1].substring(1));
		model.addAttribute("optionC", queOption[2].substring(1));
		model.addAttribute("optionD", queOption[3].substring(1));
		return "ses/ems/exam/purchaser/question/view";
	}
	
	/**
	 * 
	* @Title: viewPaper
	* @author ZhaoBo
	* @date 2016-9-19 下午5:18:33  
	* @Description: 查看考卷页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewPaper")
	public String viewPaper(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id);
		model.addAttribute("examPaper", examPaper);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(examPaper.getStartTime());
		String startTime = time.substring(0,10);
		String hour = time.substring(11, 13);
		String second = time.substring(14, 16);
		model.addAttribute("startTime", startTime);
		model.addAttribute("hour", hour);
		model.addAttribute("second", second);
		List<Integer> hours = new ArrayList<Integer>();
		List<Integer> seconds = new ArrayList<Integer>();
		for(int i=1;i<25;i++){
			hours.add(i);
		}
		for(int i=0;i<60;i++){
			seconds.add(i);
		}
		model.addAttribute("hours", hours);
		model.addAttribute("seconds", seconds);
		return "ses/ems/exam/purchaser/paper/view";
	}
}
