package com.hengtiansoft.business.barcode.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.barcode.service.BasicCodeService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;

/**
 * 
 * Class Name: BasicCodeServiceImpl
 * Description: TODO
 * @author hongqi
 *
 */
@Service
public class BasicCodeServiceImpl implements BasicCodeService{
    private static final Logger log= LoggerFactory.getLogger(BasicCodeServiceImpl.class);
    @Autowired
    LocalContainerEntityManagerFactoryBean connectionFactory;
    @Autowired
    PGoodsDao goodsDao;

    private final static String insertSql="INSERT INTO S_BASIC_CODE (QR_CODE, URL, PREFIX_CODE,PACK_CODE,GOOD_ID,STATUS,CREATE_DATE,CREATE_ID,CODE_URL) VALUES (?,?,?,?,?,?,?,?,?) ";
    /**
     * 
     * Description:读取 excel文件，返回读取情况 
     *
     * @param is excel的输入流
     * @return 返回导入结果
     */
    @Override
    public ResultDto<List<SBasicCodeEntity>> importBarcode(InputStream is) {
        /**
         * jdbc的连接
         */
        Connection conn=null;
        //获取jdbc连接
        conn=DataSourceUtils.getConnection(connectionFactory.getDataSource());
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("msg",e);
        }
        /**
         * 一次存储的个数
         */

        int MAX_COUNT=1000;
        //用于存放存储失败的对象
        List<SBasicCodeEntity> failItems=new ArrayList<>();
        String URLPre="http://wly.wupincool.com/?c=";
        //用来存放待存储的数据
        List<SBasicCodeEntity>items=new ArrayList<>();
        Map<String,Integer> amountRes=new HashMap<String,Integer>();
        amountRes.put("successAmount", 0);
        long createId=AuthorityContext.getCurrentUser().getUserId();

