package com.zq.seller.service;

import com.zq.entity.VerificationOrder;
import com.zq.seller.enums.ChanEnum;
import com.zq.seller.repositories.OrderRepository;
import com.zq.seller.repositories.VerifyRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VerifyService {
    @Autowired
    private VerifyRepository verifyRepository;

    @Autowired
    private OrderRepository orderRepository;

    //指定这个配置文件的配置属性名称,如果不配置就默认使用/opt/verification
//    @Value("${verification.rootdir:/opt/verification}")
//    public String rootDir;
    public String rootDir = "E:\\opt\\verification\\";

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //定义分隔符，如果系统上没有line.separator这个属性，则使用\n换行
    private static String END_LINE = System.getProperty("line.separator", "\n");

    /**
     * 生成某个渠道某天的对账文件
     *
     * @param chanId
     * @param day
     * @return
     */
    public File makeVerificationFile(String chanId, Date day) {
        File path = getPath("", chanId, day);
        if (path.exists()) {
            return path;
        }
        try {
            path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //构造起止时间
        Date start = getStartOfDay(day);
        Date end = add24Hours(start);
        System.out.println(start);
        System.out.println(end);
        List<String> orders = orderRepository.queryOrders(chanId, start, end);
        System.out.println(orders);
        //用分隔符把我们的订单数据串联起来
        String content = String.join(END_LINE, orders);
        //把对账数据保存到对应的目录下面去
        FileUtil.writeAsString(path, content);
        return path;
    }

    public File getPath(String rootDir, String chanId, Date day) {
        String datetime = DATE_FORMAT.format(day);
        String filename = datetime+"-"+chanId+".txt";
        File f = new File(rootDir+filename);
        return f;
    }

    private Date add24Hours(Date start) {
        return new Date(start.getTime() + 24 * 60 * 60 * 1000);
    }

    private Date getStartOfDay(Date day) {
        String start_str = DATE_FORMAT.format(day);
        Date start = null;
        try {
            start = DATE_FORMAT.parse(start_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return start;
    }

    public static VerificationOrder parseLine(String line){
        VerificationOrder order = new VerificationOrder();
        String[] props = line.split("\\|");
        order.setOuterOrderId(props[0]);
        order.setChanId(props[1]);
        order.setProductId(props[2]);
        order.setChanUserId(props[3]);
        order.setOrderType(props[4]);
        order.setOrderId(props[5]);
        order.setAmount(new BigDecimal(props[6]));
        try {
            order.setCreateAt(DATETIME_FORMAT.parse(props[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * 保存渠道订单数据
     * @param chanId
     * @param day
     */
    public void saveChanOrders(String chanId, Date day){
        ChanEnum conf = ChanEnum.getByChanId(chanId);
        //根据配置从ftp下载对账的对账数据保存到本地，这里省略不写
        //从本地读取对账文件，然后解析保存入库
        File file = getPath(conf.getRootDir(), chanId, day);
        if (!file.exists()){
            return;
        }

        //解析对账文件
        String content = null;
        try {
            content = FileUtil.readAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines = content.split(END_LINE);
        ArrayList<VerificationOrder> orders = new ArrayList<>();
        for (String line : lines) {
            orders.add(parseLine(line));
        }
        verifyRepository.save(orders);
    }

    public List<String> verifyOrder(String chanId, Date day){
        List<String> errors = new ArrayList<>();
        Date start = getStartOfDay(day);
        Date end = add24Hours(start);
        List<String> excessOrders = verifyRepository.queryExcessOrders(chanId, start, end);
        List<String> missOrders = verifyRepository.queryMissOrders(chanId, start, end);
        List<String> differentOrders = verifyRepository.queryDifferentOrders(chanId, start, end);
        errors.add("长款订单号：" + String.join(",", excessOrders));
        errors.add("漏单订单号：" + String.join(",", missOrders));
        errors.add("不一致订单号：" + String.join(",", differentOrders));
        return errors;
    }

    public File makeOrderFile(String chanId, Date date) {
        File path = getPath(rootDir, chanId, date);
        if (path.exists()) {
            return path;
        }
        try {
            path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //构造起止时间
        Date start = getStartOfDay(date);
        Date end = add24Hours(start);
        List<String> orders = orderRepository.queryOrders(chanId, start, end);
        System.out.println(orders);
        //用分隔符把我们的订单数据串联起来
        String content = String.join(END_LINE, orders);
        //把对账数据保存到对应的目录下面去
        FileUtil.writeAsString(path, content);
        return path;
    }

//    @PostConstruct
//    public void testMakeVerificationFile() throws ParseException {
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-30");
//        File file = makeVerificationFile("10000",date);
//        System.out.println(file.getAbsolutePath());
//    }

//    @PostConstruct
//    public void testSaveChanOrders(){
//        Date date = new GregorianCalendar(2018, Calendar.DECEMBER, 30).getTime();
//        saveChanOrders("10000",date);
//    }

    @PostConstruct
    public void verifyOrder(){
        Date day = new GregorianCalendar(2018, Calendar.DECEMBER, 30).getTime();
        List<String> strings = verifyOrder("10000", day);
        System.out.println("对账结果:");
        System.out.println(String.join(";", strings));
    }


}