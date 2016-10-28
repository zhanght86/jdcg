package ses.service.sms;

import java.util.List;

import ses.model.sms.Quote;

/**
 * @Title: SupplierQuoteService
 * @Description: 供应商报价服务层
 * @author: Song Biaowei
 * @date: 2016-10-28上午10:35:54
 */
public interface SupplierQuoteService {
	
	/**
	 * @Title: getAllQuote
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:35:24  
	 * @Description:获取所有的报价信息 
	 * @param @param quote
	 * @param @param page
	 * @param @return      
	 * @return List<Quote>
	 */
	List<Quote> getAllQuote(Quote quote,Integer page);
	
	/**
	 * @Title: insert
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:35:40  
	 * @Description: 保存报价
	 * @param @param quote      
	 * @return void
	 */
	void insert(Quote quote);
}