        try{
            Workbook wb=WorkbookFactory.create(is);
            int sheetNum = wb.getNumberOfSheets();
            Sheet sheet = null;
            for(int sheetIndex = 0;sheetIndex<sheetNum;sheetIndex++){//遍历sheet(index 0开始)
                sheet = wb.getSheetAt(sheetIndex);
                Row row = null;
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int rowIndex = firstRowNum+1;rowIndex<=lastRowNum;rowIndex++ ) {//遍历row(行 0开始)
                    //新增一行数据
                    row = sheet.getRow(rowIndex);
                    SBasicCodeEntity item=new SBasicCodeEntity();
                    if(null != row){
                        //记录这行是否有效
                        boolean isValid=true;
                        int firstCellNum = row.getFirstCellNum();
                        int lastCellNum = row.getLastCellNum();
                        for (int cellIndex = firstCellNum; cellIndex < lastCellNum; cellIndex++) {//遍历cell（列 0开始）
                            Cell cell = row.getCell(cellIndex, Row.RETURN_BLANK_AS_NULL);
                            if(cell==null){
                                if(cellIndex==0||cellIndex==1||cellIndex==3){
                                    isValid=false;
                                    break;
                                }
                                continue;
                            }
                            switch(cellIndex)
                            {
                            
                            case 0://16位码
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                item.setQrCode(cell.getStringCellValue());
                                break;
                            
                            case 1://二维码明码
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                item.setPrefixCode(cell.getStringCellValue());
                                break;
                                
                            case 2://二维码url
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                item.setCodeUrl(cell.getStringCellValue());
//                                System.out.println(item.getCodeUrl());
                                break;
                            case 3://箱码
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                item.setPackCode(cell.getStringCellValue());
                                break;
                            case 4://子码
                                break;
                            case 5://产品序列
                                break;
                            case 6://产品名称

                                break;
                            case 7://单瓶商品编号(P_GOODS 的good_code)
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                String goodCode=cell.getStringCellValue();
                                PGoodsEntity e=goodsDao.findByGoodCode(goodCode);
                                if(e!=null)
                                    item.setGoodId(e.getGoodsId());
                                break;
                            }
                        }
                        if(isValid){
                            item.setUrl(URLPre+item.getQrCode());
                            item.setCreateDate(new Date());
                            item.setCreateId(createId);
                            //这里添加了个需求，就是goodid如果为0则插入不成功
                            if(item.getGoodId()!=null&&item.getGoodId()!=0){
                                items.add(item);
                            }
                            else{
                                failItems.add(item);
                            }
                        }
                    }
                    if(items.size()>=MAX_COUNT){
                        //讲items里面的数据存至数据库
                        save(items,failItems,amountRes,conn);
                    }
                }//end row
                if(items.size()>0)
                {
                    save(items,failItems,amountRes,conn);
                }
            }//end sheet
        }
        catch(Exception e)
        {
            log.error("msg",e);
            return ResultDtoFactory.toNack("文件读取失败");
        }
        finally{
            DataSourceUtils.releaseConnection(conn, connectionFactory.getDataSource());
        }
        ResultDto<List<SBasicCodeEntity>> res=ResultDtoFactory.toAck("成功插入"+amountRes.get("successAmount")+"个,插入失败"+failItems.size()+"个");
        res.setData(failItems);
        return res;
    }
    /**
     * 
     * Description: 存储
     *
     * @param items
     * @param failItems
     * @param conn 
     * @param successAmount 成功插入的数量
     */
    private void save(List<SBasicCodeEntity> items, List<SBasicCodeEntity> failItems,Map<String,Integer> amountRes, Connection conn){

        PreparedStatement pstmt = null;
        try {
            pstmt=conn.prepareStatement(insertSql);
            for(SBasicCodeEntity s:items){
                setParam(pstmt,s);
                pstmt.addBatch();
            }
            try{
                pstmt.executeBatch();
                conn.commit();
                amountRes.put("successAmount", amountRes.get("successAmount")+items.size());
                pstmt.clearBatch();
            }
            catch(Exception e){
                log.error("msg",e);
                conn.rollback();
                saveByTen(items, failItems,amountRes,conn);
            }
            finally{
                try {
                    if(pstmt!=null)
                        pstmt.close();
                } catch (SQLException e) {
                    log.error("msg",e);
                }
            }

        } catch (SQLException e) {
            log.error("msg",e);
        }
        items.clear();

    }

    /**
     * 
     * Description: 设置参数
     *
     * @param pstmt
     * @param item
     * @throws SQLException
     */
    private void setParam(PreparedStatement pstmt, SBasicCodeEntity item) throws SQLException {
        pstmt.setString(1,item.getQrCode());
        pstmt.setString(2,item.getUrl());
        pstmt.setString(3,item.getPrefixCode());
        pstmt.setString(4,item.getPackCode());
        pstmt.setLong(5,item.getGoodId()==null?0:item.getGoodId());
        pstmt.setString(6,"0");
        pstmt.setTimestamp(7,new Timestamp(new Date().getTime()));
        pstmt.setLong(8,item.getCreateId());
        pstmt.setString(9,item.getCodeUrl());
    }
    /**
     * 存一个
     * Description: TODO
     *
     * @param item
     * @param failItems
     * @param conn2 
     */
    private void saveOne(SBasicCodeEntity item, List<SBasicCodeEntity> failItems,Map<String,Integer> amountRes, Connection conn){
        PreparedStatement pstmt = null;
        try{

            pstmt = conn.prepareStatement(insertSql);
            setParam(pstmt,item);
            pstmt.execute();
            conn.commit();
            amountRes.put("successAmount", amountRes.get("successAmount")+1);
        }
        catch(Exception e){
            log.error("msg",e);
            failItems.add(item);
        }
        finally {
            try {
                if(pstmt!=null)
                    pstmt.close();
            } catch (SQLException e) {
                log.error("msg",e);
            }
        }
    }

    /**
     * 
     * Description:分成十份存储
     *
     * @param item
     * @param failItems
     * @param conn2 
     * @param amount
     */
    private void saveByTen(List<SBasicCodeEntity> itemsParent, List<SBasicCodeEntity> failItems,Map<String,Integer> amountRes, Connection conn){
        for(int i=0;i<10;i++){

            if((itemsParent.size()/10+1)*i>itemsParent.size())break;
            List<SBasicCodeEntity>items=itemsParent.subList((itemsParent.size()/10+1)*i, (itemsParent.size()/10+1)*(i+1)>itemsParent.size()?itemsParent.size():(itemsParent.size()/10+1)*(i+1));
            PreparedStatement pstmt = null;
            try {
                pstmt=conn.prepareStatement(insertSql);
                for(SBasicCodeEntity s:items){
                    setParam(pstmt,s);
                    pstmt.addBatch();
                }
                try{
                    pstmt.executeBatch();
                    conn.commit();
                    amountRes.put("successAmount", amountRes.get("successAmount")+items.size());
                    pstmt.clearBatch();
                }
                catch(Exception e){
                    log.error("msg",e);
                    conn.rollback();
                    for(SBasicCodeEntity s:items){
                        saveOne(s,failItems,amountRes,conn);
                    }
                }

            } catch (SQLException e) {
                log.error("msg",e);
            }
            finally {
                try {
                    if(pstmt!=null)
                        pstmt.close();
                } catch (SQLException e) {
                    log.error("msg",e);
                }                    
            }
        }

    }

}
